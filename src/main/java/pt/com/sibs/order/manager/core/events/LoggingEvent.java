package pt.com.sibs.order.manager.core.events;

import org.springframework.context.ApplicationEvent;
import pt.com.sibs.order.manager.model.interfaces.PersistentObject;

public class LoggingEvent extends ApplicationEvent {

    private String actionEvent;
    private PersistentObject logObject;

    public LoggingEvent(String action, PersistentObject source) {
        super(source);
        this.actionEvent = action;
        this.logObject = source;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("\n Action:" + this.actionEvent);
        sb.append(this.logObject.toString());
        return sb.toString();
    }
}
