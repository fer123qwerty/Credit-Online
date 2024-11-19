package com.system.creditOnline.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CompraRequestDTO {
    private Long idCliente;
    private BigDecimal montoCompra;
}

