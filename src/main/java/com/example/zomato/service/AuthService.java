package com.example.zomato.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.zomato.repository.UserRepo;

@Service
public class AuthService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private OtpService otpService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private SmsService smsService;

    public String sendOtp(String mobile) {
        System.out.println("userRepository = " + userRepo);
        System.out.println("Chalaaaaaaaaaaaaa");
        var user = userRepo.findByMobile(mobile);
        System.out.println("Found user: " + user);

        if (user.isEmpty()) {
            return "❌ Mobile number not registered.";

        }
        String otp = otpService.generateOTP(mobile);
        smsService.sendOtpSms(mobile, otp);
        return "✅ OTP sent to " + mobile;

    }

    public String verifyOTP(String mobile, String otp) {
        Boolean verify = otpService.verifyOTP(mobile, otp);
        if (!verify) {
            return "❌ Invalid or expired OTP";
        }
        return jwtService.generateToken(mobile);
    }
}
