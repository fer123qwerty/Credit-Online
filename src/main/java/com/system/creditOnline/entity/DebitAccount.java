package com.system.creditOnline.entity;

import com.system.creditOnline.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "debit_accounts")
@AllArgsConstructor
@NoArgsConstructor
public class DebitAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customer;
    private double amount;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;
}
