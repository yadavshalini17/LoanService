package com.bg.loanservice.response;

import org.springframework.http.HttpStatus;

import lombok.Data;
@Data
public class CustomerResponse {
	  private String ssnNumber;
	    private Double eligibleLoanAmount;
	    private String message;
	    private HttpStatus status;
}
