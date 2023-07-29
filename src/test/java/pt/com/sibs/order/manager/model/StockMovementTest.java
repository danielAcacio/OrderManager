package pt.com.sibs.order.manager.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pt.com.sibs.order.manager.model.enums.MovementType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class StockMovementTest {

    @Test
    void testToString() {
        StockMovement movement = new StockMovement(1, LocalDateTime.now(),
                Item.builder().name("item").build(), 100, MovementType.INPUT, new ArrayList<>());

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("YYYY/MM/dd HH:mm:ss");
        StringBuilder sb = new StringBuilder("StockMovement");

        sb.append("\nStock Movement Number:" +movement.getId().toString());
        sb.append("\nItem:"+movement.getItem().getName());
        sb.append("\nQuantity: "+ movement.getQuantity());
        sb.append("\nMovement Type: "+ movement.getMovementType().name());
        sb.append("\nCreated at: " + fmt.format(movement.getCreationDate()));

        Assertions.assertEquals(sb.toString(), movement.toString());
    }
}