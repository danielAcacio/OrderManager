package pt.com.sibs.order.manager.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
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
import pt.com.sibs.order.manager.controller.dto.user.UserDTO;
import pt.com.sibs.order.manager.core.exceptions.EntityNotFoundException;
import pt.com.sibs.order.manager.model.User;
import pt.com.sibs.order.manager.service.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;



    @Test
    void createUser() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        UserDTO dto = new UserDTO(1,"user", "email@email.com");
        Mockito.when(this.userService.create(Mockito.any()))
                .thenReturn(dto);
        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

    @Test
    void createUserError() throws Exception {
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

    @Test
    void updateUser() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        UserDTO dto = new UserDTO(1,"user", "email@email.com");
        Mockito.when(this.userService.update(Mockito.any(),Mockito.any()))
                .thenReturn(dto);
        mockMvc.perform(MockMvcRequestBuilders.put("/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }


    @Test
    void updateUserError() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        UserDTO dto = new UserDTO(1,null, null);
        Mockito.when(this.userService.update(Mockito.any(),Mockito.any()))
                .thenReturn(dto);
        mockMvc.perform(MockMvcRequestBuilders.put("/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));
    }



    @Test
    void deleteUser() throws Exception {
        Mockito.when(this.userService.delete(Mockito.any()))
                .thenReturn(User.builder().build());
        mockMvc.perform(MockMvcRequestBuilders.delete("/user/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.NO_CONTENT.value()));
    }


    @Test
    void deleteUserError() throws Exception {
        Mockito.when(this.userService.delete(Mockito.any()))
                .thenThrow(EntityNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.delete("/user/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void listUser() throws Exception{
        UserDTO dto = new UserDTO(1,"user", "email@email.com");
        List<UserDTO> userDTOList= new ArrayList<>();
        userDTOList.add(dto);
        Mockito.when(this.userService.getAll())
                .thenReturn(userDTOList);
        mockMvc.perform(MockMvcRequestBuilders.get("/user"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").value("user"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].email").value("email@email.com"));
    }

    @Test
    void listUserPaged() throws Exception{
        UserDTO dto = new UserDTO(1,"user", "email@email.com");
        List<UserDTO> userDTOList= new ArrayList<>();
        userDTOList.add(dto);
        Mockito.when(this.userService.getAllPaged(Mockito.any()))
                .thenReturn(new PageImpl<>(userDTOList));
        mockMvc.perform(MockMvcRequestBuilders.get("/user/paged"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.[0].name").value("user"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.[0].email").value("email@email.com"));
    }
}