package com.bg.loanservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bg.loanservice.response.ExceptionResponse;

@ControllerAdvice
public class CustomerExceptionHandler {
	@ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidInputException(Exception exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse(exception.getMessage(),"400"));
    }

    @ExceptionHandler(DayLimitException.class)
    public ResponseEntity<ExceptionResponse> handleDaysLimitConstraintCrossedException(Exception exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse(exception.getMessage(),"400"));
    }
}
