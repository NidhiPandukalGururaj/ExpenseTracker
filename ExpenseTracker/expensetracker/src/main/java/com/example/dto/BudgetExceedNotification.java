package com.example.dto;

public class BudgetExceedNotification {
    private Long userId;
    private String category;
    private double amountExceeded;

    public BudgetExceedNotification() {
    }

    public BudgetExceedNotification(Long userId, String category, double amountExceeded) {
        this.userId = userId;
        this.category = category;
        this.amountExceeded = amountExceeded;
    }

    // Getters
    public Long getUserId() {
        return userId;
    }

    public String getCategory() {
        return category;
    }

    public double getAmountExceeded() {
        return amountExceeded;
    }

    // Setters
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAmountExceeded(double amountExceeded) {
        this.amountExceeded = amountExceeded;
    }

    // toString method for debugging
    @Override
    public String toString() {
        return "BudgetExceedNotification{" +
                "userId=" + userId +
                ", category='" + category + '\'' +
                ", amountExceeded=" + amountExceeded +
                '}';
    }
}
