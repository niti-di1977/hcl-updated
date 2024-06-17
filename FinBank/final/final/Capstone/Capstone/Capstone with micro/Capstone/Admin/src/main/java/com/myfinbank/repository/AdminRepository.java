package com.myfinbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myfinbank.pojo.Admin;




@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
	
	Admin findByUsername(String username);


}
