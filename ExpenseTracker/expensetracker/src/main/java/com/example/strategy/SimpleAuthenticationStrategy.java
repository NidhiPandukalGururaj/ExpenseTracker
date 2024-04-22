package com.example.strategy;

import com.example.model.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("simpleAuthenticationStrategy")
public class SimpleAuthenticationStrategy implements AuthenticationStrategy {
    @Override
    public boolean authenticate(User user, String password) {
        return user.getPassword().equals(password);
    }
}