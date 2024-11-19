package com.system.creditOnline.enums;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;

public class EsquemaPagoTest {

    @Test
    public void testEsquemaPago() {
        EsquemaPago esquema1 = EsquemaPago.ESQUEMA_1;
        assertEquals(5, esquema1.getNumeroPagos());
        assertEquals("Semanal", esquema1.getFrecuenciaCobro());
        assertEquals(new BigDecimal("10"), esquema1.getTasa());

        EsquemaPago esquema2 = EsquemaPago.ESQUEMA_2;
        assertEquals(5, esquema2.getNumeroPagos());
        assertEquals("Quincenal", esquema2.getFrecuenciaCobro());
        assertEquals(new BigDecimal("13"), esquema2.getTasa());
    }
}
