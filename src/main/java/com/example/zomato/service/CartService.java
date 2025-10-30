package com.example.zomato.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.zomato.entity.Cart;
import com.example.zomato.entity.CartItem;
import com.example.zomato.entity.Menu;
import com.example.zomato.entity.User;
import com.example.zomato.repository.CartIemRepo;
// import com.example.zomato.entity.CartItem;
import com.example.zomato.repository.CartRepo;
import com.example.zomato.repository.MenuRepo;
import com.example.zomato.repository.UserRepo;

@Service
public class CartService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MenuRepo menuRepo;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private CartIemRepo cartIemRepo;

    public String addItemtoCart(Long userId, Long menuId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found"));
        Menu menu = menuRepo.findById(menuId).orElseThrow(() -> new RuntimeException("Menu Not Found"));

        // find and create a cart
        Cart cart = cartRepo.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepo.save(newCart);
                });

        // check if same item already in cart (optional future improvement)

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setMenu(menu);
        cart.getItems().add(cartItem);

        cartIemRepo.save(cartItem);
        cartRepo.save(cart);
        return menu.getName() + " " + "Item Added to Cart";
    }


    public Cart viewChart(Long userId){
    Cart cart = cartRepo.findByUserId(userId).orElseThrow(()-> new RuntimeException("Cart Not Found"));
    return cart;
    }

    
}
