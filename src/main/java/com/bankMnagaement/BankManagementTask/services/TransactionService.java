package com.bankMnagaement.BankManagementTask.services;

import com.bankMnagaement.BankManagementTask.dtos.TransactionDto;

import java.util.List;

public interface TransactionService {
    TransactionDto addAmount(double amount);
    TransactionDto withdrawAmount(double amount);
    List<TransactionDto> getTransactions();
}
