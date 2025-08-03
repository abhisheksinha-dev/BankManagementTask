package com.bankMnagaement.BankManagementTask.services.impl;

import com.bankMnagaement.BankManagementTask.dtos.BankAccDto;
import com.bankMnagaement.BankManagementTask.entities.AppUser;
import com.bankMnagaement.BankManagementTask.entities.BankAccount;
import com.bankMnagaement.BankManagementTask.exception.InvalidIdException;
import com.bankMnagaement.BankManagementTask.exception.ObjectAlreadyExistException;
import com.bankMnagaement.BankManagementTask.exception.ResourceNotFoundException;
import com.bankMnagaement.BankManagementTask.repositories.AppUserRepository;
import com.bankMnagaement.BankManagementTask.repositories.BankAccountRepository;
import com.bankMnagaement.BankManagementTask.services.BankAccountService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BankImpl implements BankAccountService {

    private final ModelMapper modelMapper;
    private final AppUserRepository appUserRepository;
    private final BankAccountRepository bankAccountRepository;

    @Override
    @Transactional
    public BankAccDto createBankAccount(BankAccDto dto) throws ObjectAlreadyExistException {
        AppUser appUser = appUserRepository
                .findByUsername(dto.getAppUserName())
                .orElseThrow(() ->new ResourceNotFoundException("App user not exists"));

        BankAccount account = new BankAccount();

        account.setAccountHolder(appUser);
        account.setBalance(dto.getBalance());
        account.setAccountHolderName(appUser.getName());
        bankAccountRepository.save(account);

        appUser.setBankAccount(account);

        return modelMapper.map(account, BankAccDto.class);
    }

    public String checkBalance(){
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser currentUser = appUserRepository.findByUsername(username).orElseThrow();

        return "CURRENT BALANCE : "+ currentUser.getBankAccount().getBalance();
    }

    @Override
    public String deleteBankAccount(String bankAccountId) {
        BankAccount account = bankAccountRepository
                .findById(bankAccountId)
                .orElseThrow(()-> new InvalidIdException("Enter correct Id"));

        bankAccountRepository.delete(account);
        return "Bank Account Successfully deleted of Account Holder : " + account
                .getAccountHolder()
                .getName();
    }
}
