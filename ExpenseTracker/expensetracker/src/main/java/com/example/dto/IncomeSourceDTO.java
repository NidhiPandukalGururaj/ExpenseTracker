package com.example.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class IncomeSourceDTO {
    private Long userId;
    @NotNull(message = "Source name cannot be null")
    private String sourceName;
    @NotNull(message = "Annual income cannot be null")
    @Min(value = 0, message = "Annual income must be non-negative")
    private Double annualIncome;

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public Double getAnnualIncome() {
        return annualIncome;
    }

    public void setAnnualIncome(Double annualIncome) {
        this.annualIncome = annualIncome;
    }
}
