package pt.com.sibs.order.manager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pt.com.sibs.order.manager.controller.dto.user.UserDTO;
import pt.com.sibs.order.manager.core.exceptions.DataIntegrityException;
import pt.com.sibs.order.manager.core.exceptions.EntityNotFoundException;
import pt.com.sibs.order.manager.core.exceptions.NegocialException;
import pt.com.sibs.order.manager.service.UserService;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest({ExceptionHandlerController.class, UserController.class})
class ExceptionHandlerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void entityNotFoundHandler() throws Exception {
        Mockito.when(this.userService.delete(Mockito.any()))
                .thenThrow(EntityNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.delete("/user/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void negocialExceptionsHandler() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        UserDTO dto = new UserDTO(1,"user", "email@email.com");
        Mockito.when(this.userService.create(Mockito.any()))
                .thenThrow(NegocialException.class);
        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.CONFLICT.value()));
    }

    @Test
    void dataIntegrityExceptionsHandler() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        UserDTO dto = new UserDTO(1,"user", "email@email.com");
        Mockito.when(this.userService.create(Mockito.any()))
                .thenThrow(DataIntegrityException.class);
        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.CONFLICT.value()));
    }

    @Test
    void handleMethodArgumentNotValid() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        UserDTO dto = new UserDTO(1,null, null);
        Mockito.when(this.userService.create(Mockito.any()))
                .thenReturn(dto);
        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));
    }
}