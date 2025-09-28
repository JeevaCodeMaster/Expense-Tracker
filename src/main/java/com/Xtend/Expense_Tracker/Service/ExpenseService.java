package com.Xtend.Expense_Tracker.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Xtend.Expense_Tracker.DTO.ExpenseDTO;
import com.Xtend.Expense_Tracker.Entity.Category;
import com.Xtend.Expense_Tracker.Entity.Expense;
import com.Xtend.Expense_Tracker.Entity.User;
import com.Xtend.Expense_Tracker.Repository.CategoryRepository;
import com.Xtend.Expense_Tracker.Repository.ExpenseRepository;
import com.Xtend.Expense_Tracker.Repository.UserRepository;
@Service
public class ExpenseService {
	
	@Autowired
	private ExpenseRepository repo;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private CategoryRepository categoryRepository;
//	-------------------------------------------------------------------------------------

	public ExpenseDTO addExpense(ExpenseDTO expense) {
		
	
		Expense e = toEntity(expense);
		
        Expense saved = repo.save(e);
        return toDTO(saved);
	}

	public List<ExpenseDTO> getUserExpenses(Long userId) {
        return repo.findByUserId(userId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

	public void deleteExpense(Long id) {
		// TODO Auto-generated method stub
		repo.deleteById(id);;
	}

	public List<ExpenseDTO> getUserExpensesByCategory(Long userId, Long categoryId) {
	    return repo.findByUserIdAndCategoryId(userId, categoryId)
	               .stream()
	               .map(this::toDTO)  // Convert entity to DTO
	               .toList();
	}

	
	
//	-----------------------------------------------------------------------
	public ExpenseDTO toDTO(Expense e) {
        return new ExpenseDTO(
                e.getId(),
                e.getAmount(),
                e.getDescription(),
                e.getDate(),
                e.getCategory().getId(),
                e.getCategory().getName()
        );
    }

    // Convert DTO to entity for creating/updating
    public Expense toEntity(ExpenseDTO dto) {
        Expense e = new Expense();
        e.setId(dto.getId());
        e.setAmount(dto.getAmount());
        e.setDescription(dto.getDescription());
        e.setDate(dto.getDate());

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        e.setCategory(category);

        User user = userRepository.findById(1L) // replace with actual user from auth/session
                .orElseThrow(() -> new RuntimeException("User not found"));
        e.setUser(user);

        return e;
    }

	

}
