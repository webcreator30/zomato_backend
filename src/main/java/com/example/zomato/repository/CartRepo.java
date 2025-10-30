package com.example.zomato.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.zomato.entity.Cart;

public interface CartRepo extends JpaRepository<Cart , Long> {
    Optional<Cart> findByUserId(Long userId);
}
