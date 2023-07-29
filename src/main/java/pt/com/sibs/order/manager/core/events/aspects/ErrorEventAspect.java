package pt.com.sibs.order.manager.core.events.aspects;

import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import pt.com.sibs.order.manager.core.events.EventPublisher;
import pt.com.sibs.order.manager.core.exceptions.DataIntegrityException;
import pt.com.sibs.order.manager.core.exceptions.EntityNotFoundException;
import pt.com.sibs.order.manager.core.exceptions.NegocialException;

import java.util.Arrays;
import java.util.List;

@Component
@Aspect
@AllArgsConstructor
public class ErrorEventAspect {

    private static final List<String> MANAGED_EXCEPTIONS = Arrays.asList(EntityNotFoundException.class.getName(),
                                                                            NegocialException.class.getName(),
                                                                            DataIntegrityException.class.getName(),
                                                                            MethodArgumentNotValidException.class.getName());
    private EventPublisher eventPublisher;

    @AfterThrowing(value = "execution(* pt.com.sibs.order.manager..*.*(..)))", throwing = "ex")
    public void riseError(JoinPoint joinPoint, Throwable ex){
        if(!MANAGED_EXCEPTIONS.contains(ex.getClass().getName())){
            eventPublisher.publishErrorEvent(ex);
        }
    }
}
