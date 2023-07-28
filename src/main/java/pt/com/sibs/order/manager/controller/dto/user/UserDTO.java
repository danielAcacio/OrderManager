package pt.com.sibs.order.manager.controller.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.com.sibs.order.manager.controller.dto.interfaces.ParseableDTO;
import pt.com.sibs.order.manager.model.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Getter@Setter
public class UserDTO implements ParseableDTO<User> {

    private Integer id;
    @NotBlank(message = "You should to inform an user name!")
    private String name;
    @NotBlank(message = "You should to inform an email!")
    @Email(message= "You should to inform a valid email!")
    private String email;

    @Override
    public User parse() {
        return new User(this.getId(),
                        this.getName(),
                        this.getEmail());
    }

    @Override
    public UserDTO build(User user) {
        this.setId(user.getId());
        this.setName(user.getName());
        this.setEmail(user.getEmail());
        return this;
    }
}
