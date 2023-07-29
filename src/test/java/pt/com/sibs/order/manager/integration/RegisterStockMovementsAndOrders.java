package pt.com.sibs.order.manager.integration;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import pt.com.sibs.order.manager.controller.dto.item.ItemDTO;
import pt.com.sibs.order.manager.controller.dto.order.DetailsOrderDTO;
import pt.com.sibs.order.manager.controller.dto.order.RegisterOrderDTO;
import pt.com.sibs.order.manager.controller.dto.stock.DetailsStockMovementDTO;
import pt.com.sibs.order.manager.controller.dto.stock.RegisterStockMovementDTO;
import pt.com.sibs.order.manager.controller.dto.user.UserDTO;
import pt.com.sibs.order.manager.model.User;
import pt.com.sibs.order.manager.model.enums.OrderStatus;
import pt.com.sibs.order.manager.service.ItemService;
import pt.com.sibs.order.manager.service.OrderService;
import pt.com.sibs.order.manager.service.StockMovementService;
import pt.com.sibs.order.manager.service.UserService;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RegisterStockMovementsAndOrders {

    @MockBean
    private JavaMailSender javaMailSender;

    @Autowired
    private StockMovementService service;
    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @BeforeAll
    void registerUsersAndItens(){
        ItemDTO dto = new ItemDTO(null, "item");
        dto = itemService.create(dto);

        UserDTO userDTO = new UserDTO(null, "user", "email@email.com");
        userService.create(userDTO);

    }


    @Order(1)
    @Test
    void createItem() {
        RegisterStockMovementDTO rdto = new RegisterStockMovementDTO(1, 10);
        DetailsStockMovementDTO detailsDto = service.addStockItem(rdto);
        Assertions.assertNotNull(detailsDto.getId());
        Assertions.assertEquals(1, detailsDto.getItem().getId());
    }


    @Order(2)
    @Test
    void createOrder() {
        RegisterOrderDTO rdto = new RegisterOrderDTO(1, 10, "email@email.com");
        DetailsOrderDTO detailsDto = orderService.create(rdto);
        Assertions.assertNotNull(detailsDto.getId());
        Assertions.assertEquals(1, detailsDto.getItem().getId());
        Assertions.assertEquals(detailsDto.getOrderStatus(), OrderStatus.WAITING_STOCK);

        RegisterStockMovementDTO rmdto = new RegisterStockMovementDTO(1, 10);
        DetailsStockMovementDTO detailsrmDto = service.addStockItem(rmdto);

        pt.com.sibs.order.manager.model.Order order = this.orderService.getById(detailsDto.getId());
        Assertions.assertEquals(OrderStatus.COMPLETED, order.getOrderStatus());
    }


    @Order(3)
    @Test
    void createOrderWaitingStockCompletion() {
        RegisterOrderDTO rdto = new RegisterOrderDTO(1, 20, "email@email.com");
        DetailsOrderDTO detailsDto = orderService.create(rdto);

        Assertions.assertNotNull(detailsDto.getId());
        Assertions.assertEquals(1, detailsDto.getItem().getId());
        Assertions.assertEquals(detailsDto.getOrderStatus(), OrderStatus.WAITING_STOCK);

        RegisterStockMovementDTO rmdto = new RegisterStockMovementDTO(1, 5);
        DetailsStockMovementDTO detailsrmDto = service.addStockItem(rmdto);

        pt.com.sibs.order.manager.model.Order order = this.orderService.getById(detailsDto.getId());
        Assertions.assertEquals(OrderStatus.WAITING_STOCK, order.getOrderStatus());

        rmdto = new RegisterStockMovementDTO(1, 2);
        service.addStockItem(rmdto);

        order = this.orderService.getById(detailsDto.getId());
        Assertions.assertEquals(OrderStatus.WAITING_STOCK, order.getOrderStatus());

        rmdto = new RegisterStockMovementDTO(1, 50);
        service.addStockItem(rmdto);

        order = this.orderService.getById(detailsDto.getId());
        Assertions.assertEquals(OrderStatus.COMPLETED, order.getOrderStatus());

    }

}
