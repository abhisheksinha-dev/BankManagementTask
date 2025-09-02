package com.bankMnagaement.BankManagementTask.clients.impl;

import com.bankMnagaement.BankManagementTask.advices.ApiResponse;
import com.bankMnagaement.BankManagementTask.clients.EmployeeClient;
import com.bankMnagaement.BankManagementTask.dtos.EmployeeDTO;
import com.bankMnagaement.BankManagementTask.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeClientImpl implements EmployeeClient {

    private final RestClient restClient;

    Logger log = LoggerFactory.getLogger(EmployeeClientImpl.class);

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        log.trace("Trying to retrieve all employees in getAllEmployees()");

//        log.error("error log");
//        log.warn("warn log");
//        log.info("info log");
//        log.debug("debug log");
//        log.trace("trace log");

        try{
            log.info("Attempting to call the RestClient Method in getAllEmployees()");
            ApiResponse<List<EmployeeDTO>> employeeDTOList = restClient.get()
                    .uri("employees")
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, ((request, response) ->{
                        log.error(new String(response.getBody().readAllBytes()));
                        throw new ResourceNotFoundException("could not create Employee");
                    } ))
                    .body(new ParameterizedTypeReference<>() {
                    }); //since,List<EmployeeDto> i.e. List is Parameterized type as there is parameter EmployeeDto
            log.debug("Successfully retrieved the employees in getAllEmployees()");
            log.trace("Retrieved employees list in getAllEmployees() : {}", employeeDTOList.getData());
            return employeeDTOList.getData();
        } catch (Exception e) {
            log.error("Exception occurred in getAllEmployees",e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public EmployeeDTO getEmployeeById(Long employeeId) {
        log.trace("Trying to get Employees By Id in getEmployeeById: {}",employeeId);
        try{
            ApiResponse<EmployeeDTO> employeeResponse = restClient.get()
                    .uri("employees/{employeeId}", employeeId)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, ((request, response) ->{
                        log.error(new String(response.getBody().readAllBytes()));
                        throw new ResourceNotFoundException("could not create Employee");
                    } ))
                    .body(new ParameterizedTypeReference<>() {
                    });
            return employeeResponse.getData();

        }catch (Exception e){
            log.error("Exception occurred in getEmployeeById()",e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public EmployeeDTO createNewEmployee(EmployeeDTO employeeDTO) {
        log.trace("Trying to Create Employees in createNewEmployee: {}",employeeDTO);
        try {
            ResponseEntity<ApiResponse<EmployeeDTO>> employeeResponse = restClient.post()
//            ApiResponse<EmployeeDTO> employeeResponse = restClient.get()

                    .uri("employees")
                    .body(employeeDTO)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, ((request, response) ->{
                        log.debug("4xxClient error occurred during createNewEmployee()");
                        log.error(new String(response.getBody().readAllBytes()));
                        throw new ResourceNotFoundException("could not create Employee");
                    } ))
                    .toEntity(new ParameterizedTypeReference<>() {
                    });
//                    .body(new ParameterizedTypeReference<>() { //if we use Response Entity then we have to use Entity instead of body
//                    });
            log.trace("Successfully created a new Employee : {}", employeeResponse.getBody());
            return employeeResponse.getBody().getData();
//            return employeeResponse.getData();
        } catch (Exception e) {
            log.error("Exception occurred in createNewEmployee()",e);
            throw new RuntimeException(e);
        }
    }
}
