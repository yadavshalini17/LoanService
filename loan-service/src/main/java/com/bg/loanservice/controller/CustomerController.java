package com.bg.loanservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bg.loanservice.exception.DayLimitException;
import com.bg.loanservice.exception.InvalidInputException;
import com.bg.loanservice.request.CustomerRequest;
import com.bg.loanservice.response.CustomerResponse;
import com.bg.loanservice.service.CustomerService;


@RestController
public class CustomerController {
	@Autowired
    CustomerService customerService;

    @PostMapping("/customer")
    public CustomerResponse checkCustomerEligibility(@RequestBody  CustomerRequest customerRequest) throws InvalidInputException, DayLimitException {
        return customerService.checkCustomerEligibility(customerRequest);
    }
}
