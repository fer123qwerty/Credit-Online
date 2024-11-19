package com.system.creditOnline.service.impl;

import com.system.creditOnline.dto.CompraDetailsDTO;
import com.system.creditOnline.dto.CompraRequestDTO;
import com.system.creditOnline.dto.CompraResponseDTO;
import com.system.creditOnline.dto.PlazoDTO;
import com.system.creditOnline.entity.Compra;
import com.system.creditOnline.entity.Plazo;
import com.system.creditOnline.entity.User;
import com.system.creditOnline.enums.EsquemaPago;
import com.system.creditOnline.repository.CompraRepository;
import com.system.creditOnline.repository.UserRepository;
import com.system.creditOnline.service.CompraService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CompraServiceImpl implements CompraService {
    private final CompraRepository compraRepository;
    private final UserRepository userRepository;

    @Autowired
    public CompraServiceImpl(CompraRepository compraRepository, UserRepository userRepository) {
        this.compraRepository = compraRepository;
        this.userRepository = userRepository;
    }
    @Override
    public CompraResponseDTO registrarCompra(CompraRequestDTO request) {
        log.info("Iniciando proceso de compra para el usuario con ID: {}", request.getIdCliente());

        // Variables que necesitan ser inicializadas fuera del bloque try-catch
        BigDecimal montoComision = BigDecimal.ZERO;
        BigDecimal montoTotal = BigDecimal.ZERO;
        BigDecimal montoPorPlazo = BigDecimal.ZERO;
        List<LocalDate> fechasPago = new ArrayList<>();
        Compra savedCompra = null;  // Variable para la compra guardada

        try {
            // Verificar si el cliente existe
            User user = userRepository.findById(request.getIdCliente())
                    .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado."));

            // Validar monto de compra contra línea de crédito
            if (request.getMontoCompra().compareTo(BigDecimal.valueOf(user.getCreditLine())) > 0) {
                throw new IllegalArgumentException("El monto de la compra excede la línea de crédito disponible.");
            }

            // Determinar el esquema de pago
            EsquemaPago esquema = determinarEsquema(user);

            // Obtener la tasa como BigDecimal directamente
            BigDecimal tasa = esquema.getTasa();

            // Asegurarse de que la tasa no sea nula (aunque con BigDecimal no debería ser necesario)
            if (tasa == null) {
                throw new IllegalArgumentException("La tasa del esquema de pago es inválida.");
            }

            // Calcular el monto de la comisión correctamente
            montoComision = request.getMontoCompra()
                    .multiply(tasa)
                    .divide(BigDecimal.valueOf(100), BigDecimal.ROUND_HALF_UP);  // Usando un redondeo adecuado

            // Calcular el monto total
            montoTotal = request.getMontoCompra().add(montoComision);

            // Calcular el monto por plazo
            montoPorPlazo = montoTotal.divide(BigDecimal.valueOf(esquema.getNumeroPagos()), BigDecimal.ROUND_HALF_UP);

            // Calcular las fechas de pago
            fechasPago = calcularFechasPago(esquema, esquema.getNumeroPagos());

            // Crear compra
            Compra compra = new Compra();
            compra.setUser(user);
            compra.setMontoCompra(request.getMontoCompra());
            compra.setMontoComision(montoComision);
            compra.setMontoTotal(montoTotal);
            compra.setNumeroPagos(esquema.getNumeroPagos());
            compra.setEsquemaPago(esquema.name());
            compra.setMontoPorPlazo(montoPorPlazo);
            compra.setFechaCompra(LocalDate.now());
            compra.setFechaProximoPago(fechasPago.get(0));

            // Crear plazos asociados a la compra
            List<Plazo> plazos = new ArrayList<>();
            for (int i = 0; i < esquema.getNumeroPagos(); i++) {
                Plazo plazo = new Plazo();
                plazo.setCompra(compra);
                plazo.setNumPago(i + 1);
                plazo.setMontoDelPlazo(montoPorPlazo);
                plazo.setFechaPago(fechasPago.get(i));
                plazos.add(plazo);
            }
            compra.setPlazos(plazos); // Relacionar plazos con la compra

            // Guardar la compra (y sus plazos gracias a CascadeType.ALL)
            savedCompra = compraRepository.save(compra);

            log.info("Compra realizada con éxito para el usuario con ID: {}", request.getIdCliente());

        } catch (Exception e) {
            log.error("Error al realizar la compra para el usuario con ID: {}", request.getIdCliente(), e);
        }

        // Construir respuesta fuera del bloque try-catch
        if (savedCompra != null) {
            return CompraResponseDTO.builder()
                    .idCompra(savedCompra.getId())
                    .fechaCompra(LocalDate.now())
                    .montoComision(montoComision)
                    .montoTotal(montoTotal)
                    .montoPorPlazo(montoPorPlazo)
                    .fechasPago(fechasPago)
                    .build();
        } else {
            // Si la compra no se ha guardado (en caso de error), devolver una respuesta de error o null
            return CompraResponseDTO.builder()
                    .build();
        }
    }




    private EsquemaPago determinarEsquema(User user) {
        log.info("Determinando esquema de pago para el usuario con ID: {}", user.getId());

        // Evaluar las reglas en el orden definido
        if (user.getBirthdate().isBefore(LocalDate.of(2005, 1, 1))) {
            log.info("Usuario con fecha de nacimiento anterior a 2005. Esquema: ESQUEMA_1");
            return EsquemaPago.ESQUEMA_1; // Fecha de nacimiento anterior a 2005
        } else if (user.getId() > 25) {
            log.info("Usuario con ID mayor a 25. Esquema: ESQUEMA_2");
            return EsquemaPago.ESQUEMA_2; // ID mayor a 25
        } else {
            log.info("Aplicando esquema por defecto: ESQUEMA_2");
            return EsquemaPago.ESQUEMA_2; // Default (Esquema 2)
        }
    }

    private List<LocalDate> calcularFechasPago(EsquemaPago esquema, int numeroPagos) {
        log.info("Calculando fechas de pago para el esquema: {} con {} pagos.", esquema.getFrecuenciaCobro(), numeroPagos);

        List<LocalDate> fechasPago = new ArrayList<>();
        LocalDate fechaBase = LocalDate.now();

        for (int i = 0; i < numeroPagos; i++) {
            if ("Semanal".equals(esquema.getFrecuenciaCobro())) {
                fechaBase = fechaBase.plusWeeks(1);
                log.debug("Fecha de pago semanal calculada: {}", fechaBase);
            } else {
                fechaBase = fechaBase.plusWeeks(2); // Quincenal
                log.debug("Fecha de pago quincenal calculada: {}", fechaBase);
            }
            fechasPago.add(fechaBase);
        }
        return fechasPago;
    }


    @Override
    public List<CompraDetailsDTO> listarComprasPorCliente(Long idCliente) {
        log.info("Listando compras para el cliente con ID: {}", idCliente);

        User cliente = userRepository.findById(idCliente)
                .orElseThrow(() -> {
                    log.error("Cliente con ID: {} no encontrado.", idCliente);
                    return new RuntimeException("Cliente no encontrado.");
                });

        List<Compra> compras = compraRepository.findByUserId(idCliente);

        return compras.stream().map(compra -> {
            log.debug("Procesando compra con ID: {}", compra.getId());
            return CompraDetailsDTO.builder()
                    .idCompra(compra.getId())
                    .fechaCompra(compra.getFechaCompra())
                    .montoComision(compra.getMontoComision() != null ? compra.getMontoComision().doubleValue() : 0.0)
                    .montoCompra(compra.getMontoCompra() != null ? compra.getMontoCompra().doubleValue() : 0.0)
                    .totalPagar(compra.getMontoTotal() != null ? compra.getMontoTotal().doubleValue() : 0.0)
                    .plazos(compra.getPlazos().stream().map(plazo -> PlazoDTO.builder()
                            .numPago(plazo.getNumPago() != null ? plazo.getNumPago() : 0)
                            .montoDelPlazo(plazo.getMontoDelPlazo() != null ? plazo.getMontoDelPlazo().doubleValue() : 0.0)
                            .fechaPago(plazo.getFechaPago())
                            .build()).toList())
                    .build();
        }).toList();
    }
}


