package com.example.service;

import com.example.model.Budget;

import java.util.List;

public interface BudgetService {
    List<Budget> findAllBudgetsByUserId(Long userId);
    Budget updateBudget(Long budgetId, Budget budgetDetails);
}
