package pt.com.sibs.order.manager.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @Test
    void testToString() {
        Item item= new Item();
        item.setId(1);
        item.setName("item");

        StringBuilder sb = new StringBuilder("Item ");
        sb.append("\nItem Number: " +item.getId().toString());
        sb.append("\nItem Name: " + item.getName());

        Assertions.assertEquals(sb.toString(), item.toString());
    }
}