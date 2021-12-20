package alerts;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class CurrentValueChangedListener {

    @EventListener
    public void onCurrentValueChange(CurrentValueChangedEvent event) {

    }
}
