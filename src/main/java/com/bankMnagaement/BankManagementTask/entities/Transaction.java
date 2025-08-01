package com.bankMnagaement.BankManagementTask.entities;

import com.bankMnagaement.BankManagementTask.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "bank_account_id")
    private BankAccount bankAccount;

    private double amount;

    private LocalDateTime timestamp;

    @Enumerated(value = EnumType.STRING)
    private TransactionType transactionType;
}
