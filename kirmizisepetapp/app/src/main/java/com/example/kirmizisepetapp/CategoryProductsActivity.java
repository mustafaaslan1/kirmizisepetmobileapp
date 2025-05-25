package com.example.kirmizisepetapp;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CategoryProductsActivity extends AppCompatActivity {

    private TextView tvCategoryTitle;
    private RecyclerView rvCategoryProducts;
    private ProductAdapter productAdapter;
    private List<Product> productList;
    private DatabaseReference productRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_products);

        tvCategoryTitle = findViewById(R.id.tv_category_title);
        rvCategoryProducts = findViewById(R.id.rv_category_products);
        rvCategoryProducts.setLayoutManager(new LinearLayoutManager(this));

        // Kategori ismini al
        String categoryName = getIntent().getStringExtra("categoryName");
        tvCategoryTitle.setText(categoryName);

        // Firebase referansı
        productRef = FirebaseDatabase.getInstance().getReference("products");

        // Liste başlat
        productList = new ArrayList<>();
        productAdapter = new ProductAdapter(this, productList);
        rvCategoryProducts.setAdapter(productAdapter);

        // Firebase'den veri çek
        loadProductsByCategory(categoryName);
    }

    private void loadProductsByCategory(String category) {
        productRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Product product = dataSnapshot.getValue(Product.class);
                    if (product != null && product.getCategory().equalsIgnoreCase(category)) {
                        productList.add(product);
                    }
                }
                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CategoryProductsActivity.this, "Ürünler yüklenemedi: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
