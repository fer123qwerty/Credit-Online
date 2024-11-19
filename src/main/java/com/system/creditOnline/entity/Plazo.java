package com.system.creditOnline.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Plazo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "compra_id", nullable = false)
    private Compra compra;

    private Integer numPago;
    private BigDecimal montoDelPlazo;
    private LocalDate fechaPago;

    public Plazo(int i, BigDecimal valueOf, LocalDate plusMonths) {
    }
}
