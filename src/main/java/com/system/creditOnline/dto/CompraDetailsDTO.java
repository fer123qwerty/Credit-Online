package com.system.creditOnline.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompraDetailsDTO {
    private Long idCompra;
    private LocalDate fechaCompra;
    private double montoComision;
    private double montoCompra;
    private double totalPagar;
    private List<PlazoDTO> plazos;
}
