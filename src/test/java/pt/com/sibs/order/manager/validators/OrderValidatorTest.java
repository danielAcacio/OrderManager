package pt.com.sibs.order.manager.validators;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.com.sibs.order.manager.controller.dto.order.DetailsOrderDTO;
import pt.com.sibs.order.manager.controller.dto.order.RegisterOrderDTO;
import pt.com.sibs.order.manager.core.exceptions.NegocialException;
import pt.com.sibs.order.manager.model.*;
import pt.com.sibs.order.manager.model.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class OrderValidatorTest {

    @InjectMocks
    private OrderValidator validator;

    @Test
    void validateDeleteError() {
        Order order = Order
                    .builder()
                    .user(new User())
                    .creationDate(LocalDateTime.now())
                    .orderStatus(OrderStatus.COMPLETED)
                .quantity(10)
                    .build();

        Assertions.assertThrows(NegocialException.class,
                ()-> this.validator.validateDelete(order));
        order.setOrderStatus(OrderStatus.WAITING_STOCK);

        List<OrderStockMovementUsage> usages = new ArrayList<>();
        usages.add(new OrderStockMovementUsage(1,  order, new StockMovement(),1));

        order.setMovements(usages);
        Assertions.assertThrows(NegocialException.class,
                ()-> this.validator.validateDelete(order));
    }

    @Test
    void validateDeleteSuccess() {
        Order order = Order
                .builder()
                .user(new User())
                .creationDate(LocalDateTime.now())
                .orderStatus(OrderStatus.WAITING_STOCK)
                .quantity(10)
                .movements(new ArrayList<>())
                .build();

        Assertions.assertDoesNotThrow(()-> this.validator.validateDelete(order));
    }


    @Test
    void validateUpdateError() {
        RegisterOrderDTO dto = new RegisterOrderDTO(1, 2, "email@email.com");
        Order order = Order
                .builder()
                .user(new User())
                .item(Item.builder().id(1).build())
                .creationDate(LocalDateTime.now())
                .orderStatus(OrderStatus.COMPLETED)
                .quantity(10)
                .build();

        Assertions.assertThrows(NegocialException.class,
                ()-> this.validator.validateUpdate(order,dto));
        order.setOrderStatus(OrderStatus.WAITING_STOCK);

        List<OrderStockMovementUsage> usages = new ArrayList<>();
        usages.add(new OrderStockMovementUsage(1,  order, new StockMovement(),5));

        order.setMovements(usages);
        dto.setQuantity(6);
        dto.setItem(30);
        Assertions.assertThrows(NegocialException.class,
                ()-> this.validator.validateUpdate(order,dto));
    }

    @Test
    void validateUpdate() {
        RegisterOrderDTO dto = new RegisterOrderDTO(1, 2, "email@email.com");
        Order order = Order
                .builder()
                .user(new User())
                .item(Item.builder().id(1).build())
                .creationDate(LocalDateTime.now())
                .orderStatus(OrderStatus.WAITING_STOCK)
                .movements(new ArrayList<>())
                .quantity(10)
                .build();

        Assertions.assertDoesNotThrow(()-> this.validator.validateUpdate(order,dto));

        List<OrderStockMovementUsage> usages = new ArrayList<>();
        usages.add(new OrderStockMovementUsage(1,  order, new StockMovement(),1));

        order.setMovements(usages);
        Assertions.assertDoesNotThrow(()-> this.validator.validateUpdate(order,dto));
    }
}