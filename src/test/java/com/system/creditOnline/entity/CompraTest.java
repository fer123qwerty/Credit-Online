package com.system.creditOnline.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class CompraTest {

    @Test
    public void testCompraConstructor() {
        Compra compra = new Compra();
        compra.setMontoCompra(BigDecimal.valueOf(1000));
        compra.setMontoComision(BigDecimal.valueOf(100));
        compra.setMontoTotal(BigDecimal.valueOf(1100));
        compra.setFechaCompra(LocalDate.now());
        assertNotNull(compra);
        assertEquals(BigDecimal.valueOf(1000), compra.getMontoCompra());
        assertEquals(BigDecimal.valueOf(100), compra.getMontoComision());
        assertEquals(BigDecimal.valueOf(1100), compra.getMontoTotal());
        assertNotNull(compra.getFechaCompra());
    }
}
