package com.system.creditOnline.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

class CompraResponseDTOTest {

    @Test
    void testCompraResponseDTO() {
        // Crear instancias
        LocalDate fechaCompra = LocalDate.of(2023, 11, 19);
        List<LocalDate> fechasPago = List.of(LocalDate.of(2023, 12, 1), LocalDate.of(2023, 12, 15));

        CompraResponseDTO compraResponseDTO = CompraResponseDTO.builder()
                .idCompra(123L)
                .fechaCompra(fechaCompra)
                .montoComision(new BigDecimal("50.0"))
                .montoTotal(new BigDecimal("350.0"))
                .montoPorPlazo(new BigDecimal("175.0"))
                .fechasPago(fechasPago)
                .build();

        // Verificar los valores
        assertEquals(123L, compraResponseDTO.getIdCompra());
        assertEquals(fechaCompra, compraResponseDTO.getFechaCompra());
        assertEquals(new BigDecimal("50.0"), compraResponseDTO.getMontoComision());
        assertEquals(new BigDecimal("350.0"), compraResponseDTO.getMontoTotal());
        assertEquals(new BigDecimal("175.0"), compraResponseDTO.getMontoPorPlazo());
        assertEquals(2, compraResponseDTO.getFechasPago().size());
    }
}
