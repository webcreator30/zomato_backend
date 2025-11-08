package com.example.zomato.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.zomato.entity.Refund;
import com.example.zomato.service.RefundService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class RefundController {

    @Autowired
    private RefundService refundService;

    @PostMapping("/initiate-refund")
    public ResponseEntity<Refund> startRefundProcess(@RequestBody Map<String, String> req) {
        Long userId = Long.valueOf(req.get("orderId"));
        String reason = req.get("reason");
        Refund res = refundService.initiaRefund(userId, reason);
        return ResponseEntity.ok(res);

    }

}
