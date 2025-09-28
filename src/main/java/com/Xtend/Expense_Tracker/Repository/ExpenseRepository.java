package com.Xtend.Expense_Tracker.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Xtend.Expense_Tracker.Entity.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
	
	List<Expense> findByUserId(Long userId);
	List<Expense> findByUserIdAndCategoryId(Long UseId,Long CategoryId);
	

	@Query(value = """
            SELECT DATE_TRUNC('month', e.expense_date) AS month,
                   SUM(e.amount) AS total_spent
            FROM expenses e
            WHERE e.user_id = :userId
            GROUP BY DATE_TRUNC('month', e.expense_date)
            ORDER BY month
            """, nativeQuery = true)
    List<Object[]> getMonthlySpend(@Param("userId") Long userId);

    @Query(value = """
        SELECT 
            c.name AS category,
            SUM(e.amount) AS total_spent
        FROM expenses e
        JOIN categories c ON e.category_id = c.id
        WHERE e.user_id = :userId
        GROUP BY c.name
        ORDER BY total_spent DESC
        """, nativeQuery = true)
    List<Map<String, Object>> getCategoryBreakdown(@Param("userId") Long userId);

}
