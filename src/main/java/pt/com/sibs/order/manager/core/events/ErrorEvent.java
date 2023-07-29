package pt.com.sibs.order.manager.core.events;

import org.springframework.context.ApplicationEvent;

public class ErrorEvent extends ApplicationEvent {
    public ErrorEvent(Throwable source) {
        super(source);
    }
}
