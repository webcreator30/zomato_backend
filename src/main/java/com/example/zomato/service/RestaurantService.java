package com.example.zomato.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.zomato.repository.RestaurantRepo;
import com.example.zomato.repository.UserAddressRepo;

import com.example.zomato.entity.Restaurant;
import com.example.zomato.entity.UserAddress;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepo restaurantRepo;

    @Autowired
    private UserAddressRepo userAddressRepo;

    public List<Restaurant> getNearByRestaurants(Long userAddressId, Double latitude, Double longitude) {
        UserAddress address = userAddressRepo.findById(userAddressId)
                .orElseThrow(() -> new RuntimeException("User Address Not Found !" + (userAddressId)));

        address.setLatitude(latitude);
        address.setLongitude(longitude);
        userAddressRepo.save(address);

        return restaurantRepo.findRestaurantsNearby(latitude, longitude, 15.0);
    }

}
