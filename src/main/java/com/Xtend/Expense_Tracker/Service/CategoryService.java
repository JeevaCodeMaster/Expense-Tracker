package com.Xtend.Expense_Tracker.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Xtend.Expense_Tracker.DTO.CategoryDTO;
import com.Xtend.Expense_Tracker.Entity.Category;
import com.Xtend.Expense_Tracker.Entity.User;
import com.Xtend.Expense_Tracker.Repository.CategoryRepository;
import com.Xtend.Expense_Tracker.Repository.UserRepository;
@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repo;
	
	@Autowired
	private UserRepository userRepository;
	
	
	public CategoryDTO toDTO(Category category) {  
	    Long parentId = category.getParent() != null ? category.getParent().getId() : null;
	    String parentName = category.getParent() != null ? category.getParent().getName() : null;
	    return new CategoryDTO(category.getId(), category.getName(), parentId, parentName);
	}


	    public CategoryDTO addCategory(Category category) {
	    	
	    Category saved = repo.save(category);
	    return toDTO(saved);
	    }

	    public List<CategoryDTO> getUserCategories(Long userId) {
	        return repo.findByUser_Id(userId)
	                   .stream()
	                   .map(this::toDTO)  
	                   .toList();
	    }

	    
	    public CategoryDTO deleteCategory(Long categoryId) {
	        Category category = repo.findById(categoryId)
	                .orElseThrow(() -> new RuntimeException("Category not found"));

	        // Optional: check if it has subcategories or expenses
	        if (!category.getSubcategories().isEmpty()) {
	            throw new RuntimeException("Cannot delete category with subcategories");
	        }

	        repo.delete(category);  
	        return toDTO(category);  
	    }
	    
	    public CategoryDTO getCategoryByUserAndCategoryId(Long userId, Long categoryId) {
	        Category cate= repo.findByIdAndUserId(categoryId, userId)
	                   .orElseThrow(() -> new RuntimeException(
	                       "Category not found for userId: " + userId + " and categoryId: " + categoryId));
	   
	    return toDTO(cate);
	    }


}
