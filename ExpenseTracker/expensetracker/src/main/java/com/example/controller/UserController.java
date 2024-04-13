package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.dto.UserLoginDto;
import com.example.dto.UserRegistrationDto;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<User> createuser(@RequestBody User user) {
        User newUser = userService.createUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/action/register")
    public ResponseEntity<?> registerUser(@Valid @ModelAttribute UserRegistrationDto registrationDto, BindingResult bindingResult) {
        logger.info("Received registration data: {}", registrationDto);

        if (bindingResult.hasErrors()) {
            logger.error("Validation errors: {}", bindingResult.getAllErrors());
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        User user = convertDtoToEntity(registrationDto);
        userService.createUser(user);
        logger.info("User created successfully: {}", user);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    private User convertDtoToEntity(UserRegistrationDto dto) {
        User user = new User();
        user.setUserName(dto.getUserName());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());  // Consider encrypting the password here
        user.setGender(dto.getGender());

        logger.info("Converted DTO to User entity: {}", user);

        return user;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginDto loginDto) {
        logger.info("Received login data: {}", loginDto);

        // Check if either username or email is provided
        if (loginDto.getUsername() == null && loginDto.getEmail() == null) {
            logger.error("Neither username nor email provided.");
            return ResponseEntity.badRequest().body("Either username or email must be provided.");
        }
        User user = null;
        if (loginDto.getUsername() != null) {
            user = userService.getUserByUsername(loginDto.getUsername());
        } else if (loginDto.getEmail() != null) {
            user = userService.getUserByEmail(loginDto.getEmail());
        }

        if (user == null || !user.getPassword().equals(loginDto.getPassword())) {
            logger.error("Invalid username/email or password.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username/email or password.");
        }

        logger.info("User logged in successfully: {}", user);
        return ResponseEntity.ok(user);
    }


    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User userDetails) {
        User updatedUser = userService.updateUser(id, userDetails);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
