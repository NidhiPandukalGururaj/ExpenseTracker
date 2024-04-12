package com.example.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class NotificationManager {

    @Autowired
    private List<NotificationObserver> observers;

    private NotificationStrategy strategy;

    public void setStrategy(NotificationStrategy strategy) {
        this.strategy = strategy;
    }

    public void checkForNotifications() {
        // Logic to check for overdue bills and budget limits
        String message = ""; // Prepare the message based on checks

        // Notify observers
        for (NotificationObserver observer : observers) {
            observer.notify(message);
        }
        strategy.sendNotification(message);
    }
}
