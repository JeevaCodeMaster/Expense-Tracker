package com.Xtend.Expense_Tracker.Controller;

import java.io.ByteArrayOutputStream;
import java.net.http.HttpHeaders;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Xtend.Expense_Tracker.DTO.ExpenseDTO;
import com.Xtend.Expense_Tracker.Entity.Expense;
import com.Xtend.Expense_Tracker.Repository.CategoryRepository;
import com.Xtend.Expense_Tracker.Repository.UserRepository;

import com.Xtend.Expense_Tracker.Service.ExpenseService;

@RestController
@CrossOrigin(origins = "http://localhost:8085") 
@RequestMapping("/expense")
public class ExpenseController {
	
	@Autowired
	private ExpenseService service;
	
	@Autowired
    private UserRepository userRepo;

    @Autowired
    private CategoryRepository catRepo;
	
	@PostMapping("/add-expense")
	public ExpenseDTO addExpense(@RequestBody ExpenseDTO expense) {
		
		return service.addExpense(expense);
	}
	
	@GetMapping("/user/{userId}")
	public List<ExpenseDTO> getUserExpenses(@PathVariable Long userId){
		
		return service.getUserExpenses(userId); 
		
	}
	
	@GetMapping("/user/{userId}/category/{categoryId}")
	public List<ExpenseDTO> getUserExpensesByCategory(
	        @PathVariable Long userId,
	        @PathVariable Long categoryId) {
	    return service.getUserExpensesByCategory(userId, categoryId);
	}

	
	@DeleteMapping("/delete/{id}")
	public void deleteExpense(@PathVariable Long id ) {
		
		 service.deleteExpense(id);
	}
	


}
