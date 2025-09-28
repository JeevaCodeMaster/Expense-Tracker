package com.Xtend.Expense_Tracker.Repository;




import org.springframework.data.jpa.repository.JpaRepository;

import com.Xtend.Expense_Tracker.Entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	User findByUsername(String username);

}
