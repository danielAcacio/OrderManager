package pt.com.sibs.order.manager.validators;

import org.springframework.stereotype.Component;
import pt.com.sibs.order.manager.core.exceptions.DataIntegrityException;
import pt.com.sibs.order.manager.model.Item;

@Component
public class ItemValidator {
    public void validateDelete(Item item){
        if(item.getMovementList() != null && !item.getMovementList().isEmpty()){
            throw new DataIntegrityException("The item cannot be deleted: is used stock movements.");
        }

        if(item.getOrderList() != null && !item.getOrderList().isEmpty()){
            throw new DataIntegrityException("The item cannot be deleted: is used in orders ");
        }

    }

}
