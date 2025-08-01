package com.bankMnagaement.BankManagementTask.controller;

import com.bankMnagaement.BankManagementTask.dtos.TransactionDto;
import com.bankMnagaement.BankManagementTask.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/add/{amount}")
    public ResponseEntity<TransactionDto> addAmount(@PathVariable double amount){
        TransactionDto dto = transactionService.addAmount(amount);
        return new ResponseEntity<>(dto, HttpStatus.ACCEPTED);
    }
    @GetMapping("/withdraw/{amount}")
    public ResponseEntity<TransactionDto> withdrawAmount(@PathVariable double amount){
        TransactionDto dto = transactionService.withdrawAmount(amount);
        return new ResponseEntity<>(dto, HttpStatus.ACCEPTED);
    }
    @GetMapping("/get-all")
    public ResponseEntity<List<TransactionDto>> getTransactions(){
        List<TransactionDto> transactionDto = transactionService.getTransactions();
        return new ResponseEntity<>(transactionDto,HttpStatus.FOUND);
    }
}
