package com.example.kirmizisepetapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartFragment extends Fragment implements CartAdapter.CartUpdateListener {

    private RecyclerView rvCartItems;
    private TextView tvTotalPrice;
    private Button btnCheckout;
    private CartAdapter cartAdapter;
    private ArrayList<CartItem> cartItems = new ArrayList<>();

    public CartFragment() {
        // Boş constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_cart, container, false);

        rvCartItems = view.findViewById(R.id.rv_cart_items);
        tvTotalPrice = view.findViewById(R.id.tv_total_price);
        btnCheckout = view.findViewById(R.id.btn_checkout);

        rvCartItems.setLayoutManager(new LinearLayoutManager(getContext()));

        // İlk etapta adapter boş listeden oluşturulabilir (isteğe bağlı)
        cartAdapter = new CartAdapter(cartItems, getContext(), this);
        rvCartItems.setAdapter(cartAdapter);

        // Firebase'den verileri çek
        FirebaseDatabase.getInstance().getReference("cart")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        cartItems.clear();
                        for (DataSnapshot child : snapshot.getChildren()) {
                            CartItem item = child.getValue(CartItem.class);
                            if (item != null) {
                                cartItems.add(item);
                            }
                        }
                        cartAdapter.notifyDataSetChanged();
                        updateTotalPrice();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("CartFragment", "Firebase hata: " + error.getMessage());
                        Toast.makeText(getContext(), "Sepet verileri yüklenemedi", Toast.LENGTH_SHORT).show();
                    }
                });

        btnCheckout.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), CheckoutActivity.class);
            startActivity(intent);
        });

        return view;
    }

    @Override
    public void updateTotalPrice() {
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getPrice() * item.getQuantity();
        }
        tvTotalPrice.setText("Toplam: ₺" + String.format("%.2f", total));
    }
}
