package alerts;

import java.util.Map;

public class EmailConfiguration {

    private String recipient;
    private String subject;
    private String template;
    Map<String, Object> model;
    Map<String, String> images;

    public EmailConfiguration() {
    }

    public EmailConfiguration(String recipient, String subject, String template, Map<String, Object> model, Map<String, String> images) {
        this.recipient = recipient;
        this.subject = subject;
        this.template = template;
        this.model = model;
        this.images = images;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public void setModel(Map<String, Object> model) {
        this.model = model;
    }

    public Map<String, String> getImages() {
        return images;
    }

    public void setImages(Map<String, String> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "EmailConfiguration{" +
                "recipient='" + recipient + '\'' +
                ", subject='" + subject + '\'' +
                ", template='" + template + '\'' +
                ", model=" + model +
                ", images=" + images +
                '}';
    }
}
