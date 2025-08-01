package com.bankMnagaement.BankManagementTask.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BankAccDto {
    private String appUserName;
    private double balance;
    private List<TransactionDto> transactionsDto;
}
