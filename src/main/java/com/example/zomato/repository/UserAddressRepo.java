package com.example.zomato.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.zomato.entity.UserAddress;


@Repository
public interface UserAddressRepo extends JpaRepository<UserAddress , Long>{
    
}
