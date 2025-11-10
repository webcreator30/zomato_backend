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

    public List<Restaurant> getNearByRestaurants(Long userAddressId, Double latitude, Double longitude,
            Double radiusKm) {
        UserAddress address = userAddressRepo.findById(userAddressId)
                .orElseThrow(() -> new RuntimeException("User Address Not Found !" + (userAddressId)));

        // Calculate how far radiuskm looks in lat/long degrees -> in terms of square
        // latDiff = how far north/south you go for 5 km 
        // lngDiff = how far east/west you go for 5 km (depends on latitude)
        Double latDiff = radiusKm / 111.0; // i degree lat = 111 km
        Double lngDiff = radiusKm / (111.0 * Math.cos(Math.toRadians(latitude)));

        Double minLat = latitude - latDiff;
        Double maxLat = latitude + latDiff;
        Double minLng = longitude - lngDiff;
        Double maxLng = longitude + lngDiff;

        address.setLatitude(latitude);
        address.setLongitude(longitude);
        userAddressRepo.save(address);
        // System.out.println("Latitude range: " + minLat + " to " + maxLat);
        // System.out.println("Longitude range: " + minLng + " to " + maxLng);

        return restaurantRepo.findRestaurantsNearby(latitude, longitude, radiusKm, minLat, maxLat, minLng, maxLng);
    }

}
