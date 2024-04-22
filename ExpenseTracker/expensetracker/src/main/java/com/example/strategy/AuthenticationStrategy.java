package com.example.strategy;
import com.example.model.User;

public interface AuthenticationStrategy {
    boolean authenticate(User user, String password);
}