package pt.com.sibs.order.manager.core.email;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import pt.com.sibs.order.manager.model.Item;
import pt.com.sibs.order.manager.model.Order;
import pt.com.sibs.order.manager.model.User;
import pt.com.sibs.order.manager.model.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private JavaMailSender javaMailSender;
    @Captor
    ArgumentCaptor<SimpleMailMessage> emailMessage;

    @InjectMocks
    private EmailService emailService;


    @Test
    void sendEmailOrderCompleted() {
        Order order = new Order(1,
                new User(1,"user", "email@email.com", new ArrayList<>()),
                new Item(1, "item", new ArrayList<>(), new ArrayList<>()),
                1,
                OrderStatus.WAITING_STOCK,
                LocalDateTime.now(),
                new ArrayList<>());
        this.emailService.sendEmailOrderCompleted(order)
        ;
        Mockito.verify(this.javaMailSender).send(this.emailMessage.capture());
        SimpleMailMessage message = emailMessage.getValue();

        StringBuilder sb = new StringBuilder("Your order was finished: \n\n");
        sb.append(order.toString());
        sb.append("\n\nFor more details reply this e-mail.");
        Assertions.assertEquals(sb.toString(), message.getText());

    }
}