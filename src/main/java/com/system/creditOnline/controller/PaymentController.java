package com.system.creditOnline.controller;

import com.system.creditOnline.dto.PaymentDTO;
import com.system.creditOnline.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/process")
    public ResponseEntity<List<PaymentDTO>> processPayments(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate currentDate,
            @RequestParam double interestRate,
            @RequestParam double vatRate,
            @RequestParam int businessYearDays) {
        List<PaymentDTO> appliedPayments = paymentService.processPayments(currentDate, interestRate, vatRate, businessYearDays);
        return ResponseEntity.ok(appliedPayments);
    }
}
