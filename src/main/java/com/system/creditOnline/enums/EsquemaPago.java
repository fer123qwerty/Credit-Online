package com.system.creditOnline.enums;

import java.math.BigDecimal;

public enum EsquemaPago {
    ESQUEMA_1(5, "Semanal", new BigDecimal("10")),
    ESQUEMA_2(5, "Quincenal", new BigDecimal("13"));

    private final int numeroPagos;
    private final String frecuenciaCobro;
    private final BigDecimal tasa;  // Cambiar a BigDecimal

    EsquemaPago(int numeroPagos, String frecuenciaCobro, BigDecimal tasa) {
        this.numeroPagos = numeroPagos;
        this.frecuenciaCobro = frecuenciaCobro;
        this.tasa = tasa;
    }

    public int getNumeroPagos() {
        return numeroPagos;
    }

    public String getFrecuenciaCobro() {
        return frecuenciaCobro;
    }

    public BigDecimal getTasa() {  // Cambiar el tipo de retorno a BigDecimal
        return tasa;
    }
}
