package com.example.zomato.entity;

import com.example.zomato.enums.Type; // THIS ONE
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Restaurant {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

  
    private String name;
    

    @Enumerated(EnumType.STRING)
    private Type type;

    private String contactNumber;
    private String rating;
    private String address;

    private Double latitude;
    private Double longitude;
      public Restaurant() {
    }
      public Restaurant(Long id, String name, Type type, String contactNumber, String rating, String address,
            Double latitude, Double longitude) {
        Id = id;
        this.name = name;
        this.type = type;
        this.contactNumber = contactNumber;
        this.rating = rating;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
      }
      public Long getId() {
          return Id;
      }
      public void setId(Long id) {
          Id = id;
      }
      public String getName() {
          return name;
      }
      public void setName(String name) {
          this.name = name;
      }
      public Type getType() {
          return type;
      }
      public void setType(Type type) {
          this.type = type;
      }
      public String getContactNumber() {
          return contactNumber;
      }
      public void setContactNumber(String contactNumber) {
          this.contactNumber = contactNumber;
      }
      public String getRating() {
          return rating;
      }
      public void setRating(String rating) {
          this.rating = rating;
      }
      public String getAddress() {
          return address;
      }
      public void setAddress(String address) {
          this.address = address;
      }
      public Double getLatitude() {
          return latitude;
      }
      public void setLatitude(Double latitude) {
          this.latitude = latitude;
      }
      public Double getLongitude() {
          return longitude;
      }
      public void setLongitude(Double longitude) {
          this.longitude = longitude;
      }


      
    

}
