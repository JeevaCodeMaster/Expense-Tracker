package com.Xtend.Expense_Tracker.DTO;

import java.time.LocalDate;

public class ExpenseDTO {
    private Long id;
    private Double amount;
    private String description;
    private LocalDate date;
    public ExpenseDTO(Long id, Double amount, String description, LocalDate date, Long categoryId,
			String categoryName) {
		super();
		this.id = id;
		this.amount = amount;
		this.description = description;
		this.date = date;
		this.categoryId = categoryId;
		this.categoryName = categoryName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	private Long categoryId;
    private String categoryName;

    // constructor, getters, setters
}

