package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    /**
     * Handle GET requests for the login page.
     * @return the name of the Thymeleaf template to render the login view.
     */
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";  // This corresponds to src/main/resources/templates/login.html
    }

    /**
     * Handle GET requests for the registration page.
     * @return the name of the Thymeleaf template to render the registration view.
     */
    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";  // This corresponds to src/main/resources/templates/register.html
    }
}
