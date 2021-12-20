package alerts;

import org.springframework.context.ApplicationEvent;

public class CurrentValueChangedEvent extends ApplicationEvent {

    public CurrentValueChangedEvent(Object source) {
        super(source);
    }
}
