package com.example.notification;

import com.example.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import com.example.model.*;

@Component
public class RecurrentBillObserver implements NotificationObserver {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private NotificationStrategy notificationManager;

    @Override
    public void notify(String message) {
        // Logic to notify about overdue recurrent bills
        boolean rentPaid = expenseRepository.existsByExpenseCategoryAndExpenseDateBefore(ExpenseCategory.RENT,
                java.sql.Date.valueOf(LocalDate.now().withDayOfMonth(10)));
        boolean emiPaid = expenseRepository.existsByExpenseCategoryAndExpenseDateBefore(ExpenseCategory.EMI,
                java.sql.Date.valueOf(LocalDate.now().withDayOfMonth(10)));
        boolean utilityPaid = expenseRepository.existsByExpenseCategoryAndExpenseDateBefore(ExpenseCategory.UTILITY,
                java.sql.Date.valueOf(LocalDate.now().withDayOfMonth(10)));

        if (!rentPaid) {
            notificationManager.sendNotification("Rent of this month is not paid yet");
        }
        if (!emiPaid) {
            notificationManager.sendNotification("EMI of this month is not paid yet");
        }
        if (!utilityPaid) {
            notificationManager.sendNotification("Utility of this month is not paid yet.");
        }
    }
}
