package com.bankMnagaement.BankManagementTask.services.impl;

import com.bankMnagaement.BankManagementTask.dtos.BankAccDto;
import com.bankMnagaement.BankManagementTask.dtos.TransactionDto;
import com.bankMnagaement.BankManagementTask.entities.AppUser;
import com.bankMnagaement.BankManagementTask.entities.BankAccount;
import com.bankMnagaement.BankManagementTask.entities.Transaction;
import com.bankMnagaement.BankManagementTask.enums.TransactionType;
import com.bankMnagaement.BankManagementTask.exception.ResourceNotFoundException;
import com.bankMnagaement.BankManagementTask.repositories.AppUserRepository;
import com.bankMnagaement.BankManagementTask.repositories.BankAccountRepository;
import com.bankMnagaement.BankManagementTask.repositories.TransactionRepository;
import com.bankMnagaement.BankManagementTask.services.BankAccountService;
import com.bankMnagaement.BankManagementTask.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionImpl implements TransactionService {

    private final AppUserRepository appUserRepository;
    private final BankAccountRepository bankAccountRepository;
    private final TransactionRepository transactionRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public TransactionDto addAmount(double amount) {

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser currentUser = appUserRepository.findByUsername(username).orElseThrow(()-> new ResourceNotFoundException("No User"));

        BankAccount account = bankAccountRepository.findByAccountHolderUsername(username);

        double currentBalance = currentUser.getBankAccount().getBalance();
        double updatedAmount = currentBalance + amount;

        currentUser.getBankAccount().setBalance(updatedAmount);
        account.setBalance(updatedAmount);

        Transaction transaction = new Transaction();

        transaction.setAmount(amount);
        transaction.setTransactionType(TransactionType.CREDIT);
        transaction.setBankAccount(account);
        transaction.setTimestamp(LocalDateTime.now());

        transactionRepository.save(transaction);

        return modelMapper.map(transaction, TransactionDto.class);
    }

    @Override
    public TransactionDto withdrawAmount(double amount) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser currentUser = appUserRepository.findByUsername(username).orElseThrow(()-> new ResourceNotFoundException("No User"));

        BankAccount account = bankAccountRepository.findByAccountHolderUsername(username);

        double currentBalance = currentUser.getBankAccount().getBalance();
        double updatedAmount = currentBalance - amount;

        currentUser.getBankAccount().setBalance(updatedAmount);
        account.setBalance(updatedAmount);

        Transaction transaction = new Transaction();

        transaction.setAmount(amount);
        transaction.setTransactionType(TransactionType.DEBIT);
        transaction.setBankAccount(account);
        transaction.setTimestamp(LocalDateTime.now());

        transactionRepository.save(transaction);

        return modelMapper.map(transaction, TransactionDto.class);
    }

    @Override
    public List<TransactionDto> getTransactions() {

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser currentUser = appUserRepository.findByUsername(username).orElseThrow(()-> new ResourceNotFoundException("No User"));

        List<Transaction> transactions = currentUser.getBankAccount().getTransactions();

        return transactions
                .stream()
                .map(x -> modelMapper.map(x,TransactionDto.class))
                .toList();
    }

}
