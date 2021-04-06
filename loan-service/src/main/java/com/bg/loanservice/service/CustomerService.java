package com.bg.loanservice.service;

import com.bg.loanservice.entity.CustomerEntity;
import com.bg.loanservice.exception.DayLimitException;
import com.bg.loanservice.exception.InvalidInputException;
import com.bg.loanservice.request.CustomerRequest;
import com.bg.loanservice.response.CustomerResponse;

public interface CustomerService {
	 public CustomerResponse checkCustomerEligibility(CustomerRequest customerRequest) throws InvalidInputException, DayLimitException;
	    public CustomerResponse saveCustomerRecords(CustomerEntity customer);
	    public CustomerResponse updateCustomerRecords(CustomerEntity customerObj, CustomerEntity customer);
	    public Integer getCustomerScore(String ssnNumber);
	    public Double getLoanAmount(Integer score,CustomerEntity customer);
	    public void validateCustomerRequest(CustomerRequest customerRequest) throws InvalidInputException;
	    public boolean validateConstraint(CustomerEntity customerObj);
}
