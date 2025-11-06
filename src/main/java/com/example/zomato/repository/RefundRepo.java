package com.example.zomato.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.zomato.entity.Refund;
import java.util.List;


public interface RefundRepo extends JpaRepository <Refund , Long>{
    Optional<Refund> findByRazorpayRefundId(String razorpayRefundId);    
}
