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

    @GetMapping("/add")
    public String showAddIncomeSourceForm(Model model) {
        model.addAttribute("incomeSource", new IncomeSource());
        return "addIncomeSource";
    }

    @PostMapping("/add")
    public String addIncomeSource(@ModelAttribute("incomeSource") IncomeSource incomeSource) {
        incomeSourceService.addIncomeSource(incomeSource);
        return "redirect:/income-sources/user/" + incomeSource.getUserId();
    }

    @GetMapping("/delete/{incomeSourceId}")
    public String deleteIncomeSource(@PathVariable("incomeSourceId") Long incomeSourceId) {
        incomeSourceService.deleteIncomeSource(incomeSourceId);
        return "redirect:/income-sources";
    }
}
