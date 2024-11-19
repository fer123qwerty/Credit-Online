package com.system.creditOnline.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GenericResponseTest {

    @Test
    void testGenericResponse() {
        // Crear instancia
        NotificationResource notification = new NotificationResource("001", "Test message", null);
        GenericResponse<String> response = new GenericResponse<>("Test data", notification);

        // Verificar los valores
        assertEquals("Test data", response.getData());
        assertEquals("001", response.getNotifications().getCode());
        assertEquals("Test message", response.getNotifications().getMessage());
    }
}
