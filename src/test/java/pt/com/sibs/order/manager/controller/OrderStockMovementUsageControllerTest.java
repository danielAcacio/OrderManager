package pt.com.sibs.order.manager.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pt.com.sibs.order.manager.controller.dto.usage.MovimentOrderListDTO;
import pt.com.sibs.order.manager.controller.dto.usage.OrderMovementListDTO;
import pt.com.sibs.order.manager.controller.dto.user.UserDTO;
import pt.com.sibs.order.manager.service.OrderStockMovementUsageService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(OrderStockMovementUsageController.class)
class OrderStockMovementUsageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderStockMovementUsageService orderStockMovementUsageService;

    @Test
    void getByOrder()throws Exception{

        OrderMovementListDTO dto = new OrderMovementListDTO();
        Mockito.when(this.orderStockMovementUsageService.getByOrder(Mockito.any()))
                .thenReturn(dto);


        mockMvc.perform(MockMvcRequestBuilders.get("/stock/order/1/moviments"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()));
    }

    @Test
    void getByMovement() throws Exception{
        MovimentOrderListDTO dto = new MovimentOrderListDTO();
        Mockito.when(this.orderStockMovementUsageService.getByMovement(Mockito.any()))
                .thenReturn(dto);

        Mockito.when(this.orderStockMovementUsageService.getByMovement(Mockito.any()))
                .thenReturn(dto);
        mockMvc.perform(MockMvcRequestBuilders.get("/stock/movement/1/orders"))
                .andDo(MockMvcResultHandlers.print());
    }
}