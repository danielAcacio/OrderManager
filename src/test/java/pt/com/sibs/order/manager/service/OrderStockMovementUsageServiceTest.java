package pt.com.sibs.order.manager.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.com.sibs.order.manager.controller.dto.usage.MovimentOrderListDTO;
import pt.com.sibs.order.manager.controller.dto.usage.OrderMovementListDTO;
import pt.com.sibs.order.manager.model.*;
import pt.com.sibs.order.manager.model.enums.MovementType;
import pt.com.sibs.order.manager.model.enums.OrderStatus;
import pt.com.sibs.order.manager.repository.OrderStockMovementUsageRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderStockMovementUsageServiceTest {

    @Mock
    private OrderStockMovementUsageRepository repository;
    @Mock
    private OrderService orderService;
    @Mock
    private StockMovementService stockMovementService;

    @InjectMocks
    private OrderStockMovementUsageService service;

    @Test
    void getByOrder() {
        Order order = new Order(1,
                new User(1,"user", "email@email.com", new ArrayList<>()),
                new Item(1, "item", new ArrayList<>(), new ArrayList<>()),
                100,
                OrderStatus.WAITING_STOCK,
                LocalDateTime.now(),
                new ArrayList<>());
        StockMovement registeredMovement = new StockMovement(1,
                LocalDateTime.now(),
                new Item(1, "item", new ArrayList<>(), new ArrayList<>()),
                100,
                MovementType.INPUT,
                new ArrayList<>());

        OrderStockMovementUsage usage = new OrderStockMovementUsage(1,order,registeredMovement,1);
        List<OrderStockMovementUsage> orderStockMovementUsages = new ArrayList<>();
        orderStockMovementUsages.add(usage);

        Mockito.when(this.orderService.getById(Mockito.anyInt())).thenReturn(order);
        Mockito.when(this.repository.getByOrderId(Mockito.anyInt())).thenReturn(orderStockMovementUsages);

        OrderMovementListDTO dto = this.service.getByOrder(1);
        Assertions.assertEquals(order.getId(), dto.getOrder().getId());
        Assertions.assertEquals(order.getOrderStatus(), dto.getOrder().getOrderStatus());
        Assertions.assertEquals(order.getItem().getId(), dto.getOrder().getItem().getId());
        Assertions.assertEquals(usage.getId(), dto.getMovements().get(0).getId());
    }

    @Test
    void getByMovement() {
        Order order = new Order(1,
                new User(1,"user", "email@email.com", new ArrayList<>()),
                new Item(1, "item", new ArrayList<>(), new ArrayList<>()),
                100,
                OrderStatus.WAITING_STOCK,
                LocalDateTime.now(),
                new ArrayList<>());
        StockMovement registeredMovement = new StockMovement(1,
                LocalDateTime.now(),
                new Item(1, "item", new ArrayList<>(), new ArrayList<>()),
                100,
                MovementType.INPUT,
                new ArrayList<>());

        OrderStockMovementUsage usage = new OrderStockMovementUsage(1,order,registeredMovement,1);
        List<OrderStockMovementUsage> orderStockMovementUsages = new ArrayList<>();
        orderStockMovementUsages.add(usage);

        Mockito.when(this.stockMovementService.getById(Mockito.anyInt())).thenReturn(registeredMovement);
        Mockito.when(this.repository.getByMovementId(Mockito.anyInt())).thenReturn(orderStockMovementUsages);

        MovimentOrderListDTO dto = this.service.getByMovement(1);

        Assertions.assertEquals(registeredMovement.getId(), dto.getMovement().getId());
        Assertions.assertEquals(registeredMovement.getMovementType(), dto.getMovement().getMovementType());
        Assertions.assertEquals(registeredMovement.getItem().getId(), dto.getMovement().getItem().getId());
        Assertions.assertEquals(usage.getOrder().getId(), dto.getOrders().get(0).getId());

    }
}