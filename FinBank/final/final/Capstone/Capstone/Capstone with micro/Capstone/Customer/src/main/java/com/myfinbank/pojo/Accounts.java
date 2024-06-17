package com.myfinbank.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "Accounts")
public class Accounts {

    @Id
    private int accountId;
    
    private String accountType;
    private double accountBalance;
    //add date

    @ManyToOne
    @JoinColumn(name = "cId")
    private Customer cId;
    
    

   
}
