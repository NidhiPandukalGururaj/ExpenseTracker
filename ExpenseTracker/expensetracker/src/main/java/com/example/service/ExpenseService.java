package com.example.service;

import com.example.model.*;
import java.util.List;

public interface ExpenseService {
    List<Expense> getAllExpenses();

    Expense getExpenseById(Long id);

    Expense saveExpense(Expense expense);

    void deleteExpense(Long id);
}
