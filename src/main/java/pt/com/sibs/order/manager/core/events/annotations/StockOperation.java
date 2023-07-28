package pt.com.sibs.order.manager.core.events.annotations;

import pt.com.sibs.order.manager.model.enums.MovementType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface StockOperation {
    public MovementType value();
}
