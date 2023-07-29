package pt.com.sibs.order.manager.validators;

import org.springframework.stereotype.Component;
import pt.com.sibs.order.manager.core.exceptions.DataIntegrityException;
import pt.com.sibs.order.manager.model.User;

@Component
public class UserValidator {
    public void validateDelete(User user){
        if(user.getOrders()!=null && !user.getOrders().isEmpty()){
            throw new DataIntegrityException("The user cannot be deteled: there are order belonging by him.");
        }
    }
}
