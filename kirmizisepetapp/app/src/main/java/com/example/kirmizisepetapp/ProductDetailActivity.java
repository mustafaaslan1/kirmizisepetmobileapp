package com.example.kirmizisepetapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProductDetailActivity extends Activity {

    private TextView tvName, tvPrice, tvRating;
    private RatingBar ratingBar;
    private WebView webView;
    private Button btnAddCart;
    private ImageView imageProduct;
    private ImageView ivCartIcon;

    private Product product;
    private DatabaseReference productRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        productRef = FirebaseDatabase.getInstance().getReference("products");

        tvName = findViewById(R.id.tv_product_name);
        tvPrice = findViewById(R.id.tv_product_price);
        tvRating = findViewById(R.id.tv_rating);
        ratingBar = findViewById(R.id.rating_bar);
        webView = findViewById(R.id.web_view);
        btnAddCart = findViewById(R.id.btn_add_to_cart);
        imageProduct = findViewById(R.id.iv_product);
        ivCartIcon = findViewById(R.id.iv_cart_icon);

        String productId = getIntent().getStringExtra("product_id");

        getProductById(productId, product -> {
            if (product != null) {
                ProductDetailActivity.this.product = product;
                tvName.setText(product.getName());
                tvPrice.setText(String.format("₺%.2f", product.getPrice()));

                int imageResId = getResources().getIdentifier(
                        product.getImageName().replace(".png", "").toLowerCase(),
                        "drawable",
                        getPackageName()
                );

                if (imageResId != 0) {
                    imageProduct.setImageResource(imageResId);
                } else {
                    imageProduct.setImageResource(R.drawable.alisverissepeti);
                }
            }
        });

        setupWebView();

        ratingBar.setOnRatingBarChangeListener((bar, rating, fromUser) -> {
            tvRating.setText(String.format("%.1f Puan verdiniz", rating));
        });
        ratingBar.setRating(0);

        btnAddCart.setOnClickListener(v -> {
            if (product != null) {
                CartManager.getInstance().addToCart(new CartItem(product.getName(), product.getPrice(), 1));
                ivCartIcon.setVisibility(View.VISIBLE);
            }
        });

        ivCartIcon.setOnClickListener(v -> {
            Intent intent = new Intent(ProductDetailActivity.this, MainMenuActivity.class);
            intent.putExtra("navigate_to", "cart");
            startActivity(intent);
            finish();
        });
    }

    private void setupWebView() {
        WebSettings ws = webView.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setLoadWithOverviewMode(true);
        ws.setUseWideViewPort(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.vatanbilgisayar.com");
    }

    public interface ProductCallback {
        void onProductLoaded(Product product);
    }

    public void getProductById(String id, ProductCallback callback) {
        productRef.orderByChild("id").equalTo(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Product product = snapshot.getChildren().iterator().next().getValue(Product.class);
                    callback.onProductLoaded(product);
                } else {
                    callback.onProductLoaded(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("ProductDetail", "Veri alınamadı: " + error.getMessage());
            }
        });
    }
}
