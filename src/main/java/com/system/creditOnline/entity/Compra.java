package com.system.creditOnline.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private BigDecimal montoCompra;
    private BigDecimal montoComision;
    private BigDecimal montoTotal;
    private LocalDate fechaCompra;
    private Integer numeroPagos;
    private String esquemaPago;
    private BigDecimal montoPorPlazo;
    private LocalDate fechaProximoPago;

    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Plazo> plazos;

}

