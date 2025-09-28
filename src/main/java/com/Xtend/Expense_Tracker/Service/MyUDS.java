package com.Xtend.Expense_Tracker.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Xtend.Expense_Tracker.Entity.User;
import com.Xtend.Expense_Tracker.Repository.UserRepository;
import com.Xtend.Expense_Tracker.Service.MyUserDetails;




@Service

public class MyUDS implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	
	
	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		
		User user=userRepository.findByUsername(name);
		
		
		if(user==null) {
			System.out.println("no user found");
			throw new UsernameNotFoundException("user not found");
		}

		return new MyUserDetails(user);
		
	}
}
