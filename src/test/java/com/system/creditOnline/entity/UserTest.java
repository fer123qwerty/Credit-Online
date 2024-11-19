package com.system.creditOnline.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

public class UserTest {

    @Test
    public void testUserConstructor() {
        User user = new User(1L, "John Doe", LocalDate.of(1990, 1, 1), 500);
        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals("John Doe", user.getName());
        assertEquals(LocalDate.of(1990, 1, 1), user.getBirthdate());
        assertEquals(500, user.getCreditLine());
    }
}
