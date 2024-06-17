package com.myfinbank.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myfinbank.pojo.Admin;
import com.myfinbank.repository.AdminRepository;



@Service
public class AdminService {
	
	@Autowired
	AdminRepository adRepo;
	
	@Autowired
	private MailSenderService senderService;

	
	public Admin addAdmins(Admin a) {
		return adRepo.save(a);
	}
	
	public List<Admin> findAll() {
		return adRepo.findAll();
	}
	
	public Admin updateAdmin(Admin a) {
	    return adRepo.save(a);
	}
	
	public void deleteAdminByID(int aId) {
        adRepo.deleteById(aId);  }
	
	public Admin updateAdminById(Admin a, int aId) {
		// TODO Auto-generated method stub
		  Admin ad = adRepo.findById(aId).orElse(null);

	        if (ad != null) {
	        	
	        	if(a.getEmail()!=null)
	        	{
	        		senderService.sendNewMail(ad.getEmail(),"Email changed",("Hello "+ad.getUsername()+" you have successfully changed your email\nplease contact us if it wasn't you ")); 
	        		ad.setEmail(a.getEmail());
	        	}
	        	
	        	
	        	if(a.getPassword()!=0)
	        	{
	        		senderService.sendNewMail(a.getEmail(),"Password changed",("Hello "+a.getUsername()+" you have successfully changed your password\nplease contact us if it wasn't you "));
	        		ad.setPassword(a.getPassword());
	        	}
	        		

	            return adRepo.save(ad);
	        } else {
	            return null; 
	        }
	}
	
	public boolean authenticateAdmin(String username, int password) {
		System.out.println("Authenticating user: " + username + " with password: " + password);
        Admin user = adRepo.findByUsername(username);
        if (user != null && user.getPassword() == password) { 
            return true;
        }
        return false;
    }

}




