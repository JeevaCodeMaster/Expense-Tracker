package com.Xtend.Expense_Tracker.Service;


import com.Xtend.Expense_Tracker.Entity.Expense;
import com.Xtend.Expense_Tracker.Entity.Category;
import com.Xtend.Expense_Tracker.Entity.User;
import com.Xtend.Expense_Tracker.Repository.CategoryRepository;
import com.Xtend.Expense_Tracker.Repository.UserRepository;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExpenseCSVHelper {

    public static List<Expense> csvToExpenses(InputStream is, UserRepository userRepo, CategoryRepository catRepo, Long userId) throws Exception {
        CSVReader reader = new CSVReader(new InputStreamReader(is));
        List<Expense> expenses = new ArrayList<>();
        String[] line;
        reader.readNext(); // skip header

        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        while ((line = reader.readNext()) != null) {
            Expense expense = new Expense();
            expense.setAmount(Double.parseDouble(line[0]));
            expense.setDescription(line[1]);
            expense.setDate(LocalDate.parse(line[2]));
            Long categoryId = Long.parseLong(line[3]);
            Category cat = catRepo.findById(categoryId).orElse(null);
            expense.setCategory(cat);
            expense.setUser(user);
            expenses.add(expense);
        }
        return expenses;
    }

    public static void expensesToCSV(List<Expense> expenses, OutputStream os) throws IOException {
        CSVWriter writer = new CSVWriter(new OutputStreamWriter(os));
        // header
        writer.writeNext(new String[]{"amount","description","date","categoryId"});
        for (Expense e : expenses) {
            writer.writeNext(new String[]{
                    String.valueOf(e.getAmount()),
                    e.getDescription(),
                    e.getDate().toString(),
                    e.getCategory() != null ? String.valueOf(e.getCategory().getId()) : ""
            });
        }
        writer.flush();
        writer.close();
    }
}



