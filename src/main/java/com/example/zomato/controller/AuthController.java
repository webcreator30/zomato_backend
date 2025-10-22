package com.example.zomato.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.zomato.service.AuthService;


@RestController
public class AuthController {
    

@Autowired
private AuthService authService;


    @PostMapping("/send-otp")
    public String sendOTP(@RequestBody Map<String, String> body){
        String mobile = body.get("mobile");
        return authService.sendOtp(mobile);
    }

@PostMapping("/verify-otp")
public String verifyOTP(@RequestParam String mobile , @RequestParam String otp){
    return authService.verifyOTP(mobile, otp);
}

}
