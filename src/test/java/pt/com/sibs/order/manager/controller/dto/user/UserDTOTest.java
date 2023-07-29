package pt.com.sibs.order.manager.controller.dto.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.com.sibs.order.manager.model.User;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class UserDTOTest {

    @Test
    void parse() {
        UserDTO dto = new UserDTO(1,"user","email");
        User user = dto.parse();

        Assertions.assertEquals(user.getId(), dto.getId());
        Assertions.assertEquals(user.getName(), dto.getName());
        Assertions.assertEquals(user.getEmail(), dto.getEmail());
    }

    @Test
    void build() {
        User user = User.builder().id(1).name("user").email("email").orders(new ArrayList<>()).build();
        UserDTO dto = new UserDTO().build(user);
        Assertions.assertEquals(user.getId(), dto.getId());
        Assertions.assertEquals(user.getName(), dto.getName());
        Assertions.assertEquals(user.getEmail(), dto.getEmail());
    }
}