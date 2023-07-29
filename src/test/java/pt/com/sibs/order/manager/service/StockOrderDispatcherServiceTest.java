package pt.com.sibs.order.manager.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.com.sibs.order.manager.core.email.EmailService;
import pt.com.sibs.order.manager.core.events.EventPublisher;
import pt.com.sibs.order.manager.model.Item;
import pt.com.sibs.order.manager.model.Order;
import pt.com.sibs.order.manager.model.StockItemCounter;
import pt.com.sibs.order.manager.model.User;
import pt.com.sibs.order.manager.model.enums.OrderStatus;
import pt.com.sibs.order.manager.repository.OrderRepository;
import pt.com.sibs.order.manager.repository.OrderStockMovementUsageRepository;
import pt.com.sibs.order.manager.repository.StockItemCounterRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StockOrderDispatcherServiceTest {

    @Mock
    private StockItemCounterRepository repository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderStockMovementUsageRepository orderSctockMovementUsage;
    @Mock
    private EmailService emailService;
    @Mock
    private EventPublisher publisher;

    @InjectMocks
    private StockOrderDispatcherService orderDispatcherService;


    @Test
    void dispatchOrdersWithOutStock() {
        StockItemCounter counter = new StockItemCounter(1, 1,0);
        List<StockItemCounter> counters = new ArrayList<>();
        counters.add(counter);

        Mockito.when(this.repository.findByItemIdEquals(Mockito.anyInt())).thenReturn(counters);
        List<Order> orderList = this.getOrdersToTest();

        this.orderDispatcherService.dispatchOrders(orderList);
        Assertions.assertEquals(0L, orderList.stream().filter(o->o.getOrderStatus().equals(OrderStatus.COMPLETED)).count());
    }

    @Test
    void dispatchOrdersWithOneItemStock() {
        StockItemCounter counter = new StockItemCounter(1, 1,1);
        List<StockItemCounter> counters = new ArrayList<>();
        counters.add(counter);

        Mockito.when(this.repository.findByItemIdEquals(Mockito.anyInt())).thenReturn(counters);
        List<Order> orderList = this.getOrdersToTest();

        this.orderDispatcherService.dispatchOrders(orderList);
        Assertions.assertEquals(1L, orderList.stream().filter(o->o.getOrderStatus().equals(OrderStatus.COMPLETED)).count());
    }

    @Test
    void dispatchOrdersWithTwoItemStock() {
        StockItemCounter counter = new StockItemCounter(1, 1,2);
        List<StockItemCounter> counters = new ArrayList<>();
        counters.add(counter);

        Mockito.when(this.repository.findByItemIdEquals(Mockito.anyInt())).thenReturn(counters);
        List<Order> orderList = this.getOrdersToTest();

        this.orderDispatcherService.dispatchOrders(orderList);
        Assertions.assertEquals(1L, orderList.stream().filter(o->o.getOrderStatus().equals(OrderStatus.COMPLETED)).count());
    }

    @Test
    void dispatchOrdersWithThreeItemStock() {
        StockItemCounter counter1 = new StockItemCounter(1, 1,2);
        StockItemCounter counter2 = new StockItemCounter(1, 1,2);
        List<StockItemCounter> counters = new ArrayList<>();
        counters.add(counter1);
        counters.add(counter2);

        Mockito.when(this.repository.findByItemIdEquals(Mockito.anyInt())).thenReturn(counters);
        List<Order> orderList = this.getOrdersToTest();

        this.orderDispatcherService.dispatchOrders(orderList);
        Assertions.assertEquals(2L, orderList.stream().filter(o->o.getOrderStatus().equals(OrderStatus.COMPLETED)).count());
        Assertions.assertEquals(1,orderList.get(0).getMovements().size());
        Assertions.assertEquals(2,orderList.get(1).getMovements().size());
        Assertions.assertEquals(2, orderList.get(2).getRemainUnitsToFullFill());
    }


    private List<Order> getOrdersToTest(){
        List<Order> orderList = new ArrayList<>();
        Order order1 = new Order(1,
                new User(1,"user", "email@email.com", new ArrayList<>()),
                new Item(1, "item", new ArrayList<>(), new ArrayList<>()),
                1,
                OrderStatus.WAITING_STOCK,
                LocalDateTime.now(),
                new ArrayList<>());

        Order order2 = new Order(2,
                new User(1,"user", "email@email.com", new ArrayList<>()),
                new Item(1, "item", new ArrayList<>(), new ArrayList<>()),
                2,
                OrderStatus.WAITING_STOCK,
                LocalDateTime.now(),
                new ArrayList<>());


        Order order3 = new Order(3,
                new User(1,"user", "email@email.com", new ArrayList<>()),
                new Item(1, "item", new ArrayList<>(), new ArrayList<>()),
                3,
                OrderStatus.WAITING_STOCK,
                LocalDateTime.now(),
                new ArrayList<>());

        orderList.add(order1);
        orderList.add(order2);
        orderList.add(order3);
        return orderList;
    }
}