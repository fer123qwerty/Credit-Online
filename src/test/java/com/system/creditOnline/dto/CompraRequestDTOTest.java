package com.system.creditOnline.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

class CompraRequestDTOTest {

    @Test
    void testCompraRequestDTO() {
        // Crear instancia
        CompraRequestDTO compraRequestDTO = new CompraRequestDTO();
        compraRequestDTO.setIdCliente(1L);
        compraRequestDTO.setMontoCompra(new BigDecimal("100.50"));

        // Verificar valores
        assertEquals(1L, compraRequestDTO.getIdCliente());
        assertEquals(new BigDecimal("100.50"), compraRequestDTO.getMontoCompra());
    }
}
