package com.system.creditOnline.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CompraIdResponseDTOTest {

    @Test
    void testCompraIdResponseDTO() {
        // Crear una instancia
        CompraIdResponseDTO compraIdResponseDTO = new CompraIdResponseDTO(123L);

        // Verificar el valor
        assertEquals(123L, compraIdResponseDTO.getIdCompra());
    }
}
