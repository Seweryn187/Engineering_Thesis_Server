package com.server.services;

import com.server.entities.HistoricalValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.server.repositories.HistoricalValueRepository;

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
        historicalValuesListByCurrencyAbbr.addAll(historicalValueRepository.findHistoricalValueByCurrencyAbbr(abbr));
        return historicalValuesListByCurrencyAbbr;
    }
}
