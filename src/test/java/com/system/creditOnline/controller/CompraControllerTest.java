package com.system.creditOnline.controller;

import com.system.creditOnline.dto.CompraDetailsDTO;
import com.system.creditOnline.dto.CompraRequestDTO;
import com.system.creditOnline.dto.CompraResponseDTO;
import com.system.creditOnline.service.CompraService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompraControllerTest {

    @InjectMocks
    private CompraController compraController;

    @Mock
    private CompraService compraService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegistrarCompra() {
        // Arrange
        CompraRequestDTO compraRequest = new CompraRequestDTO();
        compraRequest.setIdCliente(1L);
        compraRequest.setMontoCompra(BigDecimal.valueOf(1000));

        // Instanciar CompraResponseDTO con los parámetros requeridos por el constructor
        Long idCompra = 1L;
        LocalDate fechaCompra = LocalDate.now(); // Fecha actual (puedes ajustarlo según tus necesidades)
        BigDecimal montoCompra = BigDecimal.valueOf(1000);
        BigDecimal otroMonto1 = BigDecimal.valueOf(200);
        BigDecimal otroMonto2 = BigDecimal.valueOf(300);
        List<LocalDate> fechas = List.of(LocalDate.now(), LocalDate.now().plusDays(1)); // Ejemplo de fechas

        CompraResponseDTO compraResponseDTO = new CompraResponseDTO(idCompra, fechaCompra, montoCompra, otroMonto1, otroMonto2, fechas);

        when(compraService.registrarCompra(compraRequest)).thenReturn(compraResponseDTO);

        // Act
        ResponseEntity response = compraController.registrarCompraC(compraRequest);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }


    @Test
    public void testListarComprasPorCliente() {
        // Arrange
        Long idCliente = 1L;

        when(compraService.listarComprasPorCliente(idCliente)).thenReturn(List.of(new CompraDetailsDTO()));

        // Act
        ResponseEntity response = compraController.listarComprasPorCliente(idCliente);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testListarComprasPorClienteClienteNoExistente() {
        // Arrange
        Long idCliente = 999L;
        when(compraService.listarComprasPorCliente(idCliente)).thenThrow(new RuntimeException("Cliente no encontrado."));

        // Act
        ResponseEntity response = compraController.listarComprasPorCliente(idCliente);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
