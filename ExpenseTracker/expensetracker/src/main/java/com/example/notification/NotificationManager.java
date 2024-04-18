package com.example.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

import com.example.repository.*;

@Component
public class NotificationManager {
    private NotificationStrategy notificationStrategy;

    public void setNotificationStrategy(NotificationStrategy notificationStrategy) {
        this.notificationStrategy = notificationStrategy;
    }

    public String generateNotification(Long userId, ExpenseRepository expenseRepository,
            IncomeSourceRepository incomeRepository) {
        return notificationStrategy.generateNotification(userId, expenseRepository, incomeRepository);
    }
}
