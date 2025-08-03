package com.bankMnagaement.BankManagementTask.controller;

import com.bankMnagaement.BankManagementTask.dtos.BankAccDto;
import com.bankMnagaement.BankManagementTask.exception.ObjectAlreadyExistException;
import com.bankMnagaement.BankManagementTask.services.BankAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bank-accounts")
public class BankAccountController {

    private final BankAccountService bankAccountService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<BankAccDto> createBankAccount(@RequestBody BankAccDto dto) throws ObjectAlreadyExistException{
        BankAccDto accDto = bankAccountService.createBankAccount(dto);
        return new ResponseEntity<>(accDto, HttpStatus.CREATED);
    }

    @GetMapping("check-balance")
    public ResponseEntity<String> checkBalance(){
        String totalBalance = bankAccountService.checkBalance();
        return new ResponseEntity<>(totalBalance,HttpStatus.FOUND);
    }

    @DeleteMapping("/delete/{bankAccountId}")
    public ResponseEntity<String> deleteBankAccount(@PathVariable String bankAccountId){
        String delete = bankAccountService.deleteBankAccount(bankAccountId);
        return new ResponseEntity<>(delete,HttpStatus.OK);
    }
}
