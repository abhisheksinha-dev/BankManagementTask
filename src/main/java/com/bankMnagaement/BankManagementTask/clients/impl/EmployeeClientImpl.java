package com.bankMnagaement.BankManagementTask.clients.impl;

import com.bankMnagaement.BankManagementTask.advices.ApiResponse;
import com.bankMnagaement.BankManagementTask.clients.EmployeeClient;
import com.bankMnagaement.BankManagementTask.dtos.EmployeeDTO;
import com.bankMnagaement.BankManagementTask.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
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

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        try{
            ApiResponse<List<EmployeeDTO>> employeeDTOList = restClient.get()
                    .uri("employees")
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    }); //since,List<EmployeeDto> i.e. List is Parameterized type as there is parameter EmployeeDto
            return employeeDTOList.getData();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public EmployeeDTO getEmployeeById(Long employeeId) {
        try{
            ApiResponse<EmployeeDTO> employeeResponse = restClient.get()
                    .uri("employees/{employeeId}", employeeId)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });
            return employeeResponse.getData();

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public EmployeeDTO createNewEmployee(EmployeeDTO employeeDTO) {
        try {
            ResponseEntity<ApiResponse<EmployeeDTO>> employeeResponse = restClient.post()
                    .uri("employees")
                    .body(employeeDTO)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, ((request, response) ->{
                        System.out.println(new String(response.getBody().readAllBytes()));
                        throw new ResourceNotFoundException("could not create Employee");
                    } ))
                    .toEntity(new ParameterizedTypeReference<>() { //if we use Response Entity then we have to use Entity instead of body
                    });

            return employeeResponse.getBody().getData();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
