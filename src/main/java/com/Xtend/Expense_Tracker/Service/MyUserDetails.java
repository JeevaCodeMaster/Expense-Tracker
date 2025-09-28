package com.Xtend.Expense_Tracker.Service;



import java.util.Collection;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.Xtend.Expense_Tracker.Entity.User;


public class MyUserDetails implements UserDetails{
	
	private User user;
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	public MyUserDetails(User users) {
		this.user=users;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}
	
	
	public Long getIds() {
		return user.getId();
	}
	
//	public String getRoles() {
//		return users.getRole();
//	}
//	public static String MyUserDetailss(Customer user) {
//	    return  "name"+user.getName()+"pass "+
//	     user.getId()+"role "+
//	   user.getRole();
//	}

}
