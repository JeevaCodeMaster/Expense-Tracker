package com.Xtend.Expense_Tracker.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.Xtend.Expense_Tracker.Entity.User;
import com.Xtend.Expense_Tracker.Repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repo;
	
	
	private BCryptPasswordEncoder Encoder = new BCryptPasswordEncoder(12);
	
	
	


	public ResponseEntity<User> userRegister(User user) {
		user.setPassword(Encoder.encode(user.getPassword()));
		
		return new ResponseEntity<>(repo.save(user),HttpStatus.OK);
	}

	public ResponseEntity<User> findByUsername(String username) {
		// TODO Auto-generated method stub
		User u =repo.findByUsername(username);
		return   ResponseEntity.ok(u);
	}
	public  User findByUserName(String username) {
        return repo.findByUsername(username);
	}
	
	

}
