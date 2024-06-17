package com.myfinbank.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myfinbank.pojo.Accounts;
import com.myfinbank.pojo.Customer;
import com.myfinbank.repository.CustomerRepository;
import com.myfinbank.service.CustomerService;



@RestController
@CrossOrigin(origins="*")
@RequestMapping("/api/cust")
public class CustomerController {
	
	@Autowired
	private CustomerService custService;
	
	@PostMapping("/addCustomer")
	public Customer addCustomer(@RequestBody Customer c) {
		
		return custService.addCustomer(c);		
		
	}
	
	@PutMapping("/updateCustomer/{cId}")
	public Customer updateCustomer(
            @PathVariable int cId,
            @RequestBody Customer c) {
        return custService.updateCustomerById(c,cId );
    }
	
	

	
	
	
	@GetMapping("/getCustomers")
	public List<Customer> getCustomers() {
		return custService.findAll();
	}
	
	@GetMapping("/getCustomer/{id}")
    public Customer getCustomerById(@PathVariable("id") int customerId) {
        return custService.getCustomerById(customerId);
    }
	
	@DeleteMapping("/deleteCustomerById/{id}")
    public ResponseEntity<?> deleteCustomerByID(@PathVariable int id) {
        try {
            custService.deleteCustomerByID(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
	

		//deposit
		@PostMapping("/{cId}/deposit")
	    public void deposit(@PathVariable int cId, @RequestBody Map<String, Double> requestBody) {
			double additionalBalance = requestBody.get("additionalBalance");
	        custService.deposit(cId, additionalBalance);
	    }
		
		//withdraw
		@PostMapping("/{cId}/withdraw")
	    public void withdraw(@PathVariable int cId, @RequestBody Map<String, Double> requestBody) {
			double amount = requestBody.get("amount");
	        custService.withdraw(cId, amount);
	    }
		
		//transfer funds
		@PostMapping("/{cId}/transferfunds")
		public void transfer(@PathVariable int cId, @RequestBody Map<String, Double> requestBody) {
		    double amount = requestBody.get("amount");
		    int transferToCId = requestBody.get("transferToCId").intValue(); // assuming transferToCId is also present in the request body

		    custService.transfer(cId, transferToCId, amount);
		}
		
		
		@Autowired
		CustomerRepository custRepo;
		@PostMapping("/authenticateCustomer")
		public ResponseEntity<Map<String, Object>> authenticateCustomer(@RequestBody Customer customer) {
	        boolean isAuthenticated = custService.authenticateCustomer(customer.getUsername(), customer.getPassword());
	        Map<String, Object> response = new HashMap<>();
	        if (isAuthenticated) {
	            response.put("isAuthenticated", true);
	            response.put("cId", custRepo.findByUsername(customer.getUsername()).getCId());
	        } else {
	            response.put("isAuthenticated", false);
	        }
	        return ResponseEntity.ok(response);
	    }
		
		
		
		//accounts
	    @PostMapping("/{cId}/addAccount")
		public Accounts addAccount(@PathVariable int cId,@RequestBody Accounts a) {
			
			return custService.addAccount(cId,a);		
			
		}
	    
	    //loan emi calc
	    @PostMapping("/calculate-emi")
	    public double calculateEMI(@RequestBody Map<String, Double> requestBody) {
	    	double loanAmount = requestBody.get("loanAmount");;
	    	double annualInterestRate = requestBody.get("annualInterestRate");
	    	int loanTenureInMonths = requestBody.get("loanTenureInMonths").intValue();
	        return custService.calculateEMI(loanAmount, annualInterestRate, loanTenureInMonths);
	    }
	    
	    @PostMapping("{cId}/apply-for-loan")
	    public boolean loanApp(@PathVariable int cId, @RequestBody Map<String, Double> requestBody)
	    {
	    	double loanAmount = requestBody.get("loanAmount");;
	    	double annualInterestRate = requestBody.get("annualInterestRate");
	    	int loanTenureInMonths = requestBody.get("loanTenureInMonths").intValue();
	    	return custService.loanApp(cId, loanAmount, annualInterestRate, loanTenureInMonths);
	    }
		
		
	

}




	
	
	

