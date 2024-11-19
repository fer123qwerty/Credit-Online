package com.system.creditOnline.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class PlazoTest {

    @Test
    public void testPlazoConstructor() {
        Plazo plazo = new Plazo();
        plazo.setNumPago(1);
        plazo.setMontoDelPlazo(BigDecimal.valueOf(200));
        plazo.setFechaPago(LocalDate.of(2024, 12, 15));
        assertNotNull(plazo);
        assertEquals(1, plazo.getNumPago());
        assertEquals(BigDecimal.valueOf(200), plazo.getMontoDelPlazo());
        assertEquals(LocalDate.of(2024, 12, 15), plazo.getFechaPago());
    }
}
