package com.bg.loanservice.service.impl;

import java.time.ZonedDateTime;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bg.loanservice.constants.CustomerConstants;
import com.bg.loanservice.entity.CustomerEntity;
import com.bg.loanservice.exception.DayLimitException;
import com.bg.loanservice.exception.InvalidInputException;
import com.bg.loanservice.repository.CustomerRepository;
import com.bg.loanservice.request.CustomerRequest;
import com.bg.loanservice.response.CustomerResponse;
import com.bg.loanservice.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService{
	@Autowired
	CustomerRepository repository;
	 @Autowired
	    private RestTemplate restTemplate;

	    private static Double MIN_LOAN_AMOUNT = (double)1000;
	    
	    private static Double MIN_ANNUAL_INCOME = (double)1000;
	    @Override
		public CustomerResponse checkCustomerEligibility(CustomerRequest customerRequest)throws InvalidInputException,DayLimitException {
		 validateCustomerRequest(customerRequest);
	    CustomerEntity customer = new CustomerEntity(customerRequest);
	    Integer score = 0;
	    Double loan = 0.0;
	    CustomerResponse customerResponse = new CustomerResponse();
	    System.out.println("########### before calling credit score = ");

	    CustomerEntity customerObj = repository.findBySsnNumber(customerRequest.getSsnNumber().trim());

	    //case when user applied at least once
	    if(customerObj!=null) {
	        if(validateConstraint(customerObj)){
	            throw new DayLimitException(CustomerConstants.CONSTRAINT_CROSSED);

	        }

			score = getCustomerScore(customerObj.getSsnNumber());
			loan = getLoanAmount(score, customer);
			customerResponse = updateCustomerRecords(customerObj, customer);
			customerResponse.setSsnNumber(customer.getSsnNumber());
			customerResponse.setEligibleLoanAmount(loan);
			return customerResponse;
	    }
	    //When this user records are not in DB means never applied for loan
	    else {
	        score = getCustomerScore(customer.getSsnNumber());
	        loan =  getLoanAmount(score,customer);
	        customerResponse = saveCustomerRecords(customer);
	        customerResponse.setSsnNumber(customer.getSsnNumber());
	        customerResponse.setEligibleLoanAmount(loan);
	        return customerResponse;
	    }
		}

	@Override
	public CustomerResponse saveCustomerRecords(CustomerEntity customer) {
		  CustomerResponse customerResponse = new CustomerResponse();
	        customer.setApplicationDate(new Date());
	        repository.save(customer);
	        customerResponse.setMessage(CustomerConstants.CUSTOMER_SAVED);
	        customerResponse.setStatus(HttpStatus.OK);
	        return customerResponse;
	}

	@Override
	public CustomerResponse updateCustomerRecords(CustomerEntity customerObj, CustomerEntity customer) {
		CustomerResponse customerResponse = new CustomerResponse();
        customerObj.setLoanAmount(customer.getLoanAmount());
        customerObj.setApplicationDate(new Date());
        customerObj.setAnnualIncome(customer.getAnnualIncome());
        repository.save(customerObj);
        customerResponse.setMessage(CustomerConstants.CUSTOMER_UPDATED);
        customerResponse.setStatus(HttpStatus.OK);
        return customerResponse;}

	@Override
	public Integer getCustomerScore(String ssnNumber) {
		String url = "http://credit-service/credit/{ssnNumber}";
        Integer score = restTemplate.getForObject(url, Integer.class, ssnNumber);
        System.out.println("score "+score);
        return score;
	}

	@Override
	public Double getLoanAmount(Integer score, CustomerEntity customer) {
		 if(score!=null && score>=750 && customer.getLoanAmount()!=null && customer.getLoanAmount()>0.0)
	        {
	            return customer.getAnnualIncome()/2;
	        }
	        return 0.0;
	}

	@Override
	public void validateCustomerRequest(CustomerRequest customerRequest) throws InvalidInputException {
		 if(null == customerRequest)
	        {
	            throw new InvalidInputException("Please provide customer details");

	        }
	        if(StringUtils.isBlank(customerRequest.getSsnNumber()))
	        {
	            throw new InvalidInputException("Please provide valid SSN number");
	        }
	        
	        if(null == customerRequest.getLoanAmount() )
	        {
	            throw new InvalidInputException("Please provide Loan amount");

	        }
	        
	        if(customerRequest.getLoanAmount()<MIN_LOAN_AMOUNT)
	        {
	            throw new InvalidInputException("Please provide valid Loan amount > "+MIN_LOAN_AMOUNT);
	        }
	        
	        if(null == customerRequest.getAnnualIncome())
	        {
	            throw new InvalidInputException("Please provide Annual income");
	        }
	        
	        if(customerRequest.getAnnualIncome()<MIN_ANNUAL_INCOME)
	        {
	            throw new InvalidInputException("Annual income is not eligible");
	        }
		
	}

	@Override
	public boolean validateConstraint(CustomerEntity customerObj) {
		ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime thirtyDaysAgo = now.plusDays(-30);
        return !customerObj.getApplicationDate().toInstant().isBefore(thirtyDaysAgo.toInstant());
	}

	

}
