package com.system.creditOnline.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlazoDTO {
    private int numPago;
    private double montoDelPlazo;
    private LocalDate fechaPago;
}

