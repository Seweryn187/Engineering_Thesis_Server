package controllers;

import entities.HistoricalValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import services.HistoricalValueService;

import java.util.List;

@RestController
public class HistoricalValueController {

    private final HistoricalValueService historicalValueService;

    @Autowired
    public HistoricalValueController(HistoricalValueService historicalValueService) {
        this.historicalValueService = historicalValueService;
    }

    @GetMapping
    public List<HistoricalValue> getHistoricalValueByCurrencyAbbr(String abbr) {
        return historicalValueService.getAllHistoricalValueByCurrencyAbbr(abbr);
    }
}
