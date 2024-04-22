package com.example.strategy;

import com.example.model.User;
import com.example.util.PasswordHashUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("hashedAuthenticationStrategy")
public class HashedAuthenticationStrategy implements AuthenticationStrategy {
    @Override
    public boolean authenticate(User user, String password) {
        String hashedPassword = PasswordHashUtil.hashPassword(password + user.getSalt());
        return user.getPassword().equals(hashedPassword);
    }
}