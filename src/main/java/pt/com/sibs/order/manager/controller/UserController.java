package pt.com.sibs.order.manager.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pt.com.sibs.order.manager.controller.dto.user.UserDTO;
import pt.com.sibs.order.manager.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {

    private UserService service;

    @PostMapping("/user")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO dto){
        return ResponseEntity.ok(this.service.create(dto));
    }

    @PutMapping("/user/{userId}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable(name = "userId") Integer userId,
            @Valid @RequestBody UserDTO dto){
        return ResponseEntity.ok(this.service.update(userId, dto));
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<List<UserDTO>> deleteUser(@PathVariable(name = "userId") Integer userId){
        this.service.delete(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user")
    public ResponseEntity<List<UserDTO>> listUser(){
        return ResponseEntity.ok(this.service.getAll());
    }


    @GetMapping("/user/paged")
    public ResponseEntity<Page<UserDTO>> listUserPaged(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size){
        return ResponseEntity.ok(this.service.getAllPaged(PageRequest.of(page, size)));
    }

}
