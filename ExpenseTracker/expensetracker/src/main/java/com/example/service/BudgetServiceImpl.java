package com.example.service;

import com.example.model.Budget;
import com.example.repository.BudgetRepository;
import com.example.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BudgetServiceImpl implements BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    @Override
    public List<Budget> findAllBudgetsByUserId(Long userId) {
        return budgetRepository.findByUserId(userId);
    }

  
    @Override
public Budget updateBudget(Long budgetId, Budget budgetDetails) {
    Budget budget = budgetRepository.findById(budgetId)
        .orElseThrow(() -> new RuntimeException("Budget not found with id " + budgetId));

    budget.setCategory(budgetDetails.getCategory());  // Check if category change is needed
    budget.setMonthlyBudget(budgetDetails.getMonthlyBudget()); // Ensure monthlyBudget is not null
    return budgetRepository.save(budget);
}

}
