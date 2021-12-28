package retrieve.data;

import alerts.SendAlerts;
import entities.*;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private SendAlerts sendAlerts;
    private WebClient webClient;

    private static final Logger log = LoggerFactory.getLogger(FetchDataFromNBP.class);

    @Autowired
    public FetchDataFromNBP(CurrencyRepository currencyRepository, CurrentValueRepository currentValueRepository,
                            HistoricalValueRepository historicalValueRepository, SendAlerts sendAlerts) {
        this.currencyRepository = currencyRepository;
        this.currentValueRepository = currentValueRepository;
        this.historicalValueRepository = historicalValueRepository;
        this.sendAlerts = sendAlerts;
    }


    /* (cron = "0 0 2 * * 0-5") - cron expression, which dictates at what time this function have to
    start, in this case every day in working week at 2:00 am
    */
    @Transactional
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
                log.info("-------------Getting new value and archiving old one (Sync)----------------");
                for (Currency record : this.currencyRepository.findAll()) {
                    NbpResponse response = getCurrentValueFromNBP(record);
                    if (response != null) {
                        archiveCurrentValue(record);

                        id = record.getCurrentValue().getId();
                        buyValue = (int) (response.getRates().get(0).getBid() * 1000); // Because in database I'm saving integer not a float value
                        sellValue = (int) (response.getRates().get(0).getAsk() * 1000);
                        meanValue = (int) (((response.getRates().get(0).getBid() + response.getRates().get(0).getAsk()) / 2) * 1000);
                        source = record.getCurrentValue().getSource();
                        date = response.getRates().get(0).getEffectiveDate();

                        CurrentValue newValue = new CurrentValue(id, buyValue, sellValue, source, meanValue, date);
                        CurrentValue addedValue = this.currentValueRepository.save(newValue);
                        sendAlerts.sendValueChangeAlerts(record, newValue);
                        log.info("I've fetched new value: " + addedValue);
                        if(addedValue != null){
                            ++recordCount;
                        }
                    }
                }
                log.info("-------------------End--------------------");
                if(recordCount == recordAmount){
                    break;
                }
            } catch (Exception ex) {
                log.error("Error getting current value from NBP " + ex.getMessage());
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
        String table = "c/"; // it comes from the structure of api request of NBP, this table has bought and sell value
        String format = "?format=json";
        return webClient.get()
                .uri(table + record.getAbbr() + "/" + format)
                .retrieve()
                .bodyToMono(NbpResponse.class)
                .block();
    }

    @Transactional
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
                    log.info("I've archived value: " + archivedRecord);
                    if(archivedRecord != null){
                        break;
                    }
                }
            } catch (Exception ex) {
                log.error("Error archiving value in database: " + ex.getMessage());
                if(++count == maxTries) throw ex;
            }
        }
    }
}
