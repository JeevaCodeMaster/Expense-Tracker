package com.Xtend.Expense_Tracker.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Xtend.Expense_Tracker.Entity.User;
import com.Xtend.Expense_Tracker.Service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:8085") 
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService service;
	
	@PostMapping("/register")
	public ResponseEntity<User> register(@RequestBody User user) {
		return service.userRegister(user);
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<User> getUser(@PathVariable String username ) {
		return service.findByUsername(username);
	}
	
	
	
	
	
	

}
