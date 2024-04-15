package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.dto.IncomeSourceDTO;
import com.example.model.IncomeSource;
import com.example.service.IncomeSourceService;

import jakarta.validation.Valid;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/income-sources")
public class IncomeSourceController {

    private final IncomeSourceService incomeSourceService;

    @Autowired
    public IncomeSourceController(IncomeSourceService incomeSourceService) {
        this.incomeSourceService = incomeSourceService;
    }

    @GetMapping("/user/{userId}")
    public String showIncomeSourcesForUser(@PathVariable("userId") Long userId, Model model) {
        List<IncomeSource> incomeSources = incomeSourceService.getAllIncomeSourcesByUserId(userId);
        model.addAttribute("incomeSources", incomeSources);
        return "incomeSources";
    }
  

    @PostMapping("/add")
@ResponseBody
public ResponseEntity<?> addIncomeSource(@Valid @RequestBody IncomeSourceDTO incomeSourceDTO, BindingResult result) {
    if (result.hasErrors()) {
        return ResponseEntity.badRequest().body(result.getAllErrors());
    }

    try {
        System.out.println("Received userId: " + incomeSourceDTO.getUserId());  // Debug log
        IncomeSource incomeSource = new IncomeSource();
        incomeSource.setUserId(incomeSourceDTO.getUserId());
        incomeSource.setSourceName(incomeSourceDTO.getSourceName());
        incomeSource.setAnnualIncome(BigDecimal.valueOf(incomeSourceDTO.getAnnualIncome()));
        incomeSourceService.addIncomeSource(incomeSource);
        return ResponseEntity.ok(incomeSource);  // Return the added income source object as JSON
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add income source");
    }
}

@DeleteMapping("/delete/{incomeSourceId}")
@ResponseBody
public ResponseEntity<?> deleteIncomeSource(@PathVariable("incomeSourceId") Long incomeSourceId) {
    try {
        incomeSourceService.deleteIncomeSource(incomeSourceId);
        return ResponseEntity.ok().body("Income source deleted successfully");
    } catch (Exception e) {
        // Log the exception details as well for better debugging
        System.out.println("Error deleting income source: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete income source");
    }
}
}
