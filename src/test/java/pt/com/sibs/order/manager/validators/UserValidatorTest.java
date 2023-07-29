package pt.com.sibs.order.manager.validators;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.com.sibs.order.manager.core.exceptions.DataIntegrityException;
import pt.com.sibs.order.manager.model.Order;
import pt.com.sibs.order.manager.model.User;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserValidatorTest {

    @InjectMocks
    private UserValidator validator;

    @Test
    void validateDeleteError() {
        Order order = new Order();
        List<Order> orderList = new ArrayList<>();
        orderList.add(order);
        User user = User
                    .builder()
                    .id(1)
                    .name("name")
                    .email("email@email.com")
                    .orders(orderList)
                    .build();
        Assertions.assertThrows(DataIntegrityException.class,
                ()-> validator.validateDelete(user));
    }

    @Test
    void validateDeleteSuccess() {
        Order order = new Order();
        List<Order> orderList = new ArrayList<>();
        orderList.add(order);
        User user = User
                .builder()
                .id(1)
                .name("name")
                .email("email@email.com")
                .orders(null)
                .build();
        Assertions.assertDoesNotThrow(()-> validator.validateDelete(user));
    }
}