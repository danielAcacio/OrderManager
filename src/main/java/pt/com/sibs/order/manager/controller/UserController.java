package pt.com.sibs.order.manager.controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(description = "Create a new User")
    @ApiResponses({
            @ApiResponse( responseCode = "200", description = "User created."),
            @ApiResponse( responseCode = "409", description = "New user data had conflict with another user."),
            @ApiResponse( responseCode = "500", description = "Internal Error.")
    })
    @PostMapping("/user")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO dto){
        return ResponseEntity.ok(this.service.create(dto));
    }

    @Operation(description = "Update a User")
    @ApiResponses({
            @ApiResponse( responseCode = "200", description = "User updated."),
            @ApiResponse( responseCode = "404", description = "User not found."),
            @ApiResponse( responseCode = "409", description = "New data user had conflict with another user."),
            @ApiResponse( responseCode = "500", description = "Internal Error.")
    })
    @PutMapping("/user/{userId}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable(name = "userId") Integer userId,
            @Valid @RequestBody UserDTO dto){
        return ResponseEntity.ok(this.service.update(userId, dto));
    }

    @Operation(description = "Delete a User")
    @ApiResponses({
            @ApiResponse( responseCode = "204", description = "User deleted."),
            @ApiResponse( responseCode = "404", description = "User not found."),
            @ApiResponse( responseCode = "409", description = "User has orders registered."),
            @ApiResponse( responseCode = "500", description = "Internal Error.")
    })
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<List<UserDTO>> deleteUser(@PathVariable(name = "userId") Integer userId){
        this.service.delete(userId);
        return ResponseEntity.noContent().build();
    }

    @Operation(description = "Get all Users")
    @ApiResponses({
            @ApiResponse( responseCode = "200", description = "Return All Users."),
            @ApiResponse( responseCode = "500", description = "Internal Error.")
    })
    @GetMapping("/user")
    public ResponseEntity<List<UserDTO>> listUser(){
        return ResponseEntity.ok(this.service.getAll());
    }


    @Operation(description = "Get all Users Paged")
    @ApiResponses({
            @ApiResponse( responseCode = "200", description = "Return All Users Paged."),
            @ApiResponse( responseCode = "500", description = "Internal Error.")
    })
    @GetMapping("/user/paged")
    public ResponseEntity<Page<UserDTO>> listUserPaged(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size){
        return ResponseEntity.ok(this.service.getAllPaged(PageRequest.of(page, size)));
    }

}
