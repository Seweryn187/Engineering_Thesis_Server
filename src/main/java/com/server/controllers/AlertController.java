package com.server.controllers;

import com.server.entities.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.server.services.AlertService;

import java.util.List;

@RestController
public class AlertController {

    private final AlertService alertService;

    @Autowired
    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
    @GetMapping("/alerts")
    public List<Alert> getAllAlerts() {
        return this.alertService.getAllAlerts();
    }

    @CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
    @GetMapping("/alerts/{abbr}")
    public List<Alert> getAlertsByCurrencyAbbr(@PathVariable String abbr) {
        return this.alertService.getAllAlertsByCurrencyAbbr(abbr);
    }

    @CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
    @GetMapping("/alerts/{email}")
    public List<Alert> getAlertsByUserEmail(@PathVariable String email) {
        return this.alertService.getAllAlertsByUserEmail(email);
    }



}
