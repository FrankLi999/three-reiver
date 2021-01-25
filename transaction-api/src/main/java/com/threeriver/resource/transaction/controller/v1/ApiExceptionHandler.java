package com.threeriver.resource.transaction.controller.v1;


import java.util.List;
import java.util.stream.Collectors;

import javax.security.auth.login.AccountNotFoundException;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.threeriver.resource.transaction.error.ApiErrorResponse;
import com.threeriver.resource.transaction.error.InvalidInputException;

@RestControllerAdvice
public class ApiExceptionHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);
    
    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidInput(
    		InvalidInputException ex) {
    	logger.error("Account number is blank", ex);
        ApiErrorResponse response = 
            new ApiErrorResponse("ERR-1000",
                "Account number is blank");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<ApiErrorResponse> handleConstraintViolation(ConstraintViolationException ex)
    {
        List<String> details = ex.getConstraintViolations()
                                    .parallelStream()
                                    .map(e -> e.getMessage())
                                    .collect(Collectors.toList());
 
        ApiErrorResponse response = new ApiErrorResponse("ERR-1001",
                "Account number is blank", details);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ApiErrorResponse> handleUnexpectedException(
    		AccountNotFoundException ex) {
    	logger.error("Unexpected error", ex);
        ApiErrorResponse response = 
            new ApiErrorResponse("ERR-1003",
                "Unexpected error");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}