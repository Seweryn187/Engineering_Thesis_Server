package com.server.controllers;

import com.server.entities.CurrentValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.server.services.CurrentValueService;

import java.util.List;

@RestController
public class CurrentValueController {

    private final CurrentValueService currentValueService;

    @Autowired
    public CurrentValueController(CurrentValueService currentValueService) {
        this.currentValueService = currentValueService;
    }

    @CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
    @GetMapping("/current-values")
    public List<CurrentValue> getAllCurrentValues() {
        return this.currentValueService.getAllCurrentValues();
    }

    @CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
    @GetMapping("/current-values/{name}")
    public List<CurrentValue> getCurrentValueBySourceName(@PathVariable String name) {
        return this.currentValueService.getCurrentValuesBySourceName(name);
    }
}
