package com.example.demo.controller;

import com.example.demo.db.model.User;
import com.example.demo.service.UserService;
import com.example.demo.util.vo.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NewController {

    @Autowired
    UserService userService;
    @GetMapping("/test")
    public ResponseEntity<ApiResponse<List<User>>> welcome() {
        ApiResponse<List<User>> response = new ApiResponse<>(HttpStatus.OK.value(), true, userService.getAllUsers());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
