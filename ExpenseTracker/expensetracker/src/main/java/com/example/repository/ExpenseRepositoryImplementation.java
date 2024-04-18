package com.example.repository;

import com.example.model.Expense;
import com.example.model.ExpenseCategory;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@Repository
public class ExpenseRepositoryImplementation {

    @PersistenceContext
    private EntityManager entityManager;

    public boolean existsByExpenseCategoryAndExpenseDateBefore(ExpenseCategory category, Date date) {
        long count = entityManager.createQuery(
                "SELECT COUNT(e) FROM Expense e WHERE e.expenseCategory = :category AND e.expenseDate < :date",
                Long.class)
                .setParameter("category", category)
                .setParameter("date", date)
                .getSingleResult();
        return count > 0;
    }

    public List<Expense> findByUserIdAndExpenseDateBetween(Long userId, Date startDate, Date endDate) {
        return entityManager.createQuery(
                "SELECT e FROM Expense e WHERE e.userId = :userId AND e.expenseDate BETWEEN :startDate AND :endDate",
                Expense.class)
                .setParameter("userId", userId)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }
}
