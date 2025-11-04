package com.example.zomato.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.zomato.entity.Order;
import com.example.zomato.repository.OrderRepo;
import com.razorpay.RazorpayClient;

@Service
public class PaymentService {
    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private RazorpayClient razorpayClient;

    @Value("${api_key}")
    private String razorpayKeyId;

    @Value("${key_secret}")
    private String razorpayKeySecret;

    public Map<String, Object> createRazorpayOrder(Long orderId) {
        Order order = orderRepo.findById(orderId).orElseThrow(() -> new RuntimeException("Order Not Found"));
        try {
            int amountInPaise = order.getTotalAmount().multiply(new BigDecimal(100)).intValue();

            JSONObject request = new JSONObject();
            request.put("amount", amountInPaise);
            request.put("currency", "INR");

            // This is a unique reference number for your internal tracking.
            request.put("receipt", "order_rcpt_" + order.getId());

            // 1 → means auto-capture
            // 👉 Razorpay will automatically capture the payment right after authorization.
            // So once the user pays successfully, the money is immediately taken.
            request.put("payment_capture", 1);

            // Razorpay Order Creation
            com.razorpay.Order razorpayOrder = razorpayClient.Orders.create(request);

            // save razorpayOrder id on your order for correlation
            order.setRazorpayOrderId(razorpayOrder.get("id"));
            orderRepo.save(order);

            Map<String, Object> response = new HashMap<>();
            response.put("razorpayOrderId", razorpayOrder.get("id"));
            response.put("amount", order.getTotalAmount()); // for UI display
            response.put("amountPaise", amountInPaise);
            response.put("currency", "INR");
            response.put("key", razorpayKeyId);
            response.put("orderId", order.getId());

            return response;

        } catch (Exception e) {
            throw new RuntimeException("Unable to create Razorpay order: " + e.getMessage(), e);
        }

    }
}
