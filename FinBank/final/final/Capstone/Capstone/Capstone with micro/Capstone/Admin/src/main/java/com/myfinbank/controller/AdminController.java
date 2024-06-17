package com.myfinbank.controller;

import java.util.List;

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

import com.myfinbank.pojo.Admin;
import com.myfinbank.pojo.Admin;
import com.myfinbank.service.AdminService;



@RestController
@CrossOrigin(origins="*")
@RequestMapping("/api/admin")
public class AdminController {
	
	@Autowired
	private AdminService adService;
	
	@PostMapping("/addAdmins")
	public Admin addAdmins(@RequestBody Admin a) {
		
		return adService.addAdmins(a);	
		
	}
	
	@PutMapping("/updateAdmin/{aId}")
	public Admin updateadmin(
            @PathVariable int aId,
            @RequestBody Admin a) {
        return adService.updateAdminById(a, aId);
    }
	
	
	@DeleteMapping("/deleteAdminByID/{aId}")
    public ResponseEntity<?> deleteAdminByID(@PathVariable int aId) {
        try {
            adService.deleteAdminByID(aId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
	
	
	
	@GetMapping("/getAdmins")
	public List<Admin> getAdmin() {
		return adService.findAll();
	}
	
	@PostMapping("/authenticateAdmin")
    public boolean authenticateAdmin(@RequestBody Admin admin) {
        return adService.authenticateAdmin(admin.getUsername(), admin.getPassword());
    }

	
}




	
	
	

