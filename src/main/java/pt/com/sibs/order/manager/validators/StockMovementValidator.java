package pt.com.sibs.order.manager.validators;

import org.springframework.stereotype.Component;
import pt.com.sibs.order.manager.controller.dto.stock.RegisterStockMovementDTO;
import pt.com.sibs.order.manager.core.exceptions.DataIntegrityException;
import pt.com.sibs.order.manager.core.exceptions.NegocialException;
import pt.com.sibs.order.manager.model.StockMovement;

@Component
public class StockMovementValidator {

    public void validateDelete(StockMovement movement){
        if(!movement.getUsages().isEmpty()){
            throw new DataIntegrityException("The movemente cannot be deteleted: already in use to satisfy an order!");
        }
    }

    public void validateUpdate(StockMovement movement, RegisterStockMovementDTO dto){
        boolean alreadyInUse = !movement.getUsages().isEmpty();

        if(alreadyInUse && !movement.getItem().getId().equals(dto.getItem())){
            throw new NegocialException("Is not possible to change the item type of a stock movement alreary used by orders.");
        }

        Integer usedItens = movement.getQuantity()-movement.getItensAvaiable();
        if(alreadyInUse && usedItens>dto.getQuantity()){
            throw new NegocialException("Is not possible to reduce the number of itens of a stock movement alreary used by orders.");
        }


    }

}
