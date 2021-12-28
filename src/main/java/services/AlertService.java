package services;

import entities.Alert;
import entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.AlertRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlertService {

    private final AlertRepository alertRepository;

    @Autowired
    public AlertService(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    public List<Alert> getAllAlerts() {
        List<Alert> alertsList = new ArrayList<>();
        alertRepository.findAll().forEach(alertsList::add);
        return alertsList;
    }

    public List<Alert> getAllAlertsByCurrencyAbbr(String abbr) {
        List<Alert> alertsList = new ArrayList<>();
        alertRepository.findAlertByCurrencyAbbr(abbr).forEach(alertsList::add);
        return alertsList;
    }

    public List<Alert> getAllAlertsByUserEmail(String email) {
        List<Alert> alertsList = new ArrayList<>();
        alertRepository.findAlertByUserEmail(email).forEach(alertsList::add);
        return alertsList;
    }

}
