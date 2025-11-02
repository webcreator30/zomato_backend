package com.example.zomato.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.zomato.entity.Cart;
import com.example.zomato.entity.CartItem;
import com.example.zomato.entity.Order;
import com.example.zomato.entity.OrderItem;
import com.example.zomato.entity.Menu;
import com.example.zomato.repository.CartIemRepo;
import com.example.zomato.repository.CartRepo;
import com.example.zomato.repository.MenuRepo;
import com.example.zomato.repository.OrderItemRepo;
import com.example.zomato.repository.OrderRepo;

@Service
public class OrderService {

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private CartIemRepo cartIemRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private OrderItemRepo orderItemRepo;

    @Autowired
    private MenuRepo menuRepo;

    public String placeOrder(Long userId) {
        Cart cart = cartRepo.findByUserId(userId).orElseThrow(() -> new RuntimeException("Cart Not Found"));
        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty! Cannot place order.");
        }
        // Getting Restaurant Id assuming that in a cart all cart items belong to same
        // restaurant
        List<Long> menuIds = cart.getItems().stream().map(item -> item.getMenu().getId()).toList();
        List<Menu> menuList = menuRepo.findAllById(menuIds);
        Map<Long, Menu> menuMap = new HashMap<>();
        for (Menu m : menuList) {
            menuMap.put(m.getId(), m);
        }
        // Create new order
        Order order = new Order();
        order.setUserId(userId);
        order.setRestaurantId(menuList.get(0).getRestaurantId());
        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (CartItem cartItem : cart.getItems()) {
            Menu menu = menuMap.get(cartItem.getMenu().getId());
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setMenuItemId(menu.getId());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPriceAtOrder(menu.getPrice());
            totalAmount = totalAmount.add(menu.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);
        order.setTotalAmount(totalAmount);

        orderRepo.save(order);

        // Clear Cart After Order Placed
        cart.getItems().clear();
        cartRepo.save(cart);

        return "Order placed Successfully Order Id: " + order.getId();

    }
}
