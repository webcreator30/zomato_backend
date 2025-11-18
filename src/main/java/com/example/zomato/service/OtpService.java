package com.example.zomato.service;

import java.time.Duration;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class OtpService {

    @Autowired
    private StringRedisTemplate redisTemplate;


    // Generate and Save OTP in Reddis 
    public String generateOTP(String mobile) {
        String otp = String.valueOf(10000 + new Random().nextInt(90000));
        redisTemplate.opsForValue().set("OTP_" + mobile, otp, Duration.ofMinutes(10));       
        return otp;
    }

    // Verify OTP with saved OTP in Reddis and delete OTP if matched so that it can be used once 
    public boolean verifyOTP(String mobile, String otp) {
        String savedOtp = redisTemplate.opsForValue().get("OTP_" + mobile);
        if (savedOtp != null & savedOtp.equals(otp)) {
            redisTemplate.delete("OTP_" + mobile);
            return true;
        }
        return false;
    }

}
