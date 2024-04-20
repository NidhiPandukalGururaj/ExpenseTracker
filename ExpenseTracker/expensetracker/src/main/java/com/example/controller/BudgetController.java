package com.example.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.model.Budget;
import com.example.service.BudgetService;

@RestController
@RequestMapping("/budgets")
public class BudgetController {

    private final BudgetService budgetService;

    @Autowired
    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @PostMapping("/set")
    public Budget setBudget(@RequestBody Budget budget) {
        return budgetService.setBudget(budget);
    }

}
