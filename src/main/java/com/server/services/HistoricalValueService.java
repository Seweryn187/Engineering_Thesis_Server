package com.server.services;

import com.server.entities.HistoricalValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.server.repositories.HistoricalValueRepository;

import java.time.LocalDate;
import java.util.*;

@Service
public class HistoricalValueService {

    private final HistoricalValueRepository historicalValueRepository;

    @Autowired
    public HistoricalValueService(HistoricalValueRepository historicalValueRepository) {
        this.historicalValueRepository = historicalValueRepository;
    }

    public List<HistoricalValue> getAllHistoricalValueByCurrencyAbbr(String abbr) {
        List<HistoricalValue> historicalValuesListByCurrencyAbbr = new ArrayList<>(historicalValueRepository.findHistoricalValueByCurrencyAbbr(abbr));
        return historicalValuesListByCurrencyAbbr;
    }

    public List<HistoricalValue> getAllHistoricalValueByCurrencyAbbrAndSourceName(String abbr, String source) {
        List<HistoricalValue> historicalValuesListByCurrencyAbbrAndSourceName = new ArrayList<>(historicalValueRepository.findHistoricalValueByCurrencyAbbrAndSourceName(abbr, source));
        historicalValuesListByCurrencyAbbrAndSourceName.sort(Comparator.comparing(HistoricalValue::getDate));

        return historicalValuesListByCurrencyAbbrAndSourceName;
    }

    public List<HistoricalValue> findHistoricalValueByCurrencyAbbrAndSourceNameAndDateBetween(String abbr, String source,
                                                                                             LocalDate after) {
        List<HistoricalValue> historicalValuesListByCurrencyAbbrAndSourceNameTimeBetween = new ArrayList<>(historicalValueRepository.findHistoricalValueByCurrencyAbbrAndSourceNameAndDateAfter(abbr, source, after));
        historicalValuesListByCurrencyAbbrAndSourceNameTimeBetween.sort(Comparator.comparing(HistoricalValue::getDate));

        return historicalValuesListByCurrencyAbbrAndSourceNameTimeBetween;
    }
}
