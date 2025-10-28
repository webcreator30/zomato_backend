package com.example.zomato.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.zomato.entity.Menu;

public interface MenuRepo extends JpaRepository<Menu , Long>{
    
}
