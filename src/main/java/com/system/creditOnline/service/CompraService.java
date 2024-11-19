package com.system.creditOnline.service;


import com.system.creditOnline.dto.CompraDetailsDTO;
import com.system.creditOnline.dto.CompraRequestDTO;
import com.system.creditOnline.dto.CompraResponseDTO;

import java.util.List;

public interface CompraService {

    CompraResponseDTO registrarCompra(CompraRequestDTO request);

    List<CompraDetailsDTO> listarComprasPorCliente(Long idCliente);
}
