package services;

import entities.Currency;
import entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.CurrencyRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    @Autowired
    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public List<Currency> getAllCurrencies() {
        List<Currency> userList = new ArrayList<>();
        currencyRepository.findAll().forEach(userList::add);
        return userList;
    }
}
