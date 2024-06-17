package com.myfinbank.service;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myfinbank.pojo.Accounts;
import com.myfinbank.pojo.Customer;
import com.myfinbank.repository.AccountsRepository;
import com.myfinbank.repository.CustomerRepository;


@Service
public class CustomerService {
	
	@Autowired
	CustomerRepository custRepo;
	
	@Autowired
	private MailSenderService senderService;
	
	@Autowired
	AccountsRepository AccRepo;

	
	public Customer addCustomer(Customer c) {
		return custRepo.save(c);
	}
	
	
	public Customer updateCustomer(Customer c) {
	    return custRepo.save(c);
	}
	
	
	public List<Customer> findAll() {
		return custRepo.findAll();
	}
	
	public void deleteCustomerByID(int id) {
        custRepo.deleteById(id);  }
	
	
	public void deposit(int cId, double additionalBalance) {
        Customer c = custRepo.findById(cId).orElse(null);
        if (c != null) {
            double currentBalance = c.getBalance();
            double newBalance = currentBalance + additionalBalance;
            c.setBalance(newBalance);
            custRepo.save(c);
        }
	}
	
	//withdraw
	public void withdraw(int cId, double amount) {
        Customer c = custRepo.findById(cId).orElse(null);
        if (c != null) {
            double currentBalance = c.getBalance();
            double newBalance = currentBalance - amount;
            c.setBalance(newBalance);
            if(c.getBalance()<5000)
            {
            	senderService.sendNewMail(c.getEmail(),"Low Balance",("The user: "+c.getUsername()+" and his balance is: "+c.getBalance()));

            }
            
            custRepo.save(c);
            
            
        }
	}
	
	//transfer funds
	public void transfer(int cId, int transferToCiD, double amount) {
        Customer c = custRepo.findById(cId).orElse(null);
        Customer c1 = custRepo.findById(transferToCiD).orElse(null);
        
        
        if (c != null && c1 != null) {
            double currentBalance = c.getBalance();
            double newBalance = currentBalance - amount;
            double currentBalance1 = c1.getBalance();
            double newBalance1 = currentBalance1 + amount;
            
            
            c.setBalance(newBalance);
            c1.setBalance(newBalance1);
          
            
            if(c.getBalance()<5000)
            {
            	senderService.sendNewMail(c.getEmail(),"Low Balance",("The user: "+c.getUsername()+" and his balance is: "+c.getBalance()));

            }
            
            
            custRepo.save(c);
            custRepo.save(c1);

        }
	}
	
	
	
	//login
	public boolean authenticateCustomer(String username, int password) {
		System.out.println("Authenticating user: " + username + " with password: " + password);
        Customer user = custRepo.findByUsername(username);
        if (user != null && user.getPassword() == password) { 
            return true;
        }
        return false;
    }
	
	public Customer getCustomerById(int cId) {
	    
	    Customer customer = custRepo.findById(cId).orElse(null);

	 
	    return customer;
	}
	
	
	
	
	//update by id
	public Customer updateCustomerById(Customer c, int cId) {
		// TODO Auto-generated method stub
		  Customer customer = custRepo.findById(cId).orElse(null);

	        if (customer != null) {
	        	
	        	if(c.getEmail()!=null)
	        	{
	        		senderService.sendNewMail(customer.getEmail(),"Email changed",("Hello "+customer.getUsername()+" you have successfully changed your email\nplease contact us if it wasn't you ")); 
	        		customer.setEmail(c.getEmail());
	        	}
	        	
	        	
	        	if(c.getPassword()!=0)
	        	{
	        		senderService.sendNewMail(c.getEmail(),"Password changed",("Hello "+c.getUsername()+" you have successfully changed your password\nplease contact us if it wasn't you "));
	        		customer.setPassword(c.getPassword());
	        	}
	        		

	            
	            return custRepo.save(customer);
	        } else {
	            return null; 
	        }
	}
	
	
	
	
	//Accounts
		 //generate randome account Id
		 public int randomeIdGenerator() {
			 	Random random = new Random();
		        int min = 10000000; // Minimum 8-digit number
		        int max = 99999999; // Maximum 8-digit number
		        return random.nextInt((max - min) + 1) + min;
		        
		        //you can add 
		        //check if the number exists in the database if it's exist change the number you can make in while loop
		 }
		 //transfer to other accounts
		// transfer to other accounts
		    public Accounts addAccount(int cId, Accounts a) {
		        Customer customer = custRepo.findById(cId).orElse(null);

		        if (customer != null) {
		            a.setAccountId(randomeIdGenerator());
		            a.setCId(customer); // Set the associated customer
		            return AccRepo.save(a);
		        } else {
		            // Handle the case where the customer with the given ID is not found
		            return null;
		        }
		    }
		    
		    //Emi calculato
		        public double calculateEMI(double loanAmount, double annualInterestRate, int loanTenureInMonths) {
		            // Monthly interest rate
		            double monthlyInterestRate = annualInterestRate / (12 * 100);

		            // Calculate EMI using the formula
		            return Math.round((loanAmount * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, loanTenureInMonths))
		                    / (Math.pow(1 + monthlyInterestRate, loanTenureInMonths) - 1)*100.0)/100.0;
		        }
		        
		     //apply for loan
		        public boolean loanApp(int cId, double loanAmount, double annualInterestRate, int loanTenureInMonths) {
		        	double bmi = calculateEMI(loanAmount, annualInterestRate, loanTenureInMonths);
		        	Customer customer = custRepo.findById(cId).orElse(null);
		        	double balance = customer.getBalance()/2;
		        	if(balance>bmi) {
		        		return true;
		        	}
		        	return false;
		        	
		        }
		        
	
	
	
	
	

}




