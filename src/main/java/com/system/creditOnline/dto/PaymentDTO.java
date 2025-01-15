package com.system.creditOnline.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDTO {
    private String customer;
    private int term;
    private double amount;
    private double interest;
    private double vat;
    private double payment;

}
