# Project Title

# Expense Tracker 

## 🔹 Project Overview
The **Expense Tracker** is a Spring Boot-based application to track daily expenses, categorize them, and visualize spending analytics. It uses **PostgreSQL** as the database and integrates **Spring Security** with JWT authentication for secure login and registration.  

> ⚠️ **Note:** Backend APIs are fully functional, but the frontend is partially complete. Dashboard for expense management and analytics is still under development. Table relationships are implemented in the backend, but some frontend features are pending.

---

## 🔹 Features
- User registration and login with JWT authentication
- Add, update, delete, and view expenses
- Categorize expenses (Food, Travel, Bills, etc.)
- Monthly spending overview (API ready)
- Category-wise spending breakdown (API ready)
- Future plans:
  - Complete frontend dashboard for expense management
  - Analytics visualization
  - Integration of full frontend with backend APIs
  - CSV
  

---

## 🔹 Table Relationships (Backend)
- **User** ↔ **Expense** → One-to-Many (a user can have multiple expenses)
- **Expense** ↔ **Category** → Many-to-One (an expense belongs to one category)
- **Category** ↔ **Expense** → One-to-Many (a category can have multiple expenses)

---

## 🔹 Prerequisites
- Java 17+
- Maven
- PostgreSQL
- Git
- IDE Eclipse
- Postman API.

---

## 🔹 Step-by-Step Setup Guide

### 1. Clone the repository
```bash
git clone https://github.com/JeevaCodeMaster/Expense-Tracker.git
cd Expense-Tracker
