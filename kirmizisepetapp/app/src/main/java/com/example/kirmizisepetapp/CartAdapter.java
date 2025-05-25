package com.example.kirmizisepetapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private ArrayList<CartItem> cartItems;
    private Context context;
    private CartUpdateListener updateListener;

    public interface CartUpdateListener {
        void updateTotalPrice();
    }

    public CartAdapter(ArrayList<CartItem> cartItems, Context context, CartUpdateListener updateListener) {
        this.cartItems = cartItems;
        this.context = context;
        this.updateListener = updateListener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem cartItem = cartItems.get(position);

        holder.tvName.setText(cartItem.getName());
        holder.tvPrice.setText("₺" + String.format("%.2f", cartItem.getPrice()));
        holder.tvQuantity.setText("Miktar: " + cartItem.getQuantity());

        holder.btnIncrease.setOnClickListener(v -> {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            notifyItemChanged(position);
            updateListener.updateTotalPrice();
        });

        holder.btnDecrease.setOnClickListener(v -> {
            if (cartItem.getQuantity() > 1) {
                cartItem.setQuantity(cartItem.getQuantity() - 1);
                notifyItemChanged(position);
                updateListener.updateTotalPrice();
            } else {
                // Miktar 1'in altına düşemez, denenmesi halinde uyarı gönder
                Toast.makeText(context, "Miktar 1'den daha düşük olamaz", Toast.LENGTH_SHORT).show();
            }
        });

        holder.btnDelete.setOnClickListener(v -> {
            cartItems.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, cartItems.size());
            updateListener.updateTotalPrice();
            Toast.makeText(context, "Ürün sepetten silindi", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    // CartViewHolder sınıfı, her bir öğenin görünümünü tutar (ad, fiyat, miktar, butonlar)
    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice, tvQuantity;
        Button btnIncrease, btnDecrease, btnDelete;

        public CartViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvPrice = itemView.findViewById(R.id.tv_item_price);
            tvQuantity = itemView.findViewById(R.id.tv_item_quantity);
            btnIncrease = itemView.findViewById(R.id.btn_increase);
            btnDecrease = itemView.findViewById(R.id.btn_decrease);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
