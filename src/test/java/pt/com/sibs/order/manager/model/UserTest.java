package pt.com.sibs.order.manager.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testToString() {
        User user = new User(1, "user", "email", new ArrayList<>());
        StringBuilder sb = new StringBuilder("User ");
        sb.append("\nUser Number: " +user.getId().toString());
        sb.append("\nUser Name: " + user.getName());
        sb.append("\nUser Email: "+user.getEmail());
        Assertions.assertEquals(sb.toString(), user.toString());
    }
}