package controllers;

import entities.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import services.AlertService;

import java.util.List;

@RestController
public class AlertController {

    private final AlertService alertService;

    @Autowired
    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @GetMapping("/alerts")
    public List<Alert> getAllAlerts() {
        return this.alertService.getAllAlerts();
    }


    @GetMapping("/alerts/currency-abbr")
    public List<Alert> getAlertsByCurrencyAbbr(String abbr) {
        return this.alertService.getAllAlertsByCurrencyAbbr(abbr);
    }

    @GetMapping("/alerts/user-email")
    public List<Alert> getAlertsByUserEmail(String email) {
        return this.alertService.getAllAlertsByUserEmail(email);
    }



}
