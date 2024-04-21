package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.model.User;

@Controller
public class AuthController {

    /**
     * Handle GET requests for the login page.
     * @param model Model object to pass data to the view.
     * @return the name of the Thymeleaf template to render the login view.
     */
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        // Optionally add attributes to the model if needed for the login form
        return "login";  // This corresponds to src/main/resources/templates/login.html
    }

    /**
     * Handle GET requests for the registration page.
     * @param model Model object to pass data to the view.
     * @return the name of the Thymeleaf template to render the registration view.
     */
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // Add a new user object to the model for form binding
        model.addAttribute("user", new User());  // Assuming you have a User class that matches the form fields
        return "register";  // This corresponds to src/main/resources/templates/register.html
    }

     /**
     * Handle GET requests for the profile page.
     * @param model Model object to pass data to the view.
     * @return the name of the Thymeleaf template to render the profile view.
     */
    @GetMapping("/profile")
    public String showProfilePage(Model model) {
        // Add logic to retrieve profile data and add it to the model if needed
        return "profile";  // This corresponds to src/main/resources/templates/profile.html
    }

     /**
     * Handle GET requests for the profile page.
     * @param model Model object to pass data to the view.
     * @return the name of the Thymeleaf template to render the profile view.
     */
    @GetMapping("/report")
    public String showreportPage(Model model) {
        // Add logic to retrieve profile data and add it to the model if needed
        return "report";  // This corresponds to src/main/resources/templates/profile.html
    }
     /**
     * Handle GET requests for the profile page.
     * @param model Model object to pass data to the view.
     * @return the name of the Thymeleaf template to render the profile view.
     */
    @GetMapping("/setbudget")
    public String setBudget(Model model) {
        // Add logic to retrieve profile data and add it to the model if needed
        return "budget";  // This corresponds to src/main/resources/templates/profile.html
    }
}
