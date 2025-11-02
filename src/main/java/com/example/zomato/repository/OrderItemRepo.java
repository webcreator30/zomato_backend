package com.example.zomato.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.zomato.entity.OrderItem;

public interface OrderItemRepo extends JpaRepository<OrderItem , Long> {
    
}
