package com.example.zomato.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.zomato.service.RestaurantService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.zomato.entity.Restaurant;

@RestController
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping("/restaurants/nearBy")
    public ResponseEntity<List<Restaurant>> restaurantsNearby(@RequestBody Map<String, Object> request) {
        Long userAddressId = Long.valueOf(request.get("userAddressId").toString());
        Double latitude = Double.valueOf(request.get("latitude").toString());
        Double longitude = Double.valueOf(request.get("longitude").toString());
        Double radius = Double.valueOf(request.get("radius").toString());

        

        List<Restaurant> restaurants = restaurantService.getNearByRestaurants(userAddressId, latitude, longitude,
                radius);
        return ResponseEntity.ok(restaurants);
    }

}
