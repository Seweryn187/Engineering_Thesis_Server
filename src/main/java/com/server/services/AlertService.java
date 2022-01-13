package com.server.services;

import com.server.entities.Alert;
import com.server.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.server.repositories.AlertRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlertService {

    private final AlertRepository alertRepository;
    private final UserRepository userRepository;

    @Autowired
    public AlertService(AlertRepository alertRepository, UserRepository userRepository) {
        this.alertRepository = alertRepository;
        this.userRepository = userRepository;
    }

    public List<Alert> getAllAlerts() {
        List<Alert> alertsList = new ArrayList<>();
        alertRepository.findAll().forEach(alertsList::add);
        return alertsList;
    }


    public List<Alert> getAllAlertsByUserEmail(String email) {
        List<Alert> alertsList = new ArrayList<>();
        alertsList.addAll(alertRepository.findAlertByUserEmail(email));
        return alertsList;
    }

    public Boolean addNewAlert(Alert alert) {
        alert.getUser().setId(userRepository.findByEmail(alert.getUser().getEmail()).getId());
        alertRepository.save(alert);
        return true;
    }

    public Boolean deleteAlert(Integer alertId) {
        alertRepository.deleteById(alertId);
        return true;
    }

}
