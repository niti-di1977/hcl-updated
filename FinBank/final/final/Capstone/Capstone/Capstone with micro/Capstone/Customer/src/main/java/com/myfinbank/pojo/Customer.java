package com.myfinbank.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name ="Customer")
public class Customer {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)	
	private int cId;
	@Column(name="username", unique=true, nullable=false)
	private String username;
	private int password;
	private String email;
	private double balance;
	
	
	
	public void depositAmount(int amount) {
	    if (amount > 0) {
	        this.balance += amount;
	    }
	}
	
	public void withdrawAmount(int amount) {
		if (amount > 0 && amount <= balance) {
			this.balance -= amount;		}
	}
	
}