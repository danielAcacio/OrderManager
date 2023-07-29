package pt.com.sibs.order.manager.validators;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.com.sibs.order.manager.controller.dto.stock.RegisterStockMovementDTO;
import pt.com.sibs.order.manager.core.exceptions.DataIntegrityException;
import pt.com.sibs.order.manager.core.exceptions.NegocialException;
import pt.com.sibs.order.manager.model.Item;
import pt.com.sibs.order.manager.model.Order;
import pt.com.sibs.order.manager.model.OrderStockMovementUsage;
import pt.com.sibs.order.manager.model.StockMovement;
import pt.com.sibs.order.manager.model.enums.MovementType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class StockMovementValidatorTest {

    @InjectMocks
    private StockMovementValidator validator;

    @Test
    void validateDeleteError() {
        List<OrderStockMovementUsage> orderList= new ArrayList<>();
        orderList.add(new OrderStockMovementUsage());
        StockMovement mov = new StockMovement(1,
                LocalDateTime.now(),
                Item.builder().id(2).build(),
                10,
                MovementType.INPUT,
                orderList);
        Assertions.assertThrows(DataIntegrityException.class ,()->this.validator.validateDelete(mov));


    }

    @Test
    void validateDeleteSuccess() {
        StockMovement mov = new StockMovement(1,
                LocalDateTime.now(),
                Item.builder().id(2).build(),
                10,
                MovementType.INPUT,
                new ArrayList<>());
        Assertions.assertDoesNotThrow(()->this.validator.validateDelete(mov));
    }

    @Test
    void validateUpdate() {
    }


    @Test
    void validateUpdateError() {
        RegisterStockMovementDTO dto = new RegisterStockMovementDTO(1, 2);
        List<OrderStockMovementUsage> orderList= new ArrayList<>();
        orderList.add(new OrderStockMovementUsage(1, new Order(), new StockMovement(), 3));
        StockMovement mov = new StockMovement(1,
                LocalDateTime.now(),
                Item.builder().id(2).build(),
                10,
                MovementType.INPUT,
                orderList);
        Assertions.assertThrows(NegocialException.class ,()->this.validator.validateUpdate(mov, dto));



        dto.setItem(2);
        Assertions.assertThrows(NegocialException.class ,()->this.validator.validateUpdate(mov, dto));



    }

}