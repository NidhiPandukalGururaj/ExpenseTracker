package com.example.notification;

import com.example.repository.ExpenseRepository;
import com.example.repository.IncomeSourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import com.example.model.*;

@Component
public class RecurrentBillNotification implements NotificationStrategy {

    @Override
    public String generateNotification(Long userId, ExpenseRepository expenseRepository,
            IncomeSourceRepository incomeRepository) {
        StringBuilder notificationBuilder = new StringBuilder();

        boolean rentPaid = expenseRepository.existsByExpenseCategoryAndExpenseDateBefore(ExpenseCategory.RENT,
                new Date());
        if (!rentPaid) {
            notificationBuilder.append("Rent Of This Month Not Paid Yet. ");
        }

        boolean emiPaid = expenseRepository.existsByExpenseCategoryAndExpenseDateBefore(ExpenseCategory.EMI,
                new Date());
        if (!emiPaid) {
            notificationBuilder.append("EMI Of This Month Not Paid Yet. ");
        }

        // Explicitly check for Utility category
        boolean utilityPaid = expenseRepository.existsByExpenseCategoryAndExpenseDateBefore(ExpenseCategory.UTILITY,
                new Date());
        if (!utilityPaid) {
            notificationBuilder.append("Utility Of This Month Not Paid Yet. ");
        } else {
            notificationBuilder.append("Utility category is up to date. ");
        }

        return notificationBuilder.toString().trim();
    }
}
