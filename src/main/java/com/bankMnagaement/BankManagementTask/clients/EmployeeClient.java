package com.bankMnagaement.BankManagementTask.clients;

import com.bankMnagaement.BankManagementTask.dtos.EmployeeDTO;

import java.util.List;

public interface EmployeeClient {

    List<EmployeeDTO> getAllEmployees();
    EmployeeDTO getEmployeeById(Long employeeId);
    EmployeeDTO createNewEmployee(EmployeeDTO employeeDTO);
}
