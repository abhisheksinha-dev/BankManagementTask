package com.bankMnagaement.BankManagementTask.dtos;

import com.bankMnagaement.BankManagementTask.enums.TransactionType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionDto {
    private double amount;
    private LocalDateTime timestamp;
    private TransactionType transactionType;
    private String bankAccountId;
}
