package com.example.controller;

import com.example.model.*;

import com.example.repository.ExpenseRepository;
import com.example.repository.IncomeSourceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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

    private Long getUserIdFromSession(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        System.out.println("user id" + userId);
        return userId;
    }

    @GetMapping("/expensedashboard")
    public String showHomePage(Model model) {
        return "expensedashboard";
    }

    @GetMapping("/")
    public String showdashboard(Model model) {
        return "expensedashboard";
    }

    @GetMapping("/all/{userId}")
    public String getAllExpenses(@PathVariable Long userId, Model model) {
        if (userId != null) {
            YearMonth currentMonthYear = YearMonth.now();
            LocalDate firstDayOfMonth = currentMonthYear.atDay(1);
            LocalDate lastDayOfMonth = currentMonthYear.atEndOfMonth();

            Date startDate = java.sql.Date.valueOf(firstDayOfMonth);
            Date endDate = java.sql.Date.valueOf(lastDayOfMonth);

            // Retrieve expenses for the specified user and month
            List<Expense> expenses = expenseRepository.findByUserIdAndExpenseDateBetween(userId, startDate, endDate);

            model.addAttribute("expenses", expenses);

            return "all";
        }
        return "redirect:error";
    }

    @GetMapping("/view/{id}")
    public String getExpenseById(@PathVariable Long id, Model model) {
        Optional<Expense> expense = expenseRepository.findById(id);
        if (expense.isPresent()) {
            model.addAttribute("expense", expense.get());
            return "view";
        } else {
            return "expenses/error";
        }
    }

    @GetMapping("/add")
    public String showAddExpenseForm(Expense expense) {
        return "add";
    }

    @PostMapping("/add")
    public String createExpense(@ModelAttribute Expense expense, Model model, HttpSession session) {
        Long userId = getUserIdFromSession(session);
        if (userId != null) {
            expense.setUserId(userId);
        }
        expenseRepository.save(expense);
        return "redirect:expensedashboard";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Expense ID"));
        model.addAttribute("expense", expense);
        return "edit";
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
        return "redirect:/expenses/expensedashboard";
    }

    @GetMapping("/delete/{id}")
    public String deleteExpense(@PathVariable Long id, Model model) {
        expenseRepository.deleteById(id);
        return "redirect:/expenses/expensedashboard";
    }

    @GetMapping("/notifications")
    public String checkNotifications(Model model) {
        StringBuilder notificationBuilder = new StringBuilder();

        boolean rentPaid = expenseRepository.existsByExpenseCategoryAndExpenseDateBefore(ExpenseCategory.RENT,
                new Date());
        if (!rentPaid) {
            notificationBuilder.append("Rent Of This Month Not Paid Yet. ");
        }

        boolean emiPaid = expenseRepository.existsByExpenseCategoryAndExpenseDateBefore(ExpenseCategory.EMI,
                new Date());
        if (!emiPaid) {
            notificationBuilder.append("EMI Of This Month Not Paid Yet. ");
        }

        boolean utilityPaid = expenseRepository.existsByExpenseCategoryAndExpenseDateBefore(ExpenseCategory.UTILITY,
                new Date());
        if (!utilityPaid) {
            notificationBuilder.append("Utility Of This Month Not Paid Yet. ");
        }

        YearMonth currentMonthYear = YearMonth.now();
        LocalDate firstDayOfMonth = currentMonthYear.atDay(1);
        LocalDate lastDayOfMonth = currentMonthYear.atEndOfMonth();

        Date startDate = java.sql.Date.valueOf(firstDayOfMonth);
        Date endDate = java.sql.Date.valueOf(lastDayOfMonth);

        List<Expense> expenses = expenseRepository.findByExpenseDateBetween(startDate, endDate);

        Double totalIncome = incomeRepository.findTotalIncomeByUserId(1L);
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
            List<String> notificationList = Arrays.asList(notifications.split("\\.\\s+"));
            model.addAttribute("notifications", notificationList);
        } else {
            model.addAttribute("notifications",
                    Arrays.asList("All bills paid for this month", "Budget is within limits"));
        }
        return "notifications";
    }

    @GetMapping("/error")
    public String showErrorPage() {
        return "error";
    }

}
