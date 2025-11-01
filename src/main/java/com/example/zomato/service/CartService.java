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
        CartItem existingItem = cart.getItems().stream().filter(item -> item.getMenu().getId().equals(menuId))
                .findFirst().orElse(null);
        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + 1);
            cartIemRepo.save(existingItem);
            return menu.getName() + " " + "Item Quantity Increased by 1";
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setMenu(menu);
            cart.getItems().add(cartItem);

            cartIemRepo.save(cartItem);
            cartRepo.save(cart);
            return menu.getName() + " " + "Item Added to Cart";
        }

    }

    public Cart viewChart(Long userId) {
        Cart cart = cartRepo.findByUserId(userId).orElseThrow(() -> new RuntimeException("Cart Not Found"));
        return cart;
    }

    public String removeItemFromCart(Long userId, Long menuIdToBeRemoved) {
        Cart cart = cartRepo.findByUserId(userId).orElseThrow(() -> new RuntimeException("Cart Not Found"));
        CartItem cartItem = cart.getItems()
                .stream()
                .filter(item -> item.getMenu().getId().equals(menuIdToBeRemoved))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Menu Item Not Found in Cart"));

        int newQuantity = cartItem.getQuantity() - 1;

        if (newQuantity > 0) {
            cartItem.setQuantity(newQuantity);
            cartIemRepo.save(cartItem);
            return cartItem.getMenu().getName() + " " + "CartItem quantity decreased by 1";
        } else {
            // Remove Item from cart
            cart.getItems().remove(cartItem);
            cartIemRepo.delete(cartItem);

            if (cart.getItems().isEmpty()) {
                cartRepo.delete(cart);
                return "Item removed. Cart is now empty and deleted.";
            }

            cartRepo.save(cart);
            return "Item removed from cart";
        }

    }


    public int getItemQuantity(Long userId , Long menuId){
        Cart cart = cartRepo.findByUserId(userId).orElseThrow(() -> new RuntimeException("Cart Not Found"));
        CartItem cartItem = cart.getItems().stream().filter(item -> item.getMenu().getId().equals(menuId)).findFirst().orElseThrow(()->new RuntimeException("Item Not Found in Cart"));
        return cartItem.getQuantity();
    }
}
