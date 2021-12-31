package com.server.alerts;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import freemarker.template.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${spring.mail.username}")
    private String fromEmail;

    private final JavaMailSender javaMailSender;
    private final Configuration configuration;

    @Async
    public void sendEmail(EmailConfiguration emailConfiguration) throws IOException, TemplateException {

        Template template = configuration.getTemplate(emailConfiguration.getTemplate() + ".html");
        String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, emailConfiguration.getModel());

        MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setFrom(fromEmail);
            messageHelper.setTo(emailConfiguration.getRecipient());
            messageHelper.setSubject(emailConfiguration.getSubject());
            messageHelper.setText(content, true);
            emailConfiguration.getImages().forEach((name, location) ->
                    {
                        try {
                            messageHelper.addInline(name, new ClassPathResource(location));
                        } catch (MessagingException e) {
                            e.printStackTrace();
                        }
                    }
            );
        };
        javaMailSender.send(mimeMessagePreparator);
    }
}
