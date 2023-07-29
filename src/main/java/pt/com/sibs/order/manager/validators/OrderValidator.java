package pt.com.sibs.order.manager.validators;

import org.springframework.stereotype.Component;
import pt.com.sibs.order.manager.controller.dto.order.RegisterOrderDTO;
import pt.com.sibs.order.manager.core.exceptions.NegocialException;
import pt.com.sibs.order.manager.model.Order;
import pt.com.sibs.order.manager.model.enums.OrderStatus;

@Component
public class OrderValidator {


    public void validateDelete(Order order){
        this.validateStatusNotComplete(order, "The order cannot be deleted: the order is already complete");
        if(order.getStockUnitsInUse()>0){
            throw new NegocialException("Orders partially complete cannot be deleted.");
        }
    }

    public void validateUpdate(Order order, RegisterOrderDTO dto){
        this.validateStatusNotComplete(order, "The order cannot be updated: the order is already complete");
        if(order.getStockUnitsInUse()>dto.getQuantity()){
            throw new NegocialException("The order quantity cannot be updated to a values minor then the already aloccated");
        }

        if(!order.getItem().getId().equals(dto.getItem()) && order.getStockUnitsInUse()>0){
            throw new NegocialException("The item cannot be updated in orders partially complete");
        }

    }

    private void validateStatusNotComplete(Order order, String message){
        if(order.getOrderStatus().equals(OrderStatus.COMPLETED)){
            throw new NegocialException(message);
        }
    }


}
