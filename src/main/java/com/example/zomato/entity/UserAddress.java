package com.example.zomato.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class UserAddress {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "user_id" , nullable = false)
    private User user;

    private String address_line;
    private String city;
    private String state;
    private String postalCode;
    private double latitude;
    private double longitude;
    private String label;
       public UserAddress(Long id, User user, String address_line, String city, String state, String postalCode,
            double latitude, double longitude, String label) {
        Id = id;
        this.user = user;
        this.address_line = address_line;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.label = label;
    }
       public UserAddress() {
    }
       public Long getId() {
           return Id;
       }
       public void setId(Long id) {
           Id = id;
       }
       public User getUser() {
           return user;
       }
       public void setUser(User user) {
           this.user = user;
       }
       public String getAddress_line() {
           return address_line;
       }
       public void setAddress_line(String address_line) {
           this.address_line = address_line;
       }
       public String getCity() {
           return city;
       }
       public void setCity(String city) {
           this.city = city;
       }
       public String getState() {
           return state;
       }
       public void setState(String state) {
           this.state = state;
       }
       public String getPostalCode() {
           return postalCode;
       }
       public void setPostalCode(String postalCode) {
           this.postalCode = postalCode;
       }
       public double getLatitude() {
           return latitude;
       }
       public void setLatitude(double latitude) {
           this.latitude = latitude;
       }
       public double getLongitude() {
           return longitude;
       }
       public void setLongitude(double longitude) {
           this.longitude = longitude;
       }
       public String getLabel() {
           return label;
       }
       public void setLabel(String label) {
           this.label = label;
       }
    
    
}
