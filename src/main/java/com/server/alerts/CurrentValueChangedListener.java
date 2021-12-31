package com.server.alerts;

import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CurrentValueChangedListener {

    private final EmailService emailService;


    private static final Logger log = LoggerFactory.getLogger(CurrentValueChangedListener.class);

    @Autowired
    public CurrentValueChangedListener(EmailService emailService){
        this.emailService = emailService;

    }

    @Async
    @TransactionalEventListener(phase= TransactionPhase.AFTER_COMMIT)
    public void onCurrentValueChange(CurrentValueChangedEvent event) throws TemplateException, IOException {
        EmailData emailData = (EmailData) event.getSource();

        Map<String, Object> model = new HashMap<>();
        model.put("emailData", emailData);
        Map<String, String> images = new HashMap<>();
        images.put("bee.png", "/static/images/bee.png");
        images.put("pile-of-cash.jpeg", "/static/images/pile-of-cash.jpeg");

        EmailConfiguration emailConfiguration = new EmailConfiguration(emailData.getRecipient(), "Currency value change alert",
                "alert", model, images);

        emailService.sendEmail(emailConfiguration);
        log.info("---------------------------Sending email alert (Async)---------------------------");
        log.info("Sent email with configuration:" + emailConfiguration);
        log.info("---------------------------End---------------------------");
    }


}
