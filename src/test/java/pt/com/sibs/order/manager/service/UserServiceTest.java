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
import pt.com.sibs.order.manager.controller.dto.user.UserDTO;
import pt.com.sibs.order.manager.core.exceptions.EntityNotFoundException;
import pt.com.sibs.order.manager.model.User;
import pt.com.sibs.order.manager.repository.UserRepository;
import pt.com.sibs.order.manager.validators.UserValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repository;
    @Mock
    private UserValidator validator;

    @Captor
    ArgumentCaptor<User> uCaptor;
    @InjectMocks
    private UserService service;


    @Test
    void create() {
        Mockito.when(this.repository.save(Mockito.any())).thenReturn(new User(1, "user", "email@email.com", new ArrayList<>()));
        UserDTO dtoInitial= new UserDTO(null, "user", "email@email.com");
        UserDTO processedDTO = this.service.create(dtoInitial);
        Assertions.assertEquals(1, processedDTO.getId());
        Assertions.assertEquals(dtoInitial.getName(), processedDTO.getName());
        Assertions.assertEquals(dtoInitial.getEmail(), processedDTO.getEmail());

    }

    @Test
    void update() {
        User user = new User(1, "user", "email@email.com", new ArrayList<>());
        Mockito.when(this.repository.findById(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(this.repository.save(Mockito.any())).thenReturn(new User(1, "user_name", "email@email.com", new ArrayList<>()));
        UserDTO dtoInitial= new UserDTO(1, "user_name", "email@email.com");

        UserDTO processedDTO = this.service.update(1,dtoInitial);
        Assertions.assertEquals(1, processedDTO.getId());
        Assertions.assertEquals(dtoInitial.getName(), processedDTO.getName());
        Assertions.assertEquals(dtoInitial.getEmail(), processedDTO.getEmail());
    }

    @Test
    void updateError() {
        User user = new User(1, "user", "email@email.com", new ArrayList<>());
        Mockito.when(this.repository.findById(Mockito.any())).thenReturn(Optional.empty());
        UserDTO dtoInitial= new UserDTO(1, "user_name", "email@email.com");
        Assertions. assertThrows(EntityNotFoundException.class ,()->this.service.delete(1));

    }

    @Test
    void delete() {
        User user = new User(1, "user", "email@email.com", new ArrayList<>());
        Mockito.when(this.repository.findById(Mockito.any())).thenReturn(Optional.of(user));
        this.service.delete(1);
        Mockito.verify(this.repository).delete(uCaptor.capture());
        User  u = uCaptor.getValue();
        Assertions.assertEquals(user.getId(), u.getId());
        Assertions.assertEquals(user.getEmail(), u.getEmail());
        Assertions.assertEquals(user.getName(), u.getName());
    }

    @Test
    void deleteError() {
        Mockito.when(this.repository.findById(Mockito.any())).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, ()-> this.service.delete(1));
    }


    @Test
    void getAll() {
        List<User> userList = new ArrayList<>();
        User user = new User(1, "user", "email@email.com", new ArrayList<>());
        userList.add(user);
        Mockito.when(this.repository.findAll()).thenReturn(userList);

        List<UserDTO> userDTOList = this.service.getAll();
        Assertions.assertEquals(user.getId(), userDTOList.get(0).getId());
        Assertions.assertEquals(user.getName(),userDTOList.get(0).getName());
        Assertions.assertEquals(user.getEmail(), userDTOList.get(0).getEmail());

    }

    @Test
    void getAllPaged() {
        List<User> userList = new ArrayList<>();
        User user = new User(1, "user", "email@email.com", new ArrayList<>());
        userList.add(user);
        Mockito.when(this.repository.findAll(Mockito.any(Pageable.class))).thenReturn(new PageImpl<>(userList));

        Page<UserDTO> userDTOList = this.service.getAllPaged(PageRequest.of(0,10));
        Assertions.assertEquals(user.getId(), userDTOList.getContent().get(0).getId());
        Assertions.assertEquals(user.getName(),userDTOList.getContent().get(0).getName());
        Assertions.assertEquals(user.getEmail(), userDTOList.getContent().get(0).getEmail());

    }

    @Test
    void getByEmail() {
        List<User> userList = new ArrayList<>();
        User user = new User(1, "user", "email@email.com", new ArrayList<>());
        userList.add(user);
        Mockito.when(this.repository.findByEmailLike(Mockito.any())).thenReturn(Optional.of(user));

        User u = this.service.getByEmail("email@email.com");
        Assertions.assertEquals(user.getId(), u.getId());
        Assertions.assertEquals(user.getName(),u.getName());
        Assertions.assertEquals(user.getEmail(), u.getEmail());
    }

    @Test
    void getByEmailError() {
        Mockito.when(this.repository.findByEmailLike(Mockito.any())).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class,()-> this.service.getByEmail("email@email.com"));

    }
}