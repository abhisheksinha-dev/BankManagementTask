package com.bankMnagaement.BankManagementTask.configs;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Value("${employeeService.base.url}")
    private String BASE_URL;

    @Bean
    @Qualifier("employeeRestClient")
    RestClient getEmployeeServiceRestClient(){
        return RestClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultStatusHandler(HttpStatusCode::is5xxServerError, ((request, response) -> {
                    throw new RuntimeException("server error occurred"); //it will handle exception globally.. no need to put in all the methods
                }))
                .build();
    }
}
