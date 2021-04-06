package com.bg.loanservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bg.loanservice.entity.CustomerEntity;


@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

	CustomerEntity findBySsnNumber(String ssnNumber); 

}
