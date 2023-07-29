package pt.com.sibs.order.manager.controller.dto.item;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.com.sibs.order.manager.model.Item;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ItemDTOTest {

    @Test
    void parse() {
        ItemDTO dto = new ItemDTO(1,"item");
        Item item = dto.parse();
        Assertions.assertEquals(item.getId(), dto.getId());
        Assertions.assertEquals(item.getName(), dto.getName());
    }

    @Test
    void build() {
        Item item = Item.builder().id(1).name("item").build();
        ItemDTO dto = new ItemDTO().build(item);
        Assertions.assertEquals(item.getId(), dto.getId());
        Assertions.assertEquals(item.getName(), dto.getName());
    }
}