package com.example.zomato.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.zomato.entity.User;
import com.example.zomato.repository.UserRepo;

@Service
// During Securing API Enpoint Spring Boot should know which user is logged in ,
// it check it in db and return user details in this service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String mobile) throws UsernameNotFoundException {
        User user = userRepo.findByMobile(mobile).orElseThrow(() -> new RuntimeException("User Not Found in Database"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getName())
                .password("")
                .authorities("USER")
                .build();
    }

}
