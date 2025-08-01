package com.bankMnagaement.BankManagementTask.repositories;

import com.bankMnagaement.BankManagementTask.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
}