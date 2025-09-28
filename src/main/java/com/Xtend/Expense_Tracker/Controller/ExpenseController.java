package com.Xtend.Expense_Tracker.Controller;

import java.io.ByteArrayOutputStream;
import java.net.http.HttpHeaders;
import java.util.List;
import com.Xtend.Expense_Tracker.Helper.ExpenseCSVHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
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

	@PostMapping("/import")
    public ResponseEntity<String> importCSV(@RequestParam("file") MultipartFile file,
                                            @RequestParam("userId") Long userId) {
        if (file.isEmpty()) return ResponseEntity.badRequest().body("No file selected");

        try {
            List<Expense> expenses = ExpenseCSVHelper.csvToExpenses(file.getInputStream(), userRepo, catRepo, userId);
            for (Expense e : expenses) expenseService.addExpenseDTO(e); // your service method
            return ResponseEntity.ok("CSV imported successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportCSV(@RequestParam("userId") Long userId) {
        try {
            List<Expense> expenses = expenseService.getUserExpenses(userId);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ExpenseCSVHelper.expensesToCSV(expenses, os);

            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=expenses.csv");
            headers.set(HttpHeaders.CONTENT_TYPE, "text/csv");

            return new ResponseEntity<>(os.toByteArray(), headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
	


}
