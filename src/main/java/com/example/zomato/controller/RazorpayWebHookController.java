package com.example.zomato.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.example.zomato.service.PaymentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
public class RazorpayWebHookController {
    
    
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/webhook")
    public String handleWebhook(@RequestBody String payload , @RequestHeader("X-Razorpay-Signature") String signature) {
        paymentService.handleWebhook(payload, signature);
        return "ok";
    }
    
}
