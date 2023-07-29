package pt.com.sibs.order.manager.core.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pt.com.sibs.order.manager.core.events.ErrorEvent;
import pt.com.sibs.order.manager.core.events.LoggingEvent;

@Service
public class LoggingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingService.class);

    @EventListener(value = {LoggingEvent.class})
    public void logEventHandler(LoggingEvent loggingEvent){
        LOGGER.info(loggingEvent.toString());
    }

    @EventListener(value = {ErrorEvent.class})
    public void logEventHandler(ErrorEvent loggingEvent){
        Throwable ex = (Throwable) loggingEvent.getSource();
        LOGGER.error(ex.getMessage(), ex);
    }

}
