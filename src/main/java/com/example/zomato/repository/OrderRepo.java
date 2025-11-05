package com.example.zomato.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.zomato.entity.Order;

public interface OrderRepo extends JpaRepository<Order , Long> {

    Optional<Order> findByRazorpayOrderId(String razorpayOrderId);
    
}
