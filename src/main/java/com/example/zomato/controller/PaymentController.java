package com.example.zomato.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.zomato.service.PaymentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class PaymentController {
    @Autowired
    private PaymentService paymentService;


    @PostMapping("/verify-payment")
    public void verifyPayment(@RequestBody Map<String , String> req) {
        paymentService.verifyPaymentSignature(req);
    }
    
}
