package com.example.repository;

import com.example.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.model.ExpenseCategory;

import java.util.Date;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByExpenseDateBetween(Date startDate, Date endDate);

    List<Expense> findByUserIdAndExpenseDateBetween(Long userId, Date startDate, Date endDate);

    boolean existsByExpenseCategoryAndExpenseDateBefore(ExpenseCategory category, Date date);
}
