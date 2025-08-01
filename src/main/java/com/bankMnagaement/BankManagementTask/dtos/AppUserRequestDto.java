package com.bankMnagaement.BankManagementTask.dtos;

import com.bankMnagaement.BankManagementTask.enums.RoleType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppUserRequestDto {
    private String name;
    private String username;
    private String password;
    private RoleType role;
}
