package com.bankMnagaement.BankManagementTask.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidIdException.class)
    public ResponseEntity<ErrorResponse> handleInvalidId(InvalidIdException exception){
        ErrorResponse error = new ErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException exception){
        ErrorResponse error = new ErrorResponse(exception.getMessage(), HttpStatus.NO_CONTENT.value());
        return new ResponseEntity<>(error,HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(ObjectAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateUser(ObjectAlreadyExistException exception){
        ErrorResponse error = new ErrorResponse(exception.getMessage(), HttpStatus.IM_USED.value());
        return new ResponseEntity<>(error,HttpStatus.IM_USED);
    }
}
