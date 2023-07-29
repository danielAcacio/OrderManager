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
import pt.com.sibs.order.manager.controller.dto.order.RegisterOrderDTO;
import pt.com.sibs.order.manager.controller.dto.stock.DetailsStockMovementDTO;
import pt.com.sibs.order.manager.controller.dto.stock.RegisterStockMovementDTO;
import pt.com.sibs.order.manager.controller.dto.user.UserDTO;
import pt.com.sibs.order.manager.core.exceptions.EntityNotFoundException;
import pt.com.sibs.order.manager.model.Item;
import pt.com.sibs.order.manager.model.StockMovement;
import pt.com.sibs.order.manager.model.User;
import pt.com.sibs.order.manager.model.enums.MovementType;
import pt.com.sibs.order.manager.service.StockMovementService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(StockMovementController.class)
class StockMovementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StockMovementService stockMovementService;


    @Test
    void inputStockUnits() throws Exception {
        RegisterStockMovementDTO dto=new RegisterStockMovementDTO(1, 10);
        DetailsStockMovementDTO detailsDto = new DetailsStockMovementDTO(1,
                new ItemDTO().build(Item.builder().id(1).name("item").build()),
                100,
                MovementType.INPUT,
                LocalDateTime.now());
        Mockito.when(this.stockMovementService.addStockItem(dto)).thenReturn(detailsDto);
        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.post("/stock/movement/add/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()));


    }


    @Test
    void inputStockUnitsError() throws Exception {
        RegisterStockMovementDTO dto=new RegisterStockMovementDTO(-1, -10);
        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.post("/stock/movement/add/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));

    }

    @Test
    void updateUnitsError() throws Exception {
        RegisterStockMovementDTO dto=new RegisterStockMovementDTO(-1, -10);
        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.put("/stock/movement/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));

    }


    @Test
    void updateMovement() throws Exception {
        RegisterStockMovementDTO dto=new RegisterStockMovementDTO(1, 10);
        DetailsStockMovementDTO detailsDto = new DetailsStockMovementDTO(1,
                new ItemDTO().build(Item.builder().id(1).name("item").build()),
                100,
                MovementType.INPUT,
                LocalDateTime.now());
        Mockito.when(this.stockMovementService.update(Mockito.any(), Mockito.any())).thenReturn(detailsDto);
        ObjectMapper mapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.put("/stock/movement/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.item.name").value("item"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.movementType").value("INPUT"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(100));


    }

    @Test
    void deleteMovement() throws Exception {
        Mockito.when(this.stockMovementService.delete(Mockito.any()))
                .thenReturn(new StockMovement());
        mockMvc.perform(MockMvcRequestBuilders.delete("/stock/movement/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.NO_CONTENT.value()));
    }


    @Test
    void deleteMovementError() throws Exception{
        Mockito.when(this.stockMovementService.delete(Mockito.any()))
                .thenThrow(EntityNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.delete("/stock/movement/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void listStockMovement() throws Exception{
        DetailsStockMovementDTO dto = new DetailsStockMovementDTO(1,
                new ItemDTO().build(Item.builder().id(1).name("item").build()),
                100,
                MovementType.INPUT,
                LocalDateTime.now());

        List<DetailsStockMovementDTO> detailsDTOList= new ArrayList<>();
        detailsDTOList.add(dto);
        Mockito.when(this.stockMovementService.getAll())
                .thenReturn(detailsDTOList);
        mockMvc.perform(MockMvcRequestBuilders.get("/stock/movement"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].item.name").value("item"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].movementType").value("INPUT"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].quantity").value(100));
    }

    @Test
    void listStockMovementPaged() throws Exception{
            DetailsStockMovementDTO dto = new DetailsStockMovementDTO(1,
                    new ItemDTO().build(Item.builder().id(1).name("item").build()),
                    100,
                    MovementType.INPUT,
                    LocalDateTime.now());

            List<DetailsStockMovementDTO> detailsDTOList= new ArrayList<>();
            detailsDTOList.add(dto);
            Mockito.when(this.stockMovementService.getAllPaged(Mockito.any()))
                    .thenReturn(new PageImpl<>(detailsDTOList));
            mockMvc.perform(MockMvcRequestBuilders.get("/stock/movement/paged"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content.[0].id").value(1))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content.[0].item.name").value("item"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content.[0].movementType").value("INPUT"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.content.[0].quantity").value(100));
    }
}