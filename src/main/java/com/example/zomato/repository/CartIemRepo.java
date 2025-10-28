package com.example.zomato.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.zomato.entity.CartItem;

public interface CartIemRepo  extends JpaRepository<CartItem , Long>{
    
}
