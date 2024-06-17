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
@Table(name ="Admin")
public class Admin {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)	
	private int aId;
	@Column(name="username", unique=true, nullable=false)
	private String username;
	private int password;
	private String email;
	
	
	
}