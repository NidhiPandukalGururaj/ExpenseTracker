package com.example.notification;

import com.example.repository.ExpenseRepository;
import com.example.repository.IncomeSourceRepository;

public interface NotificationStrategy {
    String generateNotification(Long userId, ExpenseRepository expenseRepository,
            IncomeSourceRepository incomeRepository);
}
