package com.server.alerts;

import com.server.entities.Alert;
import com.server.entities.CurrentValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.server.repositories.AlertRepository;

@Service
public class SendAlerts {

    private final AlertRepository alertRepository;
    private final ApplicationEventPublisher publisher;
    private static final Logger log = LoggerFactory.getLogger(CurrentValueChangedListener.class);

    @Autowired
    public SendAlerts(ApplicationEventPublisher publisher, AlertRepository alertRepository) {
        this.publisher = publisher;
        this.alertRepository = alertRepository;
    }

    @Async
    @Transactional
    public void sendValueChangeAlerts(CurrentValue currentValueRecord, CurrentValue newValue) {
        for (Alert alert : this.alertRepository.findAlertByCurrencyAbbr(currentValueRecord.getCurrency().getAbbr())) {

                if (alert.getIncrease() &&
                        (alert.getAlertValue() < newValue.getBidValue() || alert.getAlertValue() < newValue.getAskValue())) {
                    EmailData emailData = new EmailData(
                            newValue.getAskValue(),
                            newValue.getBidValue(),
                            currentValueRecord.getCurrency().getAbbr(),
                            alert.getUser().getEmail(),
                            "increased");
                    publisher.publishEvent(new CurrentValueChangedEvent(emailData));
                    
                } else if (!alert.getIncrease() &&
                        (alert.getAlertValue() > newValue.getBidValue() || alert.getAlertValue() > newValue.getAskValue())) {
                    EmailData emailData = new EmailData(
                            newValue.getAskValue(),
                            newValue.getBidValue(),
                            currentValueRecord.getCurrency().getAbbr(),
                            alert.getUser().getEmail(),
                            "decreased");
                    publisher.publishEvent(new CurrentValueChangedEvent(emailData));
                }
                if(!alert.getRepeatable()){
                    alertRepository.delete(alert);
                }
            }
        }
}
