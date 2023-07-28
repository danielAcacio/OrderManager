package pt.com.sibs.order.manager.core.events.aspects;

import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import pt.com.sibs.order.manager.core.events.EventPublisher;
import pt.com.sibs.order.manager.core.events.annotations.LoggedAction;
import pt.com.sibs.order.manager.controller.dto.interfaces.ParseableDTO;
import pt.com.sibs.order.manager.model.interfaces.PersistentObject;

@Component
@Aspect
@AllArgsConstructor
public class LogginEventsAspect {

    private EventPublisher eventPublisher;

    @AfterReturning(pointcut = "@annotation(pt.com.sibs.order.manager.core.events.annotations.LoggedAction)", returning = "returnValue")
    public void riseLoggingEvent(JoinPoint joinPoint, Object returnValue){

        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        LoggedAction annotation = signature.getMethod().getAnnotation(LoggedAction.class);
        String action = annotation.action();

        PersistentObject obj = returnValue instanceof ParseableDTO?
                ((ParseableDTO)returnValue).parse():
                (PersistentObject) returnValue;

        eventPublisher.publishLogEvent(action, obj);
    }


}
