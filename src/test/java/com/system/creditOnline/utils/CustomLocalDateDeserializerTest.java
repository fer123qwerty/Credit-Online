package com.system.creditOnline.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CustomLocalDateDeserializerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testDeserialize_validDateFormats() throws IOException {
        // Crear un módulo con el deserializador personalizado
        SimpleModule module = new SimpleModule();
        module.addDeserializer(LocalDate.class, new CustomLocalDateDeserializer());
        objectMapper.registerModule(module);

        // Fechas de prueba con los formatos soportados
        String date1 = "\"2024-11-19\""; // Formato yyyy-MM-dd
        String date2 = "\"2024/11/19\""; // Formato yyyy/MM/dd
        String date3 = "\"19-11-2024\""; // Formato dd-MM-yyyy
        String date4 = "\"19/11/2024\""; // Formato dd/MM/yyyy

        // Prueba para el primer formato
        LocalDate localDate1 = objectMapper.readValue(date1, LocalDate.class);
        assertEquals(LocalDate.of(2024, 11, 19), localDate1);

        // Prueba para el segundo formato
        LocalDate localDate2 = objectMapper.readValue(date2, LocalDate.class);
        assertEquals(LocalDate.of(2024, 11, 19), localDate2);

        // Prueba para el tercer formato
        LocalDate localDate3 = objectMapper.readValue(date3, LocalDate.class);
        assertEquals(LocalDate.of(2024, 11, 19), localDate3);

        // Prueba para el cuarto formato
        LocalDate localDate4 = objectMapper.readValue(date4, LocalDate.class);
        assertEquals(LocalDate.of(2024, 11, 19), localDate4);
    }

    @Test
    void testDeserialize_invalidDateFormat() {
        // Crear un módulo con el deserializador personalizado
        SimpleModule module = new SimpleModule();
        module.addDeserializer(LocalDate.class, new CustomLocalDateDeserializer());
        objectMapper.registerModule(module);

        // Fecha inválida (no coincide con ninguno de los formatos soportados)
        String invalidDate = "\"2024-19-11\"";

        // Verificar que se lanza una excepción
        assertThrows(IOException.class, () -> {
            objectMapper.readValue(invalidDate, LocalDate.class);
        });
    }
}
