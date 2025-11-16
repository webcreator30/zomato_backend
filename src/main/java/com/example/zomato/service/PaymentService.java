package com.example.zomato.service;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.zomato.entity.Order;
import com.example.zomato.enums.DeliveryStatus;
import com.example.zomato.enums.PaymentStatus;
import com.example.zomato.repository.OrderRepo;
import com.razorpay.RazorpayClient;

import jakarta.persistence.Converts;

@Service
public class PaymentService {
    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private RazorpayClient razorpayClient;

    @Value("${RAZORPAY_API_KEY}")
    private String razorpayKeyId;

    @Value("${RAZORPAY_KEY_SECRET}")
    private String razorpayKeySecret;


    @Async
    public CompletableFuture<Map<String, Object>> createRazorpayOrder(Long orderId) {
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

            return CompletableFuture.completedFuture(response);
        } catch (Exception e) {
            throw new RuntimeException("Unable to create Razorpay order: " + e.getMessage(), e);
        }

    }

    // Verify Payment Signature ->

    public void verifyPaymentSignature(Map<String, String> payload) {
        String razorpayOrderId = payload.get("razorpayOrderId");
        String razorpayPaymentId = payload.get("razorpayPaymentId");
        String razorpaySignature = payload.get("razorpaySignature");
        String orderId = payload.get("orderId");

        // Compute expected signature
        String data = razorpayOrderId + "|" + razorpayPaymentId;
        String expectedSignature = hmacSha256(data, razorpayKeySecret);

        // Check for valid signature
        if (!expectedSignature.equals(razorpaySignature)) {
            Order order = orderRepo.findById(Long.valueOf(orderId)).orElseThrow();
            order.setPaymentStatus(PaymentStatus.FAILED);
            orderRepo.save(order);
            throw new RuntimeException("Invalid signature. Payment failed.");
        }

        // Signature valid — mark order as paid
        Order order = orderRepo.findByRazorpayOrderId(razorpayOrderId)
                .orElseGet(() -> orderRepo.findById(Long.valueOf(orderId)).orElseThrow());
        order.setPaymentStatus(PaymentStatus.PAID);
        
        order.setDeliveryStatus(DeliveryStatus.PREPARING);
        order.setRazorpayPaymentId(razorpayPaymentId);
        orderRepo.save(order);
    }

    private String hmacSha256(String data, String secret) {

        try {
            // Mac = Message Authentication Code — a Java class that handles secure hash
            // functions.
            // "HmacSHA256" tells it to use the HMAC with SHA-256 algorithm,
            // which is what Razorpay also uses

            Mac mac = Mac.getInstance("HmacSHA256");

            // Converts your secret (your Razorpay key_secret) into bytes and wrap it in
            // SecretKeySpec Object so that it can be used by mac instance

            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(secretKey);
            byte[] rawHmac = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));

            // Converts the hash bytes into a readable hexadecimal string.
            return HexFormat.of().formatHex(rawHmac);

        } catch (Exception e) {
            throw new RuntimeException("Unable to calculate HMAC", e);
        }
    }
}
