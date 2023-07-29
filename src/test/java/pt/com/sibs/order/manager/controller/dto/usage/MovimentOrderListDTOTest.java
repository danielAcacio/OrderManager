package pt.com.sibs.order.manager.controller.dto.usage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.com.sibs.order.manager.controller.dto.order.DetailsOrderDTO;
import pt.com.sibs.order.manager.model.*;
import pt.com.sibs.order.manager.model.enums.MovementType;
import pt.com.sibs.order.manager.model.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MovimentOrderListDTOTest {

    @Test
    void build() {
        LocalDateTime date = LocalDateTime.now();
        List<OrderStockMovementUsage> orderList = new ArrayList<>();
        StockMovement movement = new StockMovement(1,
                                                    date,
                                                    Item.builder().id(1).name("Item").build(),
                                                    10,
                                                    MovementType.INPUT,
                                                    new ArrayList<>());
        Order order = new Order(1,
                User.builder().id(1).name("user").email("email").build(),
                Item.builder().id(2).name("name").build(),
                15,
                OrderStatus.WAITING_STOCK,
                date,
                new ArrayList<>());

        OrderStockMovementUsage osmu = new OrderStockMovementUsage();
        osmu.setId(1);
        osmu.setQuantity(2);
        osmu.setOrder(order);
        osmu.setStockMovement(movement);

        orderList.add(osmu);
        MovimentOrderListDTO dto = new MovimentOrderListDTO().build(movement, orderList);
        Assertions.assertEquals(movement.getId(), dto.getMovement().getId());
        Assertions.assertEquals(orderList.size(), dto.getOrders().size());

    }

}