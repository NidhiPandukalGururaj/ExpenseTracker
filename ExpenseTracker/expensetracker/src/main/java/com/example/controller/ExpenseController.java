package com.example.controller;

import com.example.model.*;

import com.example.repository.ExpenseRepository;
import com.example.repository.IncomeSourceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Arrays;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/expenses")
@CrossOrigin
public class ExpenseController {

    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private IncomeSourceRepository incomeRepository;

    @GetMapping("/")
    public String showHomePage(Model model) {
        return "index";
    }

    @GetMapping("/all")
    public String getAllExpenses(Model model) {
        // Get the current month and year
        YearMonth currentMonthYear = YearMonth.now();
        LocalDate firstDayOfMonth = currentMonthYear.atDay(1);
        LocalDate lastDayOfMonth = currentMonthYear.atEndOfMonth();

        // Convert LocalDate to Date
        Date startDate = java.sql.Date.valueOf(firstDayOfMonth);
        Date endDate = java.sql.Date.valueOf(lastDayOfMonth);

        // Retrieve expenses for the current month
        List<Expense> expenses = expenseRepository.findByExpenseDateBetween(startDate, endDate);

        model.addAttribute("expenses", expenses);
        return "all";
    }

    @GetMapping("/view/{id}")
    public String getExpenseById(@PathVariable Long id, Model model) {
        Optional<Expense> expense = expenseRepository.findById(id);
        if (expense.isPresent()) {
            model.addAttribute("expense", expense.get());
            return "view"; // Update the view name
        } else {
            return "expenses/error";
        }
    }

    @GetMapping("/add")
    public String showAddExpenseForm(Expense expense) {
        return "add"; // Update the view name
    }

    @PostMapping("/add")
    public String createExpense(@ModelAttribute Expense expense, Model model) {
        expenseRepository.save(expense);
        return "redirect:add"; // Update the redirect URL
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Expense ID"));
        model.addAttribute("expense", expense);
        return "edit"; // Update the view name
    }

    @PostMapping("/edit/{id}")
    public String updateExpense(@PathVariable Long id, @ModelAttribute Expense updatedExpense, Model model) {
        Expense existingExpense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        existingExpense.setExpenseDate(updatedExpense.getExpenseDate());
        existingExpense.setExpenseAmount(updatedExpense.getExpenseAmount());
        existingExpense.setExpenseCategory(updatedExpense.getExpenseCategory());
        existingExpense.setExpenseTransactionType(updatedExpense.getExpenseTransactionType());
        existingExpense.setExpenseReceipt(updatedExpense.getExpenseReceipt());

        expenseRepository.save(existingExpense);
        return "redirect:/expenses/all"; // Update the redirect URL
    }

    @GetMapping("/delete/{id}")
    public String deleteExpense(@PathVariable Long id, Model model) {
        expenseRepository.deleteById(id);
        return "redirect:/expenses/all"; // Update the redirect URL
    }

    // Method to check notifications
    // Method to check notifications
    @GetMapping("/notifications")
    public String checkNotifications(Model model) {
        StringBuilder notificationBuilder = new StringBuilder();

        // Check for unpaid rents
        boolean rentPaid = expenseRepository.existsByExpenseCategoryAndExpenseDateBefore(ExpenseCategory.RENT,
                new Date()); // Use java.util.Date here
        if (!rentPaid) {
            notificationBuilder.append("Rent Of This Month Not Paid Yet. ");
        }

        // Check for unpaid EMIs
        boolean emiPaid = expenseRepository.existsByExpenseCategoryAndExpenseDateBefore(ExpenseCategory.EMI,
                new Date()); // Use java.util.Date here
        if (!emiPaid) {
            notificationBuilder.append("EMI Of This Month Not Paid Yet. ");
        }

        // Check for unpaid utility bills
        boolean utilityPaid = expenseRepository.existsByExpenseCategoryAndExpenseDateBefore(ExpenseCategory.UTILITY,
                new Date()); // Use java.util.Date here
        if (!utilityPaid) {
            notificationBuilder.append("Utility Of This Month Not Paid Yet. ");
        }

        // Check budget overlimit
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
                notificationBuilder.append(
                        "Budget Overlimit: Monthly expenses are close to the threshold,review your budget. ");
            }
        }

        String notifications = notificationBuilder.toString().trim();
        if (!notifications.isEmpty()) {
            // Split the notifications into a list
            List<String> notificationList = Arrays.asList(notifications.split("\\.\\s+"));
            model.addAttribute("notifications", notificationList); // Add the notifications list to the model
        } else {
            // If all bills are paid and budget is within limits, add a message indicating
            // that
            model.addAttribute("notifications",
                    Arrays.asList("All bills paid for this month", "Budget is within limits"));
        }
        return "notifications";
    }

}
