package com.system.creditOnline.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

class PlazoDTOTest {

    @Test
    void testPlazoDTO() {
        // Crear instancia
        PlazoDTO plazoDTO = PlazoDTO.builder()
                .numPago(1)
                .montoDelPlazo(100.0)
                .fechaPago(LocalDate.of(2023, 12, 1))
                .build();

        // Verificar valores
        assertEquals(1, plazoDTO.getNumPago());
        assertEquals(100.0, plazoDTO.getMontoDelPlazo());
        assertEquals(LocalDate.of(2023, 12, 1), plazoDTO.getFechaPago());
    }
}
