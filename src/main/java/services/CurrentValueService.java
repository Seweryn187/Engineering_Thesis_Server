package services;

import entities.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import repositories.CurrencyRepository;

import java.util.ArrayList;
import java.util.List;

@EnableScheduling
@Service
public class CurrentValueService {

    private final CurrencyRepository currencyRepository;

    @Autowired
    public CurrentValueService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public List<Currency> getAllCurrencies() {
        List<Currency>currencyRecords = new ArrayList<>();
        currencyRepository.findAll().forEach(currencyRecords::add);
        return currencyRecords;
    }

    //@Scheduled
    @EventListener(ApplicationReadyEvent.class)
    public void getCurrentValuesFromNBP() {
        System.out.println("Działam");
        String table = "c";
        String format = "json";

        for(Object record : this.currencyRepository.findAll()){
            System.out.println(record);
        }
    }

    public void buildURL() {

    }
}
