package com.system.creditOnline.entity;

import com.system.creditOnline.enums.LoanStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "loans")
@AllArgsConstructor
@NoArgsConstructor
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customer;
    private LocalDate date;
    private double amount;

    @Enumerated(EnumType.STRING)
    private LoanStatus status;
}
