package pt.com.sibs.order.manager.core.events.aspects;

import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import pt.com.sibs.order.manager.core.events.EventPublisher;
import pt.com.sibs.order.manager.controller.dto.interfaces.ParseableDTO;
import pt.com.sibs.order.manager.model.interfaces.StockItemHandler;

@Component
@Aspect
@AllArgsConstructor
public class StockOperationAspect {

    private EventPublisher eventPublisher;

    @AfterReturning(pointcut = "@annotation(pt.com.sibs.order.manager.core.events.annotations.StockOperation)", returning = "returnValue")
    public void riseStockEvent(JoinPoint joinPoint, Object returnValue) {

        StockItemHandler itemHandler = (StockItemHandler) ((ParseableDTO) returnValue).parse();
        eventPublisher.publishStockMovementEvent(itemHandler.getItem());
    }
}
