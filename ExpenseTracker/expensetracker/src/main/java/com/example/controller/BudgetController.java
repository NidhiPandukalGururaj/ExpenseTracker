package com.example.controller;

import com.example.model.Budget;
import com.example.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/budgets")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Budget>> getAllBudgetsByUserId(@PathVariable Long userId) {
        List<Budget> budgets = budgetService.findAllBudgetsByUserId(userId);
        return ResponseEntity.ok().body(budgets);
    }

    @PutMapping("/{budgetId}")
    public ResponseEntity<Budget> updateBudget(@PathVariable Long budgetId, @RequestBody Budget budgetDetails) {
        Budget updatedBudget = budgetService.updateBudget(budgetId, budgetDetails);
        return ResponseEntity.ok().body(updatedBudget);
    }

    @PostMapping("/")
    public ResponseEntity<Budget> createBudget(@RequestBody Budget budget) {
        Budget newBudget = budgetService.createBudget(budget);
        return ResponseEntity.ok().body(newBudget);
    }
}
