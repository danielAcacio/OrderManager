package pt.com.sibs.order.manager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pt.com.sibs.order.manager.controller.dto.item.ItemDTO;
import pt.com.sibs.order.manager.controller.dto.order.DetailsOrderDTO;
import pt.com.sibs.order.manager.controller.dto.order.RegisterOrderDTO;
import pt.com.sibs.order.manager.controller.dto.user.UserDTO;
import pt.com.sibs.order.manager.core.exceptions.EntityNotFoundException;
import pt.com.sibs.order.manager.model.Item;
import pt.com.sibs.order.manager.model.Order;
import pt.com.sibs.order.manager.model.User;
import pt.com.sibs.order.manager.model.enums.OrderStatus;
import pt.com.sibs.order.manager.service.OrderService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(OrderContoller.class)
class OrderContollerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    void createOrder() throws Exception{
        RegisterOrderDTO dto = new RegisterOrderDTO(1, 15, "email@email.com");
        DetailsOrderDTO detailsDto = new DetailsOrderDTO(1,
                new UserDTO().build(User.builder().id(2).name("user").email("email@email.com").build()),
                new ItemDTO().build(Item.builder().id(3).name("item").build()),
                10,
                OrderStatus.WAITING_STOCK,
                0,
                LocalDateTime.now());

        Mockito.when(this.orderService.create(Mockito.any()))
                .thenReturn(detailsDto);

        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.item.id").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderer.id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderStatus").value("WAITING_STOCK"));
    }

    @Test
    void createOrderError() throws Exception {
        RegisterOrderDTO dto = new RegisterOrderDTO(1, -2, null);
        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));
    }


    @Test
    void updateOrder() throws Exception{
        RegisterOrderDTO dto = new RegisterOrderDTO(1, 15, "email@email.com");
        DetailsOrderDTO detailsDto = new DetailsOrderDTO(1,
                new UserDTO().build(User.builder().id(2).name("user").email("email@email.com").build()),
                new ItemDTO().build(Item.builder().id(3).name("item").build()),
                10,
                OrderStatus.WAITING_STOCK,
                0,
                LocalDateTime.now());

        Mockito.when(this.orderService.update(Mockito.any(), Mockito.any()))
                .thenReturn(detailsDto);

        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.put("/order/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.item.id").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderer.id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderStatus").value("WAITING_STOCK"));
    }

    @Test
    void updateOrderError() throws Exception {
        RegisterOrderDTO dto = new RegisterOrderDTO(1, -2, null);
        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.put("/order/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));
    }


    @Test
    void deleteOrder() throws Exception {
        Mockito.when(this.orderService.delete(Mockito.any()))
                .thenReturn(new Order());
        mockMvc.perform(MockMvcRequestBuilders.delete("/order/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.NO_CONTENT.value()));
    }

    @Test
    void deleteOrderError() throws Exception {
        Mockito.when(this.orderService.delete(Mockito.any()))
                .thenThrow(EntityNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.delete("/order/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.NOT_FOUND.value()));
    }


    @Test
    void listOrder() throws Exception{
        DetailsOrderDTO dto = new DetailsOrderDTO(1,
                new UserDTO().build(User.builder().id(2).name("user").email("email@email.com").build()),
                new ItemDTO().build(Item.builder().id(3).name("item").build()),
                10,
                OrderStatus.WAITING_STOCK,
                0,
                LocalDateTime.now());
        List<DetailsOrderDTO> orderDTOList= new ArrayList<>();
        orderDTOList.add(dto);
        Mockito.when(this.orderService.getAll())
                .thenReturn(orderDTOList);
        mockMvc.perform(MockMvcRequestBuilders.get("/order"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].item.id").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].orderer.id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].orderStatus").value("WAITING_STOCK"));

    }

    @Test
    void listOrderPaged() throws Exception{
        DetailsOrderDTO dto = new DetailsOrderDTO(1,
                new UserDTO().build(User.builder().id(2).name("user").email("email@email.com").build()),
                new ItemDTO().build(Item.builder().id(3).name("item").build()),
                10,
                OrderStatus.WAITING_STOCK,
                0,
                LocalDateTime.now());
        List<DetailsOrderDTO> orderDTOList= new ArrayList<>();
        orderDTOList.add(dto);
        Mockito.when(this.orderService.getAllPaged(Mockito.any()))
                .thenReturn(new PageImpl<>(orderDTOList));
        mockMvc.perform(MockMvcRequestBuilders.get("/order/paged"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.[0].item.id").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.[0].orderer.id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.[0].orderStatus").value("WAITING_STOCK"));
    }
}