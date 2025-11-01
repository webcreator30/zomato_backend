package com.example.zomato.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.zomato.entity.Cart;
import com.example.zomato.service.CartService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add-item")
    public ResponseEntity<String> addItemToCart(@RequestBody Map<String, String> request) {
        Long userId = Long.parseLong(request.get("userId"));
        Long menuId = Long.parseLong(request.get("menuId"));
        String response = cartService.addItemtoCart(userId, menuId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/view-cart")
    public ResponseEntity<Cart> viewCart(@RequestBody Map<String, String> request) {
        Long userId = Long.parseLong(request.get("userId"));
        Cart cart = cartService.viewChart(userId);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/remove-item")
    public ResponseEntity<String> removeItem(@RequestBody Map<String, String> request) {
        Long userId = Long.parseLong(request.get("userId"));
        Long menuId = Long.parseLong(request.get("menuId"));
        String res = cartService.removeItemFromCart(userId, menuId);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/get-item-quantity")
    public ResponseEntity<Integer> getItemQuantity(@RequestBody Map<String, String> request) {
        Long userId = Long.parseLong(request.get("userId"));
        Long menuId = Long.parseLong(request.get("menuId"));
        int quan = cartService.getItemQuantity(userId, menuId);
        return ResponseEntity.ok(quan);
    }

}
