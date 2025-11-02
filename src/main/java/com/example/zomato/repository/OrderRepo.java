package com.example.zomato.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.zomato.entity.Order;

public interface OrderRepo extends JpaRepository<Order , Long> {
    
}
