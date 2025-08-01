package com.bankMnagaement.BankManagementTask.services;

import com.bankMnagaement.BankManagementTask.dtos.AppUserRequestDto;
import com.bankMnagaement.BankManagementTask.dtos.AppUserResponseDto;
import com.bankMnagaement.BankManagementTask.dtos.LoginRequest;
import com.bankMnagaement.BankManagementTask.dtos.LoginResponse;
import com.bankMnagaement.BankManagementTask.exception.ObjectAlreadyExistException;

import javax.security.auth.login.CredentialNotFoundException;

public interface AppUserService {
    AppUserResponseDto registerAppUser(AppUserRequestDto dto) throws ObjectAlreadyExistException;
    LoginResponse loginUsers(LoginRequest loginRequest) throws CredentialNotFoundException;
}
