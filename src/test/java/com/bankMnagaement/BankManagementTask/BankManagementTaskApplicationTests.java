package com.bankMnagaement.BankManagementTask;

import com.bankMnagaement.BankManagementTask.clients.EmployeeClient;
import com.bankMnagaement.BankManagementTask.dtos.EmployeeDTO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BankManagementTaskApplicationTests {

	@Autowired
	private EmployeeClient employeeClient;

//	@Test
//	void contextLoads() {
//	}

	@Test
	@Order(3)
	void getAllEmployees(){
		List<EmployeeDTO> employeeDTOList = employeeClient.getAllEmployees();
		System.out.println(employeeDTOList);
	}

	@Test
	@Order(2)
	void getEmployeeByIdTest(){
		EmployeeDTO employeeDTO = employeeClient.getEmployeeById(1L);
		System.out.println(employeeDTO);
	}

	@Test
	@Order(1)
	void createNewEmployeeTest(){
		EmployeeDTO employeeDTO = EmployeeDTO.builder()
				.id(null)
				.age(29)
				.role("USER")
				.email("anuj@gmail.com")
				.password("abhi@123")
				.name("Anuj")
				.dateOfJoining(LocalDate.of(2017,1,1))
				.isActive(true)
				.salary(2500.80)
				.build();
		EmployeeDTO savedEmployeeDTO = employeeClient.createNewEmployee(employeeDTO);
		System.out.println(savedEmployeeDTO);
	}
}
