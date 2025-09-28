package com.Xtend.Expense_Tracker.Repository;






import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.Xtend.Expense_Tracker.Entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	List<Category> findByUser_Id(Long userId);

	Optional<Category> findByIdAndUserId(Long categoryId, Long userId);
}

