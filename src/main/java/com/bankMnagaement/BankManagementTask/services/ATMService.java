package com.bankMnagaement.BankManagementTask.services;

import com.bankMnagaement.BankManagementTask.dtos.ATMDto;

public interface ATMService {
    String createAtm(ATMDto dto);
    String depositMoney(ATMDto atmDto, String id);
    String withdrawMoney(double amount,String atmId);
    String checkTotalMoney(String atmId);
}
