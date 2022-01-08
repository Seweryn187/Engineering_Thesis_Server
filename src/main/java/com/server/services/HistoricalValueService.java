package com.server.services;

import com.server.entities.HistoricalValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.server.repositories.HistoricalValueRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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
        historicalValuesListByCurrencyAbbr.addAll(historicalValueRepository.findHistoricalValueByCurrencyAbbr(abbr));
        return historicalValuesListByCurrencyAbbr;
    }

    public List<HistoricalValue> getAllHistoricalValueByCurrencyAbbrAndSourceName(String abbr, String source) {
        List<HistoricalValue> historicalValuesListByCurrencyAbbrAndSourceName = new ArrayList<>();
        historicalValuesListByCurrencyAbbrAndSourceName.addAll(
                historicalValueRepository.findHistoricalValueByCurrencyAbbrAndSourceName(abbr, source));
        return historicalValuesListByCurrencyAbbrAndSourceName;
    }

    public List<HistoricalValue> getAllHistoricalValueByCurrencyAbbrAndSourceNameAndDateBefore(String abbr, String source,
                                                                                             LocalDate timePeriod) {
        List<HistoricalValue> historicalValuesListByCurrencyAbbrAndSourceNameTimeBetween = new ArrayList<>();
        historicalValuesListByCurrencyAbbrAndSourceNameTimeBetween.addAll(
                historicalValueRepository.findHistoricalValueByCurrencyAbbrAndSourceNameAndDateBefore(abbr, source, timePeriod));
        return historicalValuesListByCurrencyAbbrAndSourceNameTimeBetween;
    }
}
