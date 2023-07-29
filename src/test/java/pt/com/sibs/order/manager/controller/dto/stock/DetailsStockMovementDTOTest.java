package pt.com.sibs.order.manager.controller.dto.stock;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.com.sibs.order.manager.controller.dto.item.ItemDTO;
import pt.com.sibs.order.manager.model.Item;
import pt.com.sibs.order.manager.model.StockMovement;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class DetailsStockMovementDTOTest {

    @Test
    void parse() {
        LocalDateTime date = LocalDateTime.now();
        DetailsStockMovementDTO dto = new DetailsStockMovementDTO();

        dto.setId(1);
        dto.setQuantity(20);
        dto.setItem(new ItemDTO().build(Item.builder().id(1).name("name").build()));
        dto.setCreationDate(date);


        StockMovement movement = dto.parse();
        Assertions.assertEquals(movement.getId(), dto.getId());
        Assertions.assertEquals(movement.getQuantity(), dto.getQuantity());
        Assertions.assertEquals(movement.getMovementType(), dto.getMovementType());
        Assertions.assertEquals(movement.getItem().getId(), dto.getItem().getId());
        Assertions.assertEquals(date, movement.getCreationDate());

    }

    @Test
    void build() {
        LocalDateTime date = LocalDateTime.now();
        StockMovement movement = new StockMovement();
        movement.setId(1);
        movement.setQuantity(20);
        movement.setItem(Item.builder().id(1).name("name").build());
        movement.setCreationDate(date);
        movement.setUsages(new ArrayList<>());

        DetailsStockMovementDTO dto = new DetailsStockMovementDTO().build(movement);
        Assertions.assertEquals(movement.getId(), dto.getId());
        Assertions.assertEquals(movement.getQuantity(), dto.getQuantity());
        Assertions.assertEquals(movement.getMovementType(), dto.getMovementType());
        Assertions.assertEquals(movement.getItem().getId(), dto.getItem().getId());
        Assertions.assertEquals(date, movement.getCreationDate());
    }
}