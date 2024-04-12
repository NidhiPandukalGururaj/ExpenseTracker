package com.example.model;

import jakarta.persistence.*;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "expenses")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "expense_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expenseDate;

    @Column(name = "expense_amount")
    private double expenseAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "expense_category")
    private ExpenseCategory expenseCategory;

    @Enumerated(EnumType.STRING)
    @Column(name = "expense_transaction_type")
    private ExpenseTransactionType expenseTransactionType;

    @Lob
    @Column(name = "expense_receipt")
    private byte[] expenseReceipt;

    public Expense() {
    }

    public Expense(Long userId, Date expenseDate, double expenseAmount, ExpenseCategory expenseCategory,
            ExpenseTransactionType expenseTransactionType, byte[] expenseReceipt) {
        this.userId = userId;
        this.expenseDate = expenseDate;
        this.expenseAmount = expenseAmount;
        this.expenseCategory = expenseCategory;
        this.expenseTransactionType = expenseTransactionType;
        this.expenseReceipt = expenseReceipt;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(Date expenseDate) {
        this.expenseDate = expenseDate;
    }

    public double getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(double expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public ExpenseCategory getExpenseCategory() {
        return expenseCategory;
    }

    public void setExpenseCategory(ExpenseCategory expenseCategory) {
        this.expenseCategory = expenseCategory;
    }

    public ExpenseTransactionType getExpenseTransactionType() {
        return expenseTransactionType;
    }

    public void setExpenseTransactionType(ExpenseTransactionType expenseTransactionType) {
        this.expenseTransactionType = expenseTransactionType;
    }

    public byte[] getExpenseReceipt() {
        return expenseReceipt;
    }

    public void setExpenseReceipt(byte[] expenseReceipt) {
        this.expenseReceipt = expenseReceipt;
    }
}
