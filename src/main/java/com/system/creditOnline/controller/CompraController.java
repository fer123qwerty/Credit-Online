package com.system.creditOnline.controller;

import com.system.creditOnline.dto.CompraDetailsDTO;
import com.system.creditOnline.dto.CompraIdResponseDTO;
import com.system.creditOnline.dto.CompraRequestDTO;
import com.system.creditOnline.dto.CompraResponseDTO;
import com.system.creditOnline.dto.GenericResponse;
import com.system.creditOnline.dto.NotificationResource;
import com.system.creditOnline.service.CompraService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/compra")
public class CompraController {

    private final CompraService compraService;

    @Autowired
    public CompraController(CompraService compraService) {
        this.compraService = compraService;
    }

    @PostMapping("/purchase")
    public ResponseEntity<GenericResponse<CompraIdResponseDTO>> registrarCompraC(@RequestBody CompraRequestDTO compraRequest) {
        log.info("Registrando compra para el cliente con ID: {}", compraRequest.getIdCliente());

        // Intentar registrar la compra
        try {
            CompraResponseDTO nuevaCompra = compraService.registrarCompra(compraRequest);

            // Verificar si la compra fue registrada correctamente
            if (nuevaCompra == null || nuevaCompra.getIdCompra() == null) {
                // Si no se pudo registrar la compra, retornar un BadRequest con el mensaje de error
                log.error("Error al registrar la compra para el cliente con ID: {}", compraRequest.getIdCliente());
                return new ResponseEntity<>(new GenericResponse<>(null,
                        new NotificationResource(
                                HttpStatus.BAD_REQUEST.toString(),
                                "No se pudo registrar la compra.",
                                OffsetDateTime.now()
                        )
                ), HttpStatus.BAD_REQUEST);
            }

            // Construir la respuesta con el idCompra
            CompraIdResponseDTO responseDTO = new CompraIdResponseDTO(nuevaCompra.getIdCompra());

            // Notificaciones
            GenericResponse<CompraIdResponseDTO> response = new GenericResponse<>(
                    responseDTO,
                    new NotificationResource(
                            HttpStatus.CREATED.toString(),
                            "Compra registrada exitosamente.",
                            OffsetDateTime.now()
                    )
            );

            log.info("Compra registrada con éxito. ID de compra: {}", nuevaCompra.getIdCompra());

            // Retornar el código de éxito 201 (CREATED)
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            // Capturar cualquier excepción inesperada y retornar un BadRequest con el mensaje de error
            log.error("Error al registrar la compra: {}", e.getMessage(), e);
            return new ResponseEntity<>(new GenericResponse<>(null,
                    new NotificationResource(
                            HttpStatus.BAD_REQUEST.toString(),
                            "Ocurrió un error al procesar la compra: " + e.getMessage(),
                            OffsetDateTime.now()
                    )
            ), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/user/{idCliente}")
    public ResponseEntity<GenericResponse<List<CompraDetailsDTO>>> listarComprasPorCliente(@PathVariable Long idCliente) {
        log.info("Listando compras para el cliente con ID: {}", idCliente);

        try {
            List<CompraDetailsDTO> compras = compraService.listarComprasPorCliente(idCliente);

            GenericResponse<List<CompraDetailsDTO>> response = new GenericResponse<>(
                    compras,
                    new NotificationResource(
                            HttpStatus.OK.toString(),
                            "Listado de compras obtenido correctamente.",
                            OffsetDateTime.now()
                    )
            );

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (RuntimeException e) {
            log.error("Error al obtener compras para el cliente con ID: {}", idCliente, e);
            GenericResponse<List<CompraDetailsDTO>> response = new GenericResponse<>(
                    null,
                    new NotificationResource(
                            HttpStatus.NOT_FOUND.toString(),
                            "Cliente no encontrado.",
                            OffsetDateTime.now()
                    )
            );

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}



