package com.example.zomato.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SmsService {

    @Value("${fast2sms.api.key}")
    private String apiKey;

    public void sendOtpSms(String mobile , String otp){
     String message = "Your OTP for login is " + otp + ". It is valid for 10 minutes.";
     
    String url = "https://www.fast2sms.com/dev/bulkV2" +
                    "?authorization=" + apiKey +
                    "&route=q" +
                    "&message=" + message.replace(" ", "%20") + // encode spaces
                    "&flash=0" +
                    "&numbers=" + mobile;

     

     RestTemplate restTemplate = new RestTemplate();
     restTemplate.getForObject(url , String.class);
    //  System.out.println("Mock OTP for " + mobile + ": " + otp);
    }
}

