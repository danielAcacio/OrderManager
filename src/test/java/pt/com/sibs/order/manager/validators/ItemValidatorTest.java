package pt.com.sibs.order.manager.validators;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.com.sibs.order.manager.core.exceptions.DataIntegrityException;
import pt.com.sibs.order.manager.model.Item;
import pt.com.sibs.order.manager.model.Order;
import pt.com.sibs.order.manager.model.StockMovement;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ItemValidatorTest {

    @InjectMocks
    private ItemValidator itemValidator;

    @Test
    void validateDeleteError() {
        List<Order> orderList = new ArrayList<>();
        List<StockMovement> movementList = new ArrayList<>();

        orderList.add(new Order());
        movementList.add(new StockMovement());

        Item item = Item
                    .builder()
                    .id(1)
                    .name("name")
                    .orderList(orderList)
                    .build();
        Assertions.assertThrows(DataIntegrityException.class,
                ()-> this.itemValidator.validateDelete(item));

        item.setOrderList(null);
        item.setMovementList(movementList);

        Assertions.assertThrows(DataIntegrityException.class,
                ()-> this.itemValidator.validateDelete(item));


    }


    @Test
    void validateDeleteSuccess() {
        Item item = Item
                .builder()
                .id(1)
                .name("name")
                .orderList(null)
                .movementList(null)
                .build();
        Assertions.assertDoesNotThrow(()-> itemValidator.validateDelete(item));
    }
}