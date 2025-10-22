package com.example.zomato.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.zomato.entity.User;
import com.example.zomato.entity.UserAddress;
import com.example.zomato.service.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> singup(@RequestBody User user) {
        String result = userService.userSignup(user);

        if (result.toLowerCase().contains("failed")) {
            // Signup failed → return 400 Bad Request
            return ResponseEntity.status(400).body(result);
        }

        // Signup successful → return 201 Created
        return ResponseEntity.status(201).body(result);
    }

    @PostMapping("/add-address")
    public ResponseEntity<String> addAddress(@RequestBody User userWithNewAddress) {
        String result = userService.addNewAddress(userWithNewAddress);

        if (result.toLowerCase().contains("failed")) {
            // Signup failed → return 400 Bad Request
            return ResponseEntity.status(400).body(result);
        }

        // Signup successful → return 201 Created
        return ResponseEntity.status(201).body(result);
    }

}
