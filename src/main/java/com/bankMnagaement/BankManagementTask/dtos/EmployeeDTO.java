package com.bankMnagaement.BankManagementTask.dtos;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class EmployeeDTO {
    private Long id;
    private String name;
    private String email;
    private String password;
    private Integer age;
    private String role;
    private Double salary;
    private LocalDate dateOfJoining;
    private Boolean isActive;

    public EmployeeDTO() {
    }

    public EmployeeDTO(Long id, String name, String email, Integer age, LocalDate dateOfJoining, Boolean isActive) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.dateOfJoining = dateOfJoining;
        this.isActive = isActive;
    }

}