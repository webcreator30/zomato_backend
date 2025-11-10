package com.example.zomato.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.zomato.entity.Restaurant;
import org.springframework.data.repository.query.Param;

@Repository
public interface RestaurantRepo extends JpaRepository<Restaurant, Long> {
        @Query(value = """
                        SELECT *,
                               (6371 * acos(
                                    cos(radians(:userLat)) * cos(radians(latitude)) *
                                    cos(radians(longitude) - radians(:userLng)) +
                                    sin(radians(:userLat)) * sin(radians(latitude))
                               )) AS distance
                            FROM restaurant
                            WHERE latitude BETWEEN :minLat AND :maxLat
                            AND longitude BETWEEN :minLng AND :maxLng
                            HAVING distance <= :radius
                            ORDER BY distance
                            """, nativeQuery = true)
        List<Restaurant> findRestaurantsNearby(
                        @Param("userLat") double userLat,
                        @Param("userLng") double userLng,
                        @Param("radius") double radius,
                        @Param("minLat") double minLat,
                        @Param("maxLat") double maxLat,
                        @Param("minLng") double minLng,
                        @Param("maxLng") double maxLng);

}
