package com.example.zomato.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.example.zomato.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class OrderController {
    

    @Autowired
    private OrderService orderService;

    @PostMapping("/placeOrder")
    public ResponseEntity<String> placeOrder(@RequestBody Map<String , String> req) {
        Long userId = Long.parseLong(req.get("userId"));
        String res = orderService.placeOrder(userId);
        return ResponseEntity.ok(res);
    }
    
}
