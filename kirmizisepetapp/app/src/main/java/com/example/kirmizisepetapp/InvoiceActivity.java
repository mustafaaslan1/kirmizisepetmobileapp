package com.example.kirmizisepetapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class InvoiceActivity extends AppCompatActivity {

    private TextView tvInvoiceDetails;
    private Button btnSendEmail;

    private String fullName, address, phone, paymentMethod;
    private String invoiceText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);

        tvInvoiceDetails = findViewById(R.id.tv_invoice_details);
        btnSendEmail = findViewById(R.id.btn_send_email);

        // Kullanıcı bilgilerini al
        fullName = getIntent().getStringExtra("fullName");
        address = getIntent().getStringExtra("address");
        phone = getIntent().getStringExtra("phone");
        paymentMethod = getIntent().getStringExtra("paymentMethod");

        // Ürünleri çek
        List<CartItem> cartItems = CartManager.getInstance().getCartItems();
        double totalPrice = 0.0;

        StringBuilder invoiceBuilder = new StringBuilder();
        invoiceBuilder.append("Adı Soyadı: ").append(fullName).append("\n");
        invoiceBuilder.append("Adres: ").append(address).append("\n");
        invoiceBuilder.append("Telefon: ").append(phone).append("\n");
        invoiceBuilder.append("Ödeme Yöntemi: ").append(paymentMethod).append("\n\n");
        invoiceBuilder.append("Ürünler:\n");

        // Sepetteki her bir ürünü fatura bilgilerine ekle
        for (CartItem item : cartItems) {
            double itemTotal = item.getPrice() * item.getQuantity();
            invoiceBuilder.append("- ").append(item.getName())
                    .append(" x").append(item.getQuantity())
                    .append(" - ").append(String.format("₺%.2f", itemTotal)).append("\n");
            totalPrice += itemTotal;
        }

        // Toplam tutarını fatura detaylarına ekle
        invoiceBuilder.append("\nToplam Tutar: ").append(String.format("₺%.2f", totalPrice));
        invoiceText = invoiceBuilder.toString(); // Fatura metnini oluştur

        tvInvoiceDetails.setText(invoiceText); // Fatura detaylarını TextView'e ekle

        btnSendEmail.setOnClickListener(v -> sendEmail(invoiceText));

        // Fatura gösterildikten sonra sepeti temizle
        CartManager.getInstance().clearCart();
    }

    // Email gönderme işlemi
    private void sendEmail(String content) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Satın Alma Faturası");
        intent.putExtra(Intent.EXTRA_TEXT, content);
        startActivity(Intent.createChooser(intent, "Mail gönder"));
    }
}
