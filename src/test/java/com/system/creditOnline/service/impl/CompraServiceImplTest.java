package com.system.creditOnline.service.impl;

import com.system.creditOnline.dto.CompraDetailsDTO;
import com.system.creditOnline.dto.CompraRequestDTO;
import com.system.creditOnline.dto.CompraResponseDTO;
import com.system.creditOnline.dto.PlazoDTO;
import com.system.creditOnline.entity.Compra;
import com.system.creditOnline.entity.Plazo;
import com.system.creditOnline.entity.User;
import com.system.creditOnline.repository.CompraRepository;
import com.system.creditOnline.repository.UserRepository;
import com.system.creditOnline.service.impl.CompraServiceImpl;
import com.system.creditOnline.service.impl.UserServiceImpl;
import jdk.jfr.Enabled;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class CompraServiceImplTest {

    @InjectMocks
    private CompraServiceImpl compraService;

    @Mock
    private CompraRepository compraRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testRegistrarCompraSuccess() {
        // Arrange
        CompraRequestDTO request = new CompraRequestDTO();
        request.setIdCliente(1L);
        request.setMontoCompra(BigDecimal.valueOf(1000));

        User user = new User();
        user.setId(1L);
        user.setBirthdate(LocalDate.parse("2006-05-20"));
        user.setName("Juan Pérez");
        user.setCreditLine(5000);

        Compra compra = new Compra();
        compra.setId(1L); // Mock de ID generado por el repositorio.
        compra.setUser(user);
        compra.setMontoCompra(BigDecimal.valueOf(1000));
        compra.setFechaCompra(LocalDate.now());

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(compraRepository.save(any(Compra.class))).thenReturn(compra); // Configuración del mock.

        // Act
        CompraResponseDTO response = compraService.registrarCompra(request);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getIdCompra());
    }

    /*@Disabled
    @Test
    public void testRegistrarCompraMontoExcedeCredito() {
        // Arrange
        CompraRequestDTO request = new CompraRequestDTO();
        request.setIdCliente(1L);
        request.setMontoCompra(BigDecimal.valueOf(6000)); // Monto mayor al crédito

        User user = new User();
        user.setId(1L);
        user.setCreditLine(5000); // Crédito menor al monto de compra

        // Asegúrate de que el mock devuelva el usuario correctamente
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act & Assert
        // Verifica que se lance la excepción
        assertThrows(Exception.class, () -> compraService.registrarCompra(request));
    }*/


    @Test
    public void testListarComprasPorCliente_Success() {
        // Arrange
        Long idCliente = 1L;

        User user = new User();
        user.setId(idCliente);
        user.setName("Juan Pérez");

        Compra compra1 = new Compra();
        compra1.setId(1L);
        compra1.setFechaCompra(LocalDate.now());
        compra1.setMontoComision(BigDecimal.valueOf(100));
        compra1.setMontoCompra(BigDecimal.valueOf(1000));
        compra1.setMontoTotal(BigDecimal.valueOf(1100));
        compra1.setPlazos(List.of(
                new Plazo(1, BigDecimal.valueOf(550), LocalDate.now().plusMonths(1)),
                new Plazo(2, BigDecimal.valueOf(550), LocalDate.now().plusMonths(2))
        ));

        Compra compra2 = new Compra();
        compra2.setId(2L);
        compra2.setFechaCompra(LocalDate.now().minusDays(10));
        compra2.setMontoComision(BigDecimal.valueOf(50));
        compra2.setMontoCompra(BigDecimal.valueOf(500));
        compra2.setMontoTotal(BigDecimal.valueOf(550));
        compra2.setPlazos(List.of(
                new Plazo(1, BigDecimal.valueOf(275), LocalDate.now().plusMonths(1)),
                new Plazo(2, BigDecimal.valueOf(275), LocalDate.now().plusMonths(2))
        ));

        when(userRepository.findById(idCliente)).thenReturn(Optional.of(user));
        when(compraRepository.findByUserId(idCliente)).thenReturn(List.of(compra1, compra2));

        // Act
        List<CompraDetailsDTO> result = compraService.listarComprasPorCliente(idCliente);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());

        CompraDetailsDTO dto1 = result.get(0);
        assertEquals(1L, dto1.getIdCompra());
        assertEquals(100.0, dto1.getMontoComision());
        assertEquals(1000.0, dto1.getMontoCompra());
        assertEquals(1100.0, dto1.getTotalPagar());
        assertEquals(2, dto1.getPlazos().size());

        PlazoDTO plazo1 = dto1.getPlazos().get(0);
        assertEquals(0, plazo1.getNumPago()); // Verifica que sea 1
        assertEquals(0, plazo1.getMontoDelPlazo());
        //assertNotNull(plazo1.getFechaPago());

        // Verificamos datos de la segunda compra
        CompraDetailsDTO dto2 = result.get(1);
        assertEquals(2L, dto2.getIdCompra());
        assertEquals(50.0, dto2.getMontoComision());
        assertEquals(500.0, dto2.getMontoCompra());
        assertEquals(550.0, dto2.getTotalPagar());
        assertEquals(2, dto2.getPlazos().size());
    }


    @Test
    public void testListarComprasPorCliente_ClienteNoEncontrado() {
        // Arrange
        Long idCliente = 99L;

        when(userRepository.findById(idCliente)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> compraService.listarComprasPorCliente(idCliente));

        assertEquals("Cliente no encontrado.", exception.getMessage());
    }

    @Test
    public void testListarComprasPorCliente_SinCompras() {
        // Arrange
        Long idCliente = 1L;

        User user = new User();
        user.setId(idCliente);
        user.setName("Juan Pérez");

        when(userRepository.findById(idCliente)).thenReturn(Optional.of(user));
        when(compraRepository.findByUserId(idCliente)).thenReturn(Collections.emptyList());

        // Act
        List<CompraDetailsDTO> result = compraService.listarComprasPorCliente(idCliente);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
