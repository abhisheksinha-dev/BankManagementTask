package com.bankMnagaement.BankManagementTask.controller;

import com.bankMnagaement.BankManagementTask.dtos.ATMDto;
import com.bankMnagaement.BankManagementTask.services.ATMService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/atm")
public class ATMController {

    private final ATMService atmService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> createATM(@RequestBody ATMDto atmDto){
        String create = atmService.createAtm(atmDto);
        return new ResponseEntity<>(create, HttpStatus.CREATED);
    }

    @PostMapping("/deposit/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> depositMoney(@RequestBody ATMDto atmDto,@PathVariable String id){
        String deposit = atmService.depositMoney(atmDto,id);
        return new ResponseEntity<>(deposit,HttpStatus.ACCEPTED);
    }

    @GetMapping("/withdraw/{atmId}/{amount}")
    public ResponseEntity<String> withdrawMoney(@PathVariable String atmId,@PathVariable double amount) {
        String withdraw = atmService.withdrawMoney(amount,atmId);
        return new ResponseEntity<>(withdraw,HttpStatus.ACCEPTED);
    }

    @GetMapping("/check-balance/{atmId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> checkTotalMoney(@PathVariable String atmId){
        String balance = atmService.checkTotalMoney(atmId);
        return new ResponseEntity<>(balance,HttpStatus.FOUND);
    }
}
