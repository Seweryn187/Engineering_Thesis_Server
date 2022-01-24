package com.server.data;

import com.server.alerts.SendAlerts;
import com.server.entities.CurrentValue;

import com.server.response.FcsApi;
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

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

import static com.server.utility.Utility.calculateSpread;
import static com.server.utility.Utility.castFloatToInt;

@EnableScheduling
@Service
public class FetchDataFromFcsApi {

    private final CurrencyRepository currencyRepository;
    private final CurrentValueRepository currentValueRepository;
    private final SendAlerts sendAlerts;
    private final ArchiveData archiveData;
    private final FetchData fetchData;
    @Value("${fcsapi.apikey}")
    private String apikey;
    private WebClient webClient;

    private static final Logger log = LoggerFactory.getLogger(FetchDataFromFcsApi.class);

    @Autowired
    public FetchDataFromFcsApi(CurrencyRepository currencyRepository, CurrentValueRepository currentValueRepository,
                               SendAlerts sendAlerts, ArchiveData archiveData, FetchData fetchData) {
        this.currencyRepository = currencyRepository;
        this.currentValueRepository = currentValueRepository;
        this.archiveData = archiveData;
        this.fetchData = fetchData;
        this.sendAlerts = sendAlerts;
    }


    /* (cron = "0 0 2 * * 0-5") - cron expression, which dictates at what time this function have to
    start, in this case every day in working week at 2:00 am
    */
    @Transactional
    //@Scheduled(fixedDelay = 100000000)
    public void saveNewCurrentValue() {
        CurrentValue newCurrentValue = new CurrentValue();
        int triesCount = 0;
        int maxTries = 3;
        int recordCount = 1;
        long recordAmount;
        LocalDate localDate = LocalDate.now();

        webClient = fetchData.buildClient("https://fcsapi.com/api-v3/forex/latest");
        recordAmount = this.currencyRepository.count();

        while(triesCount < maxTries) {
            try {
                log.info("-------------Getting new value and archiving old one (Sync)----------------");
                for (CurrentValue record : this.currentValueRepository.findCurrentValueBySourceName("Fcs API")) {
                    if(recordCount%3 == 0){ // because api which I'm using only allows 5 requests per minute
                        try {
                            TimeUnit.MINUTES.sleep(1);
                        } catch (InterruptedException ie) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    FcsApi response = getCurrentValueFromFcsApi(record.getCurrency().getAbbr());
                    if (response != null) {
                        if(response.getResponse()[0].getT().toLocalDateTime().getDayOfMonth() != record.getDate().getDayOfMonth()){
                            archiveData.archiveCurrentValue(record);
                        }

                        newCurrentValue.setId(record.getId());
                        newCurrentValue.setBidValue(castFloatToInt(response.getResponse()[0].getL())); // Because in database I'm saving integer not a float value
                        newCurrentValue.setAskValue(castFloatToInt(response.getResponse()[0].getH()));
                        newCurrentValue.setMeanValue(castFloatToInt((response.getResponse()[0].getH() + response.getResponse()[0].getL())/2));
                        newCurrentValue.setSource(record.getSource());
                        if(response.getResponse()[0].getT().toLocalDateTime().getDayOfMonth() != localDate.getDayOfMonth()){
                            newCurrentValue.setDate(localDate);
                        } else{
                            newCurrentValue.setDate(LocalDate.from(response.getResponse()[0].getT().toLocalDateTime()));
                        }
                        newCurrentValue.setSpread(calculateSpread(newCurrentValue.getAskValue(), newCurrentValue.getBidValue(), newCurrentValue.getMeanValue()));
                        newCurrentValue.setAskIncrease(record.getAskValue() < response.getResponse()[0].getH());
                        newCurrentValue.setBidIncrease(record.getBidValue() < response.getResponse()[0].getL());
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
                log.error("Error getting current value from FcsAPI " + ex.getMessage());
                if(++triesCount == maxTries) throw ex;
            }
        fetchData.checkBestSpread(currentValueRepository, currencyRepository);

        }


    }

    public FcsApi getCurrentValueFromFcsApi(String abbr) {
        return webClient.get()
                .uri("?symbol="+ abbr +"/PLN"+ "&access_key=" + apikey)
                .retrieve()
                .bodyToMono(FcsApi.class)
                .block();
    }

}

