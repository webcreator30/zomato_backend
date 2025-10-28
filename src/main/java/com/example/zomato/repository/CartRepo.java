package com.example.zomato.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.zomato.entity.Cart;

public interface CartRepo extends JpaRepository<Cart , Long> {
    
}
