package pt.com.sibs.order.manager.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import pt.com.sibs.order.manager.controller.dto.order.DetailsOrderDTO;
import pt.com.sibs.order.manager.controller.dto.order.RegisterOrderDTO;
import pt.com.sibs.order.manager.controller.dto.user.UserDTO;
import pt.com.sibs.order.manager.core.exceptions.EntityNotFoundException;
import pt.com.sibs.order.manager.model.Item;
import pt.com.sibs.order.manager.model.Order;
import pt.com.sibs.order.manager.model.User;
import pt.com.sibs.order.manager.model.enums.OrderStatus;
import pt.com.sibs.order.manager.repository.OrderRepository;
import pt.com.sibs.order.manager.validators.OrderValidator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository repository;
    @Mock
    private UserService userService;
    @Mock
    private ItemService itemService;
    @Mock
    private StockOrderDispatcherService orderDispatcherService;
    @Mock
    private OrderValidator orderValidator;

    @Captor
    private ArgumentCaptor<Order> oCaptor;

    @InjectMocks
    private OrderService service;

    @Test
    void create() {
        User user = new User(1,"user","user@email.com", new ArrayList<>());
        Item item = new Item(1,"item", new ArrayList<>(), new ArrayList<>());
        Order registeredOrder = new Order(1, user, item, 10, OrderStatus.WAITING_STOCK, LocalDateTime.now(), new ArrayList<>());
        RegisterOrderDTO rDto = new RegisterOrderDTO(1,10,"user@email.com");

        Mockito.when(this.userService.getByEmail(Mockito.any())).thenReturn(user);
        Mockito.when(this.itemService.getById(Mockito.any())).thenReturn(item);
        Mockito.when(this.repository.save(Mockito.any())).thenReturn(registeredOrder);

        DetailsOrderDTO dto = this.service.create(rDto);
        Assertions.assertEquals(dto.getItem().getId(), registeredOrder.getItem().getId());
        Assertions.assertEquals(dto.getOrderer().getId(), registeredOrder.getUser().getId());
    }

    @Test
    void update() {
        User user = new User(1,"user","user@email.com", new ArrayList<>());
        Item item = new Item(1,"item", new ArrayList<>(), new ArrayList<>());
        Order registeredOrder = new Order(1, user, item, 10, OrderStatus.WAITING_STOCK, LocalDateTime.now(), new ArrayList<>());
        RegisterOrderDTO rDto = new RegisterOrderDTO(1,10,"user@email.com");
        Mockito.when(this.repository.findById(Mockito.anyInt())).thenReturn(Optional.of(registeredOrder));
        Mockito.when(this.userService.getByEmail(Mockito.any())).thenReturn(user);
        Mockito.when(this.itemService.getById(Mockito.any())).thenReturn(item);
        Mockito.when(this.repository.save(Mockito.any())).thenReturn(registeredOrder);
        DetailsOrderDTO dto = this.service.update(1 ,rDto);
        Assertions.assertEquals(dto.getId(), registeredOrder.getId());
        Assertions.assertEquals(dto.getItem().getId(), registeredOrder.getItem().getId());
        Assertions.assertEquals(dto.getOrderer().getId(), registeredOrder.getUser().getId());
    }

    @Test
    void delete() {
        Order order = new Order(1,
                new User(1,"user", "email@email.com", new ArrayList<>()),
                new Item(1, "item", new ArrayList<>(), new ArrayList<>()),
                100,
                OrderStatus.WAITING_STOCK,
                LocalDateTime.now(),
                new ArrayList<>());

        Mockito.when(this.repository.findById(Mockito.any())).thenReturn(Optional.of(order));
        this.service.delete(1);
        Mockito.verify(this.repository).delete(oCaptor.capture());
        Order o = oCaptor.getValue();
        Assertions.assertEquals(order.getId(), o.getId());
        Assertions.assertEquals(order.getQuantity(),o.getQuantity());
        Assertions.assertEquals(order.getItem().getId(), o.getItem().getId());
        Assertions.assertEquals(order.getUser().getId(), o.getUser().getId());
        Assertions.assertEquals(order.getOrderStatus(), o.getOrderStatus());
    }

    @Test
    void deleteError() {
        Mockito.when(this.repository.findById(Mockito.any())).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, ()-> this.service.delete(1));
    }

    @Test
    void getAll() {
        List<Order> orderList = new ArrayList<>();
        Order order = new Order(1,
                new User(1,"user", "email@email.com", new ArrayList<>()),
                new Item(1, "item", new ArrayList<>(), new ArrayList<>()),
                100,
                OrderStatus.WAITING_STOCK,
                LocalDateTime.now(),
                new ArrayList<>());
        orderList.add(order);
        Mockito.when(this.repository.findAll()).thenReturn(orderList);

        List<DetailsOrderDTO> detailsDTOList = this.service.getAll();
        Assertions.assertEquals(order.getId(), detailsDTOList.get(0).getId());
        Assertions.assertEquals(order.getQuantity(),detailsDTOList.get(0).getQuantity());
        Assertions.assertEquals(order.getItem().getId(), detailsDTOList.get(0).getItem().getId());
        Assertions.assertEquals(order.getUser().getId(), detailsDTOList.get(0).getOrderer().getId());
        Assertions.assertEquals(order.getOrderStatus(), detailsDTOList.get(0).getOrderStatus());


    }

    @Test
    void getAllPaged() {
        List<Order> orderList = new ArrayList<>();
        Order order = new Order(1,
                                new User(1,"user", "email@email.com", new ArrayList<>()),
                                new Item(1, "item", new ArrayList<>(), new ArrayList<>()),
                                100,
                                OrderStatus.WAITING_STOCK,
                                LocalDateTime.now(),
                                new ArrayList<>());
        orderList.add(order);
        Mockito.when(this.repository.findAll(Mockito.any(Pageable.class))).thenReturn(new PageImpl<>(orderList));

        Page<DetailsOrderDTO> detailsDTOList = this.service.getAllPaged(PageRequest.of(0,10));
        Assertions.assertEquals(order.getId(), detailsDTOList.getContent().get(0).getId());
        Assertions.assertEquals(order.getQuantity(),detailsDTOList.getContent().get(0).getQuantity());
        Assertions.assertEquals(order.getItem().getId(), detailsDTOList.getContent().get(0).getItem().getId());
        Assertions.assertEquals(order.getUser().getId(), detailsDTOList.getContent().get(0).getOrderer().getId());
        Assertions.assertEquals(order.getOrderStatus(), detailsDTOList.getContent().get(0).getOrderStatus());

    }
}