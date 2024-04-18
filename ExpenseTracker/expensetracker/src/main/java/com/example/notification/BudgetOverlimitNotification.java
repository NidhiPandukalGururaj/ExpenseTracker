package com.example.notification;

import com.example.repository.ExpenseRepository;
import com.example.repository.IncomeSourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.model.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Date;
import java.util.List;

@Component
public class BudgetOverlimitNotification implements NotificationStrategy {
    @Override
    public String generateNotification(Long userId, ExpenseRepository expenseRepository,
            IncomeSourceRepository incomeRepository) {
        StringBuilder notificationBuilder = new StringBuilder();

        YearMonth currentMonthYear = YearMonth.now();
        LocalDate firstDayOfMonth = currentMonthYear.atDay(1);
        LocalDate lastDayOfMonth = currentMonthYear.atEndOfMonth();

        Date startDate = java.sql.Date.valueOf(firstDayOfMonth);
        Date endDate = java.sql.Date.valueOf(lastDayOfMonth);

        List<Expense> expenses = expenseRepository.findByUserIdAndExpenseDateBetween(userId, startDate, endDate);

        Double totalIncome = incomeRepository.findTotalIncomeByUserId(userId);
        if (totalIncome != null) {
            double monthlyBudget = totalIncome / 12;
            double totalExpenses = expenses.stream().mapToDouble(Expense::getExpenseAmount).sum();

            double spentPercentage = (totalExpenses / monthlyBudget) * 100;
            if (spentPercentage >= 80) {
                notificationBuilder
                        .append("Budget Overlimit: Monthly expenses are close to the threshold, review your budget. ");
            }
        }

        return notificationBuilder.toString().trim();
    }
}
