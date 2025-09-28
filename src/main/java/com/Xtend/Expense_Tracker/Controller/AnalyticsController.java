package com.Xtend.Expense_Tracker.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Xtend.Expense_Tracker.Service.AnalyticsService;

@RestController
@CrossOrigin(origins = "http://localhost:8085") 
@RequestMapping("/analytics")

public class AnalyticsController {
	
	
	@Autowired
	private  AnalyticsService analyticsService;

	 @GetMapping("/monthly-spend/{userId}")
	    public ResponseEntity<Map<String, Double>> getMonthlySpend(@PathVariable Long userId) {
	        Map<String, Double> monthlySpend = analyticsService.getMonthlySpend(userId);
	        return ResponseEntity.ok(monthlySpend);
	    }
    @GetMapping("/category-breakdown/{userId}")
    public ResponseEntity<List<Map<String, Object>>> getCategoryBreakdown(@PathVariable Long userId) {
        return ResponseEntity.ok(analyticsService.getCategoryBreakdown(userId));
    }

}
