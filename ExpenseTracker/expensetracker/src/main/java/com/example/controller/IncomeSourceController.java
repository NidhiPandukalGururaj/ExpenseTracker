package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.model.IncomeSource;
import com.example.service.IncomeSourceService;
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

@PostMapping("/user/{userId}")
public String addIncomeSource(@ModelAttribute("incomeSource") IncomeSource incomeSource, @RequestParam("userId") Long userId) {
    System.out.println("Received userId: " + userId);  // Debug log
    incomeSource.setUserId(userId);
    incomeSourceService.addIncomeSource(incomeSource);
    return "incomeSources";
}

    @GetMapping("/delete/{incomeSourceId}")
    public String deleteIncomeSource(@PathVariable("incomeSourceId") Long incomeSourceId) {
        incomeSourceService.deleteIncomeSource(incomeSourceId);
        return "redirect:/income-sources";
    }
}
