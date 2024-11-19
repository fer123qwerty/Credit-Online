package com.system.creditOnline.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

class CompraDetailsDTOTest {

    @Test
    void testCompraDetailsDTOBuilder() {
        // Crear instancias con Builder
        LocalDate fechaCompra = LocalDate.of(2023, 11, 19);
        PlazoDTO plazo1 = new PlazoDTO(1, 100.0, LocalDate.of(2023, 12, 1));
        PlazoDTO plazo2 = new PlazoDTO(2, 200.0, LocalDate.of(2023, 12, 15));

        CompraDetailsDTO compraDetailsDTO = CompraDetailsDTO.builder()
                .idCompra(123L)
                .fechaCompra(fechaCompra)
                .montoComision(50.0)
                .montoCompra(300.0)
                .totalPagar(350.0)
                .plazos(List.of(plazo1, plazo2))
                .build();

        // Verificar que los valores están correctos
        assertEquals(123L, compraDetailsDTO.getIdCompra());
        assertEquals(fechaCompra, compraDetailsDTO.getFechaCompra());
        assertEquals(50.0, compraDetailsDTO.getMontoComision());
        assertEquals(300.0, compraDetailsDTO.getMontoCompra());
        assertEquals(350.0, compraDetailsDTO.getTotalPagar());
        assertEquals(2, compraDetailsDTO.getPlazos().size());
    }
}
