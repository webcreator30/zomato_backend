package com.example.zomato.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.zomato.entity.User;
import com.example.zomato.entity.UserAddress;
import com.example.zomato.repository.UserAddressRepo;
import com.example.zomato.repository.UserRepo;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserAddressRepo userAddressRepo;

    public String userSignup(User user) {
        try {
            if (user.getAddresses() != null) {
                for (UserAddress addr : user.getAddresses()) {
                    addr.setUser(user);
                }
            }
            User savedUser = userRepo.save(user);
            return "User signup successful with ID: " + savedUser.getId();
        } catch (Exception e) {
            return "Signup failed !! " + e.getMessage();
        }

    }

    public String addNewAddress(User userWithNewAddress) {
        try {
            User existingUser = userRepo.findByEmail(userWithNewAddress.getEmail())
                    .orElseThrow(() -> new RuntimeException("User Not Found"));
            if (userWithNewAddress.getAddresses() != null) {
                    for(UserAddress addr: userWithNewAddress.getAddresses()){
                        addr.setUser(existingUser);
                    }
                existingUser.getAddresses().addAll(userWithNewAddress.getAddresses());
            }
            userRepo.save(existingUser);

            return "Address added successfully for User " + existingUser.getName();
        } catch (Exception e) {
            return "Error Adding Address for user " + e.getMessage();
        }
    }
}
