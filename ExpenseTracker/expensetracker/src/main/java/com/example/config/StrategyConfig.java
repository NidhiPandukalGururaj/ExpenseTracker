package com.example.config;

import com.example.strategy.AuthenticationStrategy;
import com.example.strategy.SimpleAuthenticationStrategy;
import com.example.strategy.HashedAuthenticationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Qualifier;

@Configuration
public class StrategyConfig {

    @Bean
    @Qualifier("simpleAuthenticationStrategy")
    public AuthenticationStrategy simpleAuthenticationStrategy() {
        return new SimpleAuthenticationStrategy();
    }

    @Bean
    @Qualifier("hashedAuthenticationStrategy")
    public AuthenticationStrategy hashedAuthenticationStrategy() {
        return new HashedAuthenticationStrategy();
    }
}
