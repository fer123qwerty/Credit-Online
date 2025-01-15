package com.system.creditOnline.service;

import com.system.creditOnline.dto.PaymentDTO;

import java.time.LocalDate;
import java.util.List;

public interface PaymentService {
    List<PaymentDTO> processPayments(LocalDate currentDate, double interestRate, double vatRate, int businessYearDays);
}
