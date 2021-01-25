package com.threeriver.datafeed.account.controller.v1;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.threeriver.resource.account.error.AccountNotFoundException;
import com.threeriver.resource.account.error.ApiErrorResponse;
import com.threeriver.resource.account.error.InvalidInputException;

@RestControllerAdvice
public class ApiExceptionHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);
    
	@ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleAccountNotFound(
    		AccountNotFoundException ex) {
    	logger.error("No account found with account number " + ex.getAccountNumber(), ex);
        ApiErrorResponse response = 
            new ApiErrorResponse("ERR-0001",
                "No account found with account number " + ex.getAccountNumber());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidInput(
    		InvalidInputException ex) {
    	logger.error("Account number is blank", ex);
        ApiErrorResponse response = 
            new ApiErrorResponse("ERR-0002",
                "Account number is blank");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ApiErrorResponse> handleUnexpectedException(
    		AccountNotFoundException ex) {
    	logger.error("Unexpected error", ex);
        ApiErrorResponse response = 
            new ApiErrorResponse("ERR-0003",
                "Unexpected error");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}