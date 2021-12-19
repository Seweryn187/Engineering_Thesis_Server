package retrieveData;

import entities.Currency;
import entities.CurrentValue;
import entities.HistoricalValue;
import entities.Source;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import repositories.CurrencyRepository;
import repositories.CurrentValueRepository;
import repositories.HistoricalValueRepository;
import response.NbpResponse;

import java.time.Duration;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@EnableScheduling
@Service
public class FetchDataFromNBP {

    private final CurrencyRepository currencyRepository;
    private final CurrentValueRepository currentValueRepository;
    private final HistoricalValueRepository historicalValueRepository;
    private WebClient webClient;

    @Autowired
    public FetchDataFromNBP(CurrencyRepository currencyRepository, CurrentValueRepository currentValueRepository, HistoricalValueRepository historicalValueRepository) {
        this.currencyRepository = currencyRepository;
        this.currentValueRepository = currentValueRepository;
        this.historicalValueRepository = historicalValueRepository;
    }


    /* (cron = "0 0 2 * * 0-5") - cron expression, which dictates at what time this function have to
    start, in this case every day in working week at 2:00 am
    */
    @Scheduled(fixedRate = 100000)
    public void saveNewCurrentValue() {
        int id;
        int meanValue;
        int buyValue;
        int sellValue;
        Source source;
        Date date;
        int triesCount = 0;
        int maxTries = 3;
        int recordCount = 0;
        long recordAmount;

        buildClient();
        recordAmount = this.currencyRepository.count();
        while(triesCount < maxTries) {
            try {
                for (Currency record : this.currencyRepository.findAll()) {
                    NbpResponse response = getCurrentValueFromNBP(record);
                    if (response != null) {
                        archiveCurrentValue(record);

                        id = record.getCurrentValue().getId();
                        buyValue = (int) (response.getRates().get(0).getBid() * 1000);
                        sellValue = (int) (response.getRates().get(0).getAsk() * 1000);
                        meanValue = (int) (((response.getRates().get(0).getBid() + response.getRates().get(0).getAsk()) / 2) * 1000);
                        source = record.getCurrentValue().getSource();
                        date = response.getRates().get(0).getEffectiveDate();

                        CurrentValue newValue = new CurrentValue(id, buyValue, sellValue, source, meanValue, date);
                        CurrentValue addedValue = this.currentValueRepository.save(newValue);
                        System.out.println("I've fetched new value: " + addedValue);
                        if(addedValue != null){
                            ++recordCount;
                        }

                    }
                }
                if(recordCount == recordAmount){
                    break;
                }
            } catch (Exception ex) {
                System.out.println("Error getting current value from NBP " + ex.getMessage());
                if(++triesCount == maxTries) throw ex;
            }
        }


    }

    public void buildClient() {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .responseTimeout(Duration.ofMillis(5000))
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS)));

        webClient = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl("https://api.nbp.pl/api/exchangerates/rates/")
                .build();
    }

    public NbpResponse getCurrentValueFromNBP(Currency record) {
        String table = "c/";
        String format = "?format=json";
        return webClient.get()
                .uri(table + record.getAbbr() + "/" + format)
                .retrieve()
                .bodyToMono(NbpResponse.class)
                .block();
    }

    public void archiveCurrentValue(Currency record) {
        int meanValue;
        int buyValue;
        int sellValue;
        Source source;
        Currency currency;
        Date date;
        int count = 0;
        int maxTries = 3;

        while(count < maxTries) {
            try {
                if (record != null) {
                    meanValue = record.getCurrentValue().getMeanValue();
                    buyValue = record.getCurrentValue().getBuyValue();
                    sellValue = record.getCurrentValue().getSellValue();
                    source = record.getCurrentValue().getSource();
                    currency = record;
                    date = record.getCurrentValue().getDate();

                    HistoricalValue oldValue = new HistoricalValue(meanValue, buyValue, sellValue, date, source, currency);
                    HistoricalValue archivedRecord = this.historicalValueRepository.save(oldValue);
                    System.out.println("I've archived value: " + archivedRecord);
                    if(archivedRecord != null){
                        break;
                    }
                }
            } catch (Exception ex) {
                System.out.println("Error archiving value in database: " + ex.getMessage());
                if(++count == maxTries) throw ex;
            }
        }
    }

}
