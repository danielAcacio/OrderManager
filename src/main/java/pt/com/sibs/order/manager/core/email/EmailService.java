package pt.com.sibs.order.manager.core.email;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pt.com.sibs.order.manager.model.Order;

@Service
@AllArgsConstructor
public class EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    private JavaMailSender javaMailSender;

    @Async
    public void sendEmailOrderCompleted(Order order){
        StringBuilder sb = new StringBuilder("Your order was finished: \n\n");
        sb.append(order.toString());
        sb.append("\n\nFor more details reply this e-mail.");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@email.com");
        message.setTo(order.getUser().getEmail());
        message.setSubject("Order completion notification");
        message.setText(sb.toString());
        javaMailSender.send(message);

        LOGGER.info("Email sent to: "+order.getUser().getEmail()+" to notify finishing od order: "+ order.getId());

    }
}
