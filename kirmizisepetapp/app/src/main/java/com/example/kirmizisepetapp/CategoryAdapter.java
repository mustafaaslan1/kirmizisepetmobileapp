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

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<Category> categoryList;
    private Context context;
    private int selectedPosition = -1; // Seçili pozisyon, başlangıçta yok

    public CategoryAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_item, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.tvCategoryName.setText(category.getName());
        holder.imgCategoryIcon.setImageResource(category.getIconResId());

        // Seçilen karta hafif grimsi arka plan ver, diğerlerine varsayılan
        if (selectedPosition == position) {
            holder.itemView.setBackgroundResource(R.drawable.secilen_kategori_bg);
        } else {
            holder.itemView.setBackgroundResource(0);
        }

        holder.itemView.setOnClickListener(v -> {
            int previousPosition = selectedPosition;
            selectedPosition = position;

            // Önceki ve yeni seçili öğeyi yenile
            notifyItemChanged(previousPosition);
            notifyItemChanged(selectedPosition);

            // Kategoriye tıklanınca activity başlat
            Intent intent = new Intent(context, CategoryProductsActivity.class);
            intent.putExtra("categoryName", category.getName());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategoryName;
        ImageView imgCategoryIcon;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
            imgCategoryIcon = itemView.findViewById(R.id.imgCategoryIcon);
        }
    }
}
