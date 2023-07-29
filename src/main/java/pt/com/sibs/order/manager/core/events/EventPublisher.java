package pt.com.sibs.order.manager.core.events;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import pt.com.sibs.order.manager.model.Item;
import pt.com.sibs.order.manager.model.interfaces.PersistentObject;

@Component
@AllArgsConstructor
public class EventPublisher {
    private ApplicationEventPublisher eventPublisher;

    public void publishLogEvent(String action, PersistentObject obj){
        eventPublisher.publishEvent(new LoggingEvent(action, obj));
    }

    public void publishStockMovementEvent(Item item){
        eventPublisher.publishEvent(new StockMovementEvent(item));
    }

    public void publishErrorEvent(Throwable error){
        eventPublisher.publishEvent(new ErrorEvent(error));
    }

}
