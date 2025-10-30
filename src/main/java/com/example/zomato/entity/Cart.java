package com.example.zomato.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private User user;

    // cascade = CascadeType.ALL: Any operation performed on the Cart (like save,
    // update, delete) will be also cascaded to the associated CartItem objects in
    // items. For example, deleting a cart removes all its items automatically.​

    // orphanRemoval=true:
    // If a CartItem object is removed from the items list (and no longer
    //  referenced by any Cart), that item gets deleted from
    //  the database automatically when the cart is saved.
    // This keeps your database clean and free of "orphaned" line items.

    @OneToMany(mappedBy = "cart" , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    // public Cart(long id, User user, List<CartItem> items) {
    //     this.id = id;
    //     this.user = user;
    //     this.items = items;
    // }

    

}
