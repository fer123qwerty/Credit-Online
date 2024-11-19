package com.system.creditOnline.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NotificationResourceTest {

    @Test
    void testNotificationResource() {
        // Crear instancia
        NotificationResource notification = new NotificationResource("001", "Test message", null);

        // Verificar valores
        assertEquals("001", notification.getCode());
        assertEquals("Test message", notification.getMessage());
    }
}
