package com.Xtend.Expense_Tracker.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Xtend.Expense_Tracker.Repository.ExpenseRepository;

@Service

public class AnalyticsService {

	@Autowired
	 private  ExpenseRepository expenseRepository;

	 private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM");

	 public Map<String, Double> getMonthlySpend(Long userId) {
		    List<Object[]> results = expenseRepository.getMonthlySpend(userId);
		    Map<String, Double> monthlySpend = new LinkedHashMap<>();

		    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

		    for (Object[] row : results) {
		        Object dateObj = row[0];
		        LocalDate month;

		        if (dateObj instanceof java.sql.Timestamp) {
		            month = ((java.sql.Timestamp) dateObj).toLocalDateTime().toLocalDate();
		        } else if (dateObj instanceof java.sql.Date) {
		            month = ((java.sql.Date) dateObj).toLocalDate();
		        } else if (dateObj instanceof java.time.Instant) {
		            month = ((java.time.Instant) dateObj).atZone(java.time.ZoneId.systemDefault()).toLocalDate();
		        } else {
		            throw new IllegalStateException("Unexpected type: " + dateObj.getClass());
		        }

		        Double total = ((Number) row[1]).doubleValue();
		        monthlySpend.put(month.format(formatter), total);
		    }

		    return monthlySpend;
		}
	    public List<Map<String, Object>> getCategoryBreakdown(Long userId) {
	        return expenseRepository.getCategoryBreakdown(userId);
	    }
}
