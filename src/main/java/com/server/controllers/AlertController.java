package com.server.controllers;

import com.server.entities.Alert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.server.services.AlertService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
public class AlertController {

    private final AlertService alertService;
    private static final Logger log = LoggerFactory.getLogger(AlertController.class);


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
    @GetMapping("/alerts/{email}")
    public List<Alert> getAlertsByUserEmail(@PathVariable String email) {
        return this.alertService.getAllAlertsByUserEmail(email);
    }

    @CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
    @PostMapping("/alerts/add")
    public Map<String, String> addNewAlert(@RequestBody Alert alert) {
        if(this.alertService.addNewAlert(alert)){
            log.info("Added alert: " + alert);
            return Collections.singletonMap("response", "added");
        }
        else {
            return Collections.singletonMap("response", "not");
        }
    }

    @CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
    @DeleteMapping("/alerts/delete/{alertValue}/{email}/{abbr}")
    public Map<String, String> deleteAlert(@PathVariable Integer alertValue, @PathVariable String email, @PathVariable String abbr) {
        if(this.alertService.deleteAlert(alertValue, email, abbr)){
            log.info("Deleted alert with parameters: " + alertValue + ", " + email + ", " + abbr);
            return Collections.singletonMap("response", "deleted");
        }
        else {
            return Collections.singletonMap("response", "not");
        }
    }

}
