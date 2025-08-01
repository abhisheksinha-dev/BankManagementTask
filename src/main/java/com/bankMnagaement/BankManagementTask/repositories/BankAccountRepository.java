package com.bankMnagaement.BankManagementTask.repositories;

import com.bankMnagaement.BankManagementTask.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
    BankAccount findByAccountHolderUsername(String username);
}