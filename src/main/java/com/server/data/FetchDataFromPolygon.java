package com.server.data;

import com.server.alerts.SendAlerts;
import com.server.entities.CurrentValue;

import com.server.response.PolygonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import com.server.repositories.CurrencyRepository;
import com.server.repositories.CurrentValueRepository;
import com.server.repositories.HistoricalValueRepository;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

import static com.server.utility.Utility.calculateSpread;
import static com.server.utility.Utility.castFloatToInt;

@EnableScheduling
@Service
public class FetchDataFromPolygon {

    private final CurrencyRepository currencyRepository;
    private final CurrentValueRepository currentValueRepository;
    private final SendAlerts sendAlerts;
    private final ArchiveData archiveData;
    private final FetchData fetchData;
    private WebClient webClient;
    @Value("${polygon.apikey}")
    private String apikey;

    private static final Logger log = LoggerFactory.getLogger(FetchDataFromPolygon.class);

    @Autowired
    public FetchDataFromPolygon(CurrencyRepository currencyRepository, CurrentValueRepository currentValueRepository,
                                HistoricalValueRepository historicalValueRepository, SendAlerts sendAlerts, ArchiveData archiveData, FetchData fetchData) {
        this.currencyRepository = currencyRepository;
        this.currentValueRepository = currentValueRepository;
        this.sendAlerts = sendAlerts;
        this.archiveData = archiveData;
        this.fetchData = fetchData;
    }


    /* (cron = "0 0 2 * * 0-5") - cron expression, which dictates at what time this function have to
    start, in this case every day in working week at 2:00 am
    */
    @Transactional
    //@Scheduled(fixedDelay = 30000)
    public void saveNewCurrentValue() {
        CurrentValue newCurrentValue = new CurrentValue();
        int triesCount = 0;
        int maxTries = 3;
        int recordCount = 1;
        long recordAmount;
        LocalDate localDate = LocalDate.now();
        LocalDate responseDate;

        webClient = fetchData.buildClient("https://api.polygon.io");
        recordAmount = this.currencyRepository.count();
        while(triesCount < maxTries) {
            try {
                log.info("-------------Getting new value and archiving old one (Sync)----------------");

                for (CurrentValue record : this.currentValueRepository.findCurrentValueBySourceName("Polygon")) {
                    if(recordCount%5 == 0){ // because api which I'm using only allows 5 requests per minute
                        try {
                            TimeUnit.MINUTES.sleep(1);
                        } catch (InterruptedException ie) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    PolygonResponse response = getCurrentValueFromPolygon(record);

                    if (response != null) {
                        responseDate = response.getResults().get(0).getT().toLocalDateTime().toLocalDate();
                        if(responseDate.getDayOfMonth() != record.getDate().getDayOfMonth()){
                            archiveData.archiveCurrentValue(record);
                        }

                        newCurrentValue.setId(record.getId());
                        newCurrentValue.setBidValue(castFloatToInt(response.getResults().get(0).getL())); // Because in database I'm saving integer not a float value
                        newCurrentValue.setAskValue(castFloatToInt(response.getResults().get(0).getH()));
                        newCurrentValue.setMeanValue(castFloatToInt((response.getResults().get(0).getH() + response.getResults().get(0).getL())/2));
                        newCurrentValue.setSource(record.getSource());
                        if(responseDate.getDayOfMonth() != localDate.getDayOfMonth()){
                            newCurrentValue.setDate(localDate);
                        } else{
                            newCurrentValue.setDate(responseDate);
                        }
                        newCurrentValue.setSpread(calculateSpread(newCurrentValue.getAskValue(), newCurrentValue.getBidValue(), newCurrentValue.getMeanValue()));
                        newCurrentValue.setAskIncrease(record.getAskValue() < response.getResults().get(0).getH());
                        newCurrentValue.setBidIncrease(record.getBidValue() < response.getResults().get(0).getL());
                        newCurrentValue.setCurrency(record.getCurrency());
                        newCurrentValue.setBestPrice(false);

                        CurrentValue addedValue = this.currentValueRepository.save(newCurrentValue);
                        sendAlerts.sendValueChangeAlerts(record, newCurrentValue);
                        log.info("I've fetched new value: " + addedValue);

                        if(addedValue != null){
                            ++recordCount;
                        }
                    }
                }
                log.info("-------------------End--------------------");
                if(recordCount == recordAmount+1){
                    break;
                }
            } catch (Exception ex) {
                log.error("Error getting current value from Polygon " + ex.getMessage());
                if(++triesCount == maxTries) throw ex;
            }

        }
        fetchData.checkBestSpread(currentValueRepository, currencyRepository);

    }

    public PolygonResponse getCurrentValueFromPolygon(CurrentValue record) {
        String toCurrency = "PLN";
        return webClient.get()//"&from_currency=" + record.getCurrency().getAbbr() + "&to_currency=" + toCurrency + "&apikey=" + apikey
                .uri("/v2/aggs/ticker/C:"+record.getCurrency().getAbbr() + toCurrency + "/prev?adjusted=true&apiKey=" + apikey)
                .retrieve()
                .bodyToMono(PolygonResponse.class)
                .block();
    }



}
