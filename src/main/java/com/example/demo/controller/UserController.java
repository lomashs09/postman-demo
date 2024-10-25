package com.example.demo.controller;

import com.example.demo.db.model.User;
import com.example.demo.service.UserService;
import com.example.demo.util.Constant;
import com.example.demo.util.vo.ApiResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constant.API_VERSION + "/users")
@Validated
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<User>> createUser(@Valid @RequestBody User user) {
        logger.info("Received request to create user: {}", user);
        User createdUser = userService.createUser(user);
        ApiResponse<User> response = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                true,
                createdUser
        );

        logger.info("User created: {}", createdUser);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        logger.info("Received request to fetch all users");
        List<User> users = userService.getAllUsers();
        ApiResponse<List<User>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                true,
                users
        );

        logger.info("Fetched {} users", users.size());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable Long id) {
        logger.info("Received request to fetch user by id: {}", id);
        User user = userService.getUserById(id)
                .orElseThrow(() -> {
                    logger.error("User not found with id: {}", id);
                    return new RuntimeException("User not found with id " + id);
                });

        ApiResponse<User> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                true,
                user
        );

        logger.info("Fetched user: {}", user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable Long id, @Valid @RequestBody User userDetails) {
        logger.info("Received request to update user with id: {}", id);
        User updatedUser = userService.updateUser(id, userDetails);
        ApiResponse<User> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                true,
                updatedUser
        );

        logger.info("User updated: {}", updatedUser);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        logger.info("Received request to delete user with id: {}", id);
        userService.deleteUser(id);
        ApiResponse<Void> response = new ApiResponse<>(
                HttpStatus.NO_CONTENT.value(),
                true,
                null
        );

        logger.info("User deleted with id: {}", id);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}