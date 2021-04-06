package com.bg.loanservice.entity;

import java.util.Date;

import javax.persistence.*;

import com.bg.loanservice.request.CustomerRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="customer_detail")
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long idCustomer;

    @Column(name = "ssn_number")
    private String ssnNumber;

    @Column(name = "loan_amount")
    private Double loanAmount;

    @Column(name = "annual_income")
    private Double annualIncome;

    @Column(name = "applicationDate")
    private Date applicationDate;



    public CustomerEntity(CustomerRequest customerRequest) {
        this.ssnNumber = customerRequest.getSsnNumber().trim();
        this.loanAmount = customerRequest.getLoanAmount();
        this.annualIncome = customerRequest.getAnnualIncome();
        this.applicationDate = new Date();
    }

    public CustomerEntity(String ssnNumber, Double loanAmount, Double annualIncome, Date applicationDate) {
        this.ssnNumber = ssnNumber;
        this.loanAmount = loanAmount;
        this.annualIncome = annualIncome;
        this.applicationDate = applicationDate;
    }
}


 


