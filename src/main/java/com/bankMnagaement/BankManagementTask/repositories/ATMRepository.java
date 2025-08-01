package com.bankMnagaement.BankManagementTask.repositories;

import com.bankMnagaement.BankManagementTask.entities.ATM;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ATMRepository extends JpaRepository<ATM,String> {
}
