package com.Xtend.Expense_Tracker.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Xtend.Expense_Tracker.DTO.CategoryDTO;
import com.Xtend.Expense_Tracker.Entity.Category;
import com.Xtend.Expense_Tracker.Service.CategoryService;

@RestController
@CrossOrigin(origins = "http://localhost:8085") 
@RequestMapping("/category")
public class CategoryController {
	
	@Autowired
	private CategoryService service;
	
	
	@PostMapping("/add-category")
	public CategoryDTO addCategory(@RequestBody Category categories) {
		 
		 return  service.addCategory(categories);
	}
	
	 @GetMapping("/user/{userId}")
	    public List<CategoryDTO> getUserCategories(@PathVariable Long userId) {
	        return service.getUserCategories(userId);
	    }
	 
	 @DeleteMapping("/delete/{categoryId}")
	 public CategoryDTO deleteCategory(@PathVariable Long categoryId) {
	     return service.deleteCategory(categoryId);
	 }
	 @GetMapping("/user/{userId}/category/{categoryId}")
	    public ResponseEntity<CategoryDTO> getCategoryByUserAndCategoryId(
	            @PathVariable Long userId,
	            @PathVariable Long categoryId) {
	        
	        return ResponseEntity.ok(service.getCategoryByUserAndCategoryId(userId, categoryId));
	    }

}
