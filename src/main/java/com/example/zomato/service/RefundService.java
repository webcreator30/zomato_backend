package com.example.zomato.service;

import java.math.BigDecimal;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.zomato.entity.Order;
import com.example.zomato.entity.Refund;
import com.example.zomato.enums.PaymentStatus;
import com.example.zomato.enums.RefundStatus;
import com.example.zomato.repository.OrderRepo;
import com.example.zomato.repository.RefundRepo;
import com.razorpay.RazorpayClient;

@Service
public class RefundService {

    @Autowired
    private RefundRepo refundRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private RazorpayClient razorpayClient;

    public Refund initiaRefund(Long orderId, String reason) {
        // Step 1️⃣ : Fetch order from DB
        Order order = orderRepo.findById(orderId).orElseThrow(() -> new RuntimeException("Order Not Found"));

        // Step 2️⃣ : Validate that refund is allowed
        // Check if payment satus is not PAID than no refund
        if (!PaymentStatus.PAID.equals(order.getPaymentStatus())) {
            throw new IllegalStateException("Order is not paid — cannot initiate refund");
        }
        // Check if order already refunded
        if (refundRepo.findByOrderId(orderId).isPresent()) {
            throw new IllegalStateException("Order Already Refunded Cannot initiate refund");
        }

        try {
            JSONObject refundRequest = new JSONObject();
            refundRequest.put("payment_id", order.getRazorpayPaymentId());
            refundRequest.put("amount", order.getTotalAmount().multiply(new BigDecimal(100)));
            refundRequest.put("speed", "optimum");
            refundRequest.put("notes", new JSONObject().put("reason", reason));

            // Call RazorPay Refund API and POST refundRequest JSON Object
            com.razorpay.Refund razorpayRefund = razorpayClient.Refunds.create(refundRequest);

            // Save refund in DB
            Refund refund = new Refund();
            refund.setOrder(order);
            refund.setRazorpayPaymentId(order.getRazorpayPaymentId());
            refund.setRazorpayRefundId(razorpayRefund.get("id"));
            refund.setAmount_to_refund(order.getTotalAmount());
            refund.setReason(reason);
            refund.setRefundStatus(RefundStatus.INITIATED);

            refundRepo.save(refund);

            order.setRazorpayRefundId(razorpayRefund.get("id"));
            order.setCancelReason(reason);
            order.setPaymentStatus(PaymentStatus.REFUND_INITIATED);
            orderRepo.save(order);

            return refund;

        } catch (Exception e) {
            throw new RuntimeException("Refund failed" + e.getMessage(), e);
        }

    }

}
