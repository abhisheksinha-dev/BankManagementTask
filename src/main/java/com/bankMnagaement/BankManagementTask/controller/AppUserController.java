package com.bankMnagaement.BankManagementTask.controller;

import com.bankMnagaement.BankManagementTask.dtos.AppUserRequestDto;
import com.bankMnagaement.BankManagementTask.dtos.AppUserResponseDto;
import com.bankMnagaement.BankManagementTask.dtos.LoginRequest;
import com.bankMnagaement.BankManagementTask.dtos.LoginResponse;
import com.bankMnagaement.BankManagementTask.exception.ObjectAlreadyExistException;
import com.bankMnagaement.BankManagementTask.services.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.CredentialNotFoundException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class AppUserController {

    private final AppUserService appUserService;

    @PostMapping("/register")
    public ResponseEntity<AppUserResponseDto> registerAppUser(@RequestBody AppUserRequestDto dto) throws ObjectAlreadyExistException {
        AppUserResponseDto responseDto = appUserService.registerAppUser(dto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginAppUser(@RequestBody LoginRequest request) throws CredentialNotFoundException {
        LoginResponse responseDto = appUserService.loginUsers(request);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
