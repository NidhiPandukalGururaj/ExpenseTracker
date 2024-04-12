package com.example.notification;

import org.springframework.stereotype.Component;

@Component
public class PopupNotificationStrategy implements NotificationStrategy {
    @Override
    public void sendNotification(String message) {
        // Implement pop-up notification strategy
        String javascriptCode = "alert('" + message + "');";
        System.out.println(javascriptCode); // This will print the JavaScript code to the console (you can replace with appropriate logging)
    }
}
