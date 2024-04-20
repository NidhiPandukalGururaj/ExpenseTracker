package com.example.service;

import com.example.model.*;
import java.util.List;
import java.util.Date;

public interface ExpenseService {
    List<Expense> getAllExpenses();

    Expense getExpenseById(Long id);

    Expense saveExpense(Expense expense);

    void deleteExpense(Long id);

    // List<Expense> findExpenseSummaryByUserIdAndDateRange(Long userId, Date startDate, Date endDate);

    List<Expense> getExpensesByUserId(Long userId);

}
