package com.example.zomato.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@Configuration
public class RazorpayConfig {
 
    
    @Value("${RAZORPAY_API_KEY}")
    private String apiKey;
    
    @Value("${RAZORPAY_KEY_SECRET}")
    private String keySecret;

    // RazorpayClient client is the official Razorpay SDK class that enables
    // your app to interact with Razorpay APIs (e.g., create orders, capture payments).
    @Bean
    public RazorpayClient razorpayClient() throws RazorpayException{
        return new RazorpayClient(apiKey, keySecret);
    }
}
