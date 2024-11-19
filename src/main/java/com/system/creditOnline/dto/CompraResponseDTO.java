package com.system.creditOnline.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CompraResponseDTO {
    private Long idCompra;
    private LocalDate fechaCompra;
    private BigDecimal montoComision;
    private BigDecimal montoTotal;
    private BigDecimal montoPorPlazo;
    private List<LocalDate> fechasPago;
}
