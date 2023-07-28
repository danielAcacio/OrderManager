package pt.com.sibs.order.manager.core.events;

import org.springframework.context.ApplicationEvent;
import pt.com.sibs.order.manager.model.Item;

public class StockMovementEvent extends ApplicationEvent {
    private Item item;

    public StockMovementEvent(Item source) {
        super(source);
        this.item = source;
    }
}
