package pt.com.sibs.order.manager.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pt.com.sibs.order.manager.model.enums.OrderStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void testToString() {
        Order order = new Order(1,
                User.builder().email("email@email.com").build(),
                Item.builder().name("item").build(),
                100, OrderStatus.COMPLETED, LocalDateTime.now(), new ArrayList<>());


        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("YYYY/MM/dd HH:mm:ss");

        StringBuilder sb = new StringBuilder("Order");
        sb.append("\nOrder Number:" +order.getId().toString());
        sb.append("\nOrderer Email: " +order.getUser().getEmail());
        sb.append("\nItem: "+order.getItem().getName());
        sb.append("\nQuantity: "+ order.getQuantity());
        sb.append("\nOrder Status: "+ order.getOrderStatus().name());
        sb.append("\nCreated at: " + fmt.format(order.getCreationDate()));


        Assertions.assertEquals(sb.toString(), order.toString());

    }
}