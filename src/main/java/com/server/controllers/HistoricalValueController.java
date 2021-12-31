package com.server.controllers;

import com.server.entities.HistoricalValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.server.services.HistoricalValueService;

import java.util.List;

@RestController
public class HistoricalValueController {

    private final HistoricalValueService historicalValueService;

    @Autowired
    public HistoricalValueController(HistoricalValueService historicalValueService) {
        this.historicalValueService = historicalValueService;
    }

    @CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
    @GetMapping("/historical-value/{abbr}")
    public List<HistoricalValue> getHistoricalValueByCurrencyAbbr(@PathVariable String abbr) {
        return historicalValueService.getAllHistoricalValueByCurrencyAbbr(abbr);
    }
}
