package retriveData;

import entities.Currency;
import entities.CurrentValue;
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
import response.NbpResponse;

import java.time.Duration;

import java.util.concurrent.TimeUnit;

@EnableScheduling
@Service
public class FetchDataForApplication {

    private final CurrencyRepository currencyRepository;
    private final CurrentValueRepository currentValueRepository;
    private WebClient webClient;

    @Autowired
    public FetchDataForApplication(CurrencyRepository currencyRepository, CurrentValueRepository currentValueRepository) {
        this.currencyRepository = currencyRepository;
        this.currentValueRepository = currentValueRepository;
    }

    @Scheduled(fixedDelay = 100000) // (cron = "0 0 5 * * 0-5")
    public void saveNewCurrentValue() {
        int meanValue;
        int buyValue;
        int sellValue;

        buildClient();
        try{
            for(Currency record : this.currencyRepository.findAll()){
                NbpResponse response = getCurrentValueFromNBP(record);
                if(response != null){
                    buyValue = (int) (response.getRates().get(0).getBid() * 1000);
                    sellValue = (int) (response.getRates().get(0).getAsk() * 1000);
                    meanValue = (int) (((response.getRates().get(0).getBid() + response.getRates().get(0).getAsk())/2) * 1000);
                    CurrentValue newValue = new CurrentValue(record.getCurrentValue().getId(), buyValue,
                            sellValue, record.getCurrentValue().getSource(), meanValue);
                    System.out.println("I've fetched new value: " + this.currentValueRepository.save(newValue));
                }
            }
        } catch (Exception ex) {
            System.out.println("Error getting current value from NBP " + ex.getMessage());
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
                .baseUrl("http://api.nbp.pl/api/exchangerates/rates/")
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

}
