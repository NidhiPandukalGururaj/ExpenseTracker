package com.example.notification;

import com.example.repository.ExpenseRepository;
import com.example.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.model.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Date;
import java.util.List;

@Component
public class BudgetOverlimitObserver implements NotificationObserver {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private IncomeRepository incomeRepository;

    @Override
    public void notify(String message) {
        // Get the current month and year
        YearMonth currentMonthYear = YearMonth.now();
        LocalDate firstDayOfMonth = currentMonthYear.atDay(1);
        LocalDate lastDayOfMonth = currentMonthYear.atEndOfMonth();

        // Convert LocalDate to Date
        Date startDate = java.sql.Date.valueOf(firstDayOfMonth);
        Date endDate = java.sql.Date.valueOf(lastDayOfMonth);

        // Retrieve expenses for the current month
        List<Expense> expenses = expenseRepository.findByExpenseDateBetween(startDate, endDate);

        // Check budget limit based on the user's income
        Double totalIncome = incomeRepository.findTotalIncomeByUserId(1L); // Assuming userId=1
        if (totalIncome != null) {
            double monthlyBudget = totalIncome / 12;
            double totalExpenses = expenses.stream()
                    .mapToDouble(Expense::getExpenseAmount)
                    .sum();

            double spentPercentage = (totalExpenses / monthlyBudget) * 100;
            if (spentPercentage >= 80) {
                // Notify user
                System.out.println("Budget Overlimit Notification: " + message);
            }
        }
    }
}
