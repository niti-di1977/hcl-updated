package com.myfinbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myfinbank.pojo.Customer;



@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	Customer findByUsername(String username);
   
}
