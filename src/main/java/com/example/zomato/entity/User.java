package com.example.zomato.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.util.ArrayList;
import java.util.List;

import com.example.zomato.enums.Gender;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    @Column(name = "mobile")
    private String mobile;
    private String dob;
    private String anniversary;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String profile_pic;


    @OneToMany(mappedBy = "user"  , cascade = CascadeType.ALL)
    private List<UserAddress> addresses = new ArrayList<>();

    public void setAddresses(List<UserAddress> addresses) {
    this.addresses = addresses;
    if(addresses != null){
        for(UserAddress addr : addresses){
            addr.setUser(this);
        }
    }
}
    public List<UserAddress> getAddresses() {
        return addresses;
    }

    public User(Long id, String name, String email, String mobile, String dob, String anniversary, Gender gender,
            String profile_pic) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.dob = dob;
        this.anniversary = anniversary;
        this.gender = gender;
        this.profile_pic = profile_pic;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAnniversary() {
        return anniversary;
    }

    public void setAnniversary(String anniversary) {
        this.anniversary = anniversary;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }


     
}
