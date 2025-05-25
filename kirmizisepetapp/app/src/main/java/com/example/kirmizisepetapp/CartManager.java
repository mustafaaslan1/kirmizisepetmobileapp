package com.example.kirmizisepetapp;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CartManager {
    private static CartManager instance;
    private ArrayList<CartItem> cartItems;
    private DatabaseReference cartRef;

    private CartManager() {
        cartItems = new ArrayList<>();
        cartRef = FirebaseDatabase.getInstance().getReference("cart");
    }

    public static CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void addToCart(CartItem item) {
        cartItems.add(item);
        cartRef.push().setValue(item);
    }

    public ArrayList<CartItem> getCartItems() {
        return cartItems;
    }

    public double getTotalPrice() {
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }

    public void clearCart() {
        cartItems.clear();
        cartRef.removeValue();
    }
}
