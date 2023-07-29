package pt.com.sibs.order.manager.controller.dto.order;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pt.com.sibs.order.manager.controller.dto.item.ItemDTO;
import pt.com.sibs.order.manager.controller.dto.user.UserDTO;
import pt.com.sibs.order.manager.model.Item;
import pt.com.sibs.order.manager.model.Order;
import pt.com.sibs.order.manager.model.User;
import pt.com.sibs.order.manager.model.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DetailsOrderDTOTest {

    @Test
    void parse() {
        DetailsOrderDTO dto = new DetailsOrderDTO();
        LocalDateTime date = LocalDateTime.now();

        dto.setId(1);
        dto.setItem(new ItemDTO().build(Item.builder().id(1).name("item").build()));
        dto.setOrderer(new UserDTO().build(User.builder().id(2).name("user").email("email").build()));
        dto.setOrderStatus(OrderStatus.WAITING_STOCK);
        dto.setCreationDate(date);
        dto.setQuantity(10);
        dto.setCreationDate(date);
        dto.setCurrentCompletion(0);


        Order order = dto.parse();

        Assertions.assertEquals(order.getId(), dto.getId());
        Assertions.assertEquals(order.getQuantity(), dto.getQuantity());
        Assertions.assertEquals(order.getOrderStatus(), dto.getOrderStatus());
        Assertions.assertEquals(order.getItem().getId(), dto.getItem().getId());
        Assertions.assertEquals(order.getUser().getId(), dto.getOrderer().getId());
        Assertions.assertEquals(order.getQuantity() - order.getRemainUnitsToFullFill(), dto.getCurrentCompletion());

    }

    @Test
    void build() {
        LocalDateTime date = LocalDateTime.now();
        Order order = new Order();
        order.setId(1);
        order.setItem(Item.builder().id(1).name("item").build());
        order.setUser(User.builder().id(2).name("user").email("email").build());
        order.setOrderStatus(OrderStatus.WAITING_STOCK);
        order.setQuantity(10);
        order.setCreationDate(date);
        order.setMovements(new ArrayList<>());

        DetailsOrderDTO dto = new DetailsOrderDTO().build(order);
        Assertions.assertEquals(order.getId(), dto.getId());
        Assertions.assertEquals(order.getQuantity(), dto.getQuantity());
        Assertions.assertEquals(order.getOrderStatus(), dto.getOrderStatus());
        Assertions.assertEquals(order.getItem().getId(), dto.getItem().getId());
        Assertions.assertEquals(order.getUser().getId(), dto.getOrderer().getId());
    }
}