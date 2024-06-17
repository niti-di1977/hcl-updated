package com.myfinbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myfinbank.pojo.Accounts;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Integer> {

}
