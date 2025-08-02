package com.bankMnagaement.BankManagementTask.services;

import com.bankMnagaement.BankManagementTask.dtos.BankAccDto;
import com.bankMnagaement.BankManagementTask.exception.ObjectAlreadyExistException;

public interface BankAccountService {
    BankAccDto createBankAccount(BankAccDto dto) throws ObjectAlreadyExistException;
    String checkBalance();
    String deleteBankAccount(String bankAccountId);
}
