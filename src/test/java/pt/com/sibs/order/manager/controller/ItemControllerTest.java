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
import pt.com.sibs.order.manager.controller.dto.user.UserDTO;
import pt.com.sibs.order.manager.core.exceptions.EntityNotFoundException;
import pt.com.sibs.order.manager.model.Item;
import pt.com.sibs.order.manager.model.User;
import pt.com.sibs.order.manager.service.ItemService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(ItemController.class)
class ItemControllerTest {

    @MockBean
    private ItemService itemService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createItem() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        ItemDTO dto = new ItemDTO(1,"item");
        Mockito.when(this.itemService.create(Mockito.any()))
                .thenReturn(dto);
        mockMvc.perform(MockMvcRequestBuilders.post("/item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

    @Test
    void createItemError() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        ItemDTO dto = new ItemDTO(1,null);
        Mockito.when(this.itemService.create(Mockito.any()))
                .thenReturn(dto);
        mockMvc.perform(MockMvcRequestBuilders.post("/item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void updateItem() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ItemDTO dto = new ItemDTO(1,"item");
        Mockito.when(this.itemService.update(Mockito.any(), Mockito.any()))
                .thenReturn(dto);
        mockMvc.perform(MockMvcRequestBuilders.put("/item/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));


    }

    @Test
    void updateItemError() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        ItemDTO dto = new ItemDTO(1,null);
        Mockito.when(this.itemService.update(Mockito.any(), Mockito.any()))
                .thenReturn(dto);
        mockMvc.perform(MockMvcRequestBuilders.put("/item/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void deleteItem() throws Exception{
        Mockito.when(this.itemService.delete(Mockito.any()))
                .thenReturn(Item.builder().build());
        mockMvc.perform(MockMvcRequestBuilders.delete("/item/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.NO_CONTENT.value()));
    }

    @Test
    void deleteItemErro() throws Exception {
        Mockito.when(this.itemService.delete(Mockito.any()))
                .thenThrow(EntityNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.delete("/item/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.NOT_FOUND.value()));
    }


    @Test
    void listItem() throws Exception{
        ItemDTO dto = new ItemDTO(1,"item");
        List<ItemDTO> itemDTOList= new ArrayList<>();
        itemDTOList.add(dto);
        Mockito.when(this.itemService.getAll())
                .thenReturn(itemDTOList);
        mockMvc.perform(MockMvcRequestBuilders.get("/item"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").value("item"));}

    @Test
    void listItemPaged() throws Exception{
        ItemDTO dto = new ItemDTO(1,"item");
        List<ItemDTO> itemDTOList= new ArrayList<>();
        itemDTOList.add(dto);
        Mockito.when(this.itemService.getAllPaged(Mockito.any()))
                .thenReturn(new PageImpl<>(itemDTOList));
        mockMvc.perform(MockMvcRequestBuilders.get("/item/paged"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.[0].name").value("item"));
    }
}