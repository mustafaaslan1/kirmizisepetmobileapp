package com.example.kirmizisepetapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductVH> {

    private final List<Product> products;
    private final Context ctx;

    public ProductAdapter(Context ctx, List<Product> products) {
        this.ctx = ctx;
        this.products = products;
    }

    @NonNull
    @Override
    public ProductVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.item_product, parent, false);
        return new ProductVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductVH h, int pos) {
        Product p = products.get(pos);
        h.tvName.setText(p.getName());
        h.tvPrice.setText(String.format("₺%.2f", p.getPrice()));

        // Ürünün görsel ismine göre drawable'dan resim çekiliyor
        int imageResId = ctx.getResources().getIdentifier(
                p.getImageName(), "drawable", ctx.getPackageName());

        // Eğer resim bulunamazsa varsayılan sepet resmi gösteriliyor
        if (imageResId != 0) {
            h.ivProduct.setImageResource(imageResId);
        } else {
            h.ivProduct.setImageResource(R.drawable.alisverissepeti);
        }

        // Ürün kartına tıklanınca detay ekranına geç
        h.itemView.setOnClickListener(v -> {
            Intent i = new Intent(ctx, ProductDetailActivity.class);
            i.putExtra("product_id", p.getId());
            ctx.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    static class ProductVH extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice;
        ImageView ivProduct;

        ProductVH(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
            ivProduct = itemView.findViewById(R.id.iv_product);
        }
    }

    // Liste güncellendiğinde RecyclerView'a bildiriliyor
    public void updateProducts(List<Product> newProducts) {
        this.products.clear();
        this.products.addAll(newProducts);
        notifyDataSetChanged();
    }
}
