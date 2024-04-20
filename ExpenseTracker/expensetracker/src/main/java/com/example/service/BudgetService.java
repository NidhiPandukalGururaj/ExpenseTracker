package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Budget;
import com.example.repository.BudgetRepository;
import com.example.repository.ExpenseRepository;

import java.util.List;

@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final ExpenseRepository expenseRepository;

    @Autowired
    public BudgetService(BudgetRepository budgetRepository, ExpenseRepository expenseRepository) {
        this.budgetRepository = budgetRepository;
        this.expenseRepository = expenseRepository;
    }

    public Budget setBudget(Budget budget) {
        return budgetRepository.save(budget);
    }

    
}
