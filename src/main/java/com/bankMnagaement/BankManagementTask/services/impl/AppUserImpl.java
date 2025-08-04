package com.bankMnagaement.BankManagementTask.services.impl;

import com.bankMnagaement.BankManagementTask.dtos.AppUserRequestDto;
import com.bankMnagaement.BankManagementTask.dtos.AppUserResponseDto;
import com.bankMnagaement.BankManagementTask.dtos.LoginRequest;
import com.bankMnagaement.BankManagementTask.dtos.LoginResponse;
import com.bankMnagaement.BankManagementTask.entities.AppUser;
import com.bankMnagaement.BankManagementTask.exception.ObjectAlreadyExistException;
import com.bankMnagaement.BankManagementTask.exception.ResourceNotFoundException;
import com.bankMnagaement.BankManagementTask.repositories.AppUserRepository;
import com.bankMnagaement.BankManagementTask.security.JwtSecurityTokenUtils;
import com.bankMnagaement.BankManagementTask.services.AppUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppUserImpl implements AppUserService {

    private final AppUserRepository appUserRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtSecurityTokenUtils jwtSecurityTokenUtils;

    @Override
    public AppUserResponseDto registerAppUser(AppUserRequestDto dto) throws ObjectAlreadyExistException {

        if(appUserRepository.findByUsername(dto.getUsername()).isPresent())
            throw new ObjectAlreadyExistException("Already registered");

        AppUser user = new AppUser();

        user.setName(dto.getName());
        user.setUsername(dto.getUsername());
        user.setRole(dto.getRole());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        appUserRepository.save(user);

        return modelMapper.map(dto,AppUserResponseDto.class);
    }

    @Override
    public LoginResponse loginUsers(LoginRequest loginRequest) throws CredentialNotFoundException {

        AppUser users = appUserRepository
                .findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User doesn't exist, please enter correct username or register yourself"));

        if (passwordEncoder.matches(loginRequest.getPassword(), users.getPassword())) {
            String token = jwtSecurityTokenUtils.generateToken(loginRequest.getUsername());

            LoginResponse response = new LoginResponse();
            response.setUsername(loginRequest.getUsername());
            response.setToken(token);

            return response;
        } else {
            throw new CredentialNotFoundException("Password incorrect, please enter valid password");
        }
    }

    @Override
    public List<AppUserResponseDto> getAllUsers() {
        List<AppUser> appUserList = appUserRepository.findAll();
        return appUserList.stream().map(appUser -> modelMapper.map(appUser,AppUserResponseDto.class)).toList();
    }
}
