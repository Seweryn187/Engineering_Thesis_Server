package services;

import entities.HistoricalValue;
import entities.Source;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.HistoricalValueRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class HistoricalValueService {

    private final HistoricalValueRepository historicalValueRepository;

    @Autowired
    public HistoricalValueService(HistoricalValueRepository historicalValueRepository) {
        this.historicalValueRepository = historicalValueRepository;
    }

    public List<HistoricalValue> getAllHistoricalValueByCurrencyAbbr(String abbr) {
        List<HistoricalValue> historicalValuesListByCurrencyAbbr = new ArrayList<>();
        historicalValueRepository.findHistoricalValueByCurrencyAbbr(abbr).forEach(historicalValuesListByCurrencyAbbr::add);
        return historicalValuesListByCurrencyAbbr;
    }
}
