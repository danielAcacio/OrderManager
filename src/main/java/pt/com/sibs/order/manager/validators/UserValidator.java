package pt.com.sibs.order.manager.validators;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pt.com.sibs.order.manager.core.exceptions.DataIntegrityException;
import pt.com.sibs.order.manager.core.exceptions.NegocialException;
import pt.com.sibs.order.manager.model.User;
import pt.com.sibs.order.manager.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class UserValidator {

    private UserRepository repository;

    public void validateEmail(User user){
        List<User> users = this.repository.findByIdAndEmail(user.getId(), user.getEmail());
        if(!users.isEmpty()){
            throw new NegocialException("Email already in use!");
        }
    }

    public void validateDelete(User user){
        if(user.getOrders()!=null && !user.getOrders().isEmpty()){
            throw new DataIntegrityException("The user cannot be deteled: there are order belonging by him.");
        }
    }
}
