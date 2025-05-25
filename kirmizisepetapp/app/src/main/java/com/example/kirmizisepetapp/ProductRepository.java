package com.example.kirmizisepetapp;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    // Firebase Realtime Database referansı
    private final DatabaseReference databaseRef;

    public ProductRepository() {
        // "products" adlı node'a referans alıyoruz
        databaseRef = FirebaseDatabase.getInstance().getReference("products");
    }

    // Veriler yüklendiğinde ne yapılacağını tanımlayan arayüz
    public interface ProductCallback {
        void onProductsLoaded(List<Product> products);
        void onProductLoaded(Product product);
    }

    public void getAllProducts(ProductCallback callback) {
        // Verileri bir defaya mahsus çekmek için listener ekleniyor
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Product> productList = new ArrayList<>();
                // Her bir product için dönüyoruz
                for (DataSnapshot data : snapshot.getChildren()) {
                    try {
                        // Veriyi Product nesnesine çeviriyoruz
                        Product product = data.getValue(Product.class);
                        if (product != null) {
                            productList.add(product);
                        }
                    } catch (Exception e) {
                        // Hata olursa log'a yazdır
                        Log.e("ProductRepo", "Veri dönüştürme hatası: " + e.getMessage());
                    }
                }
                // Veriler yüklendikten sonra callback ile gönderiliyor
                callback.onProductsLoaded(productList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Eğer veriye erişilemezse burada hatayı logla
                Log.e("ProductRepo", "Veri alınamadı: " + error.getMessage());
            }
        });
    }
}
