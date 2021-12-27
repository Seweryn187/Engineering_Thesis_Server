package alerts;

import org.springframework.context.ApplicationEvent;

public class CurrentValueChangedEvent extends ApplicationEvent {

    public CurrentValueChangedEvent(EmailData emailData) {
        super(emailData);
    }
}
