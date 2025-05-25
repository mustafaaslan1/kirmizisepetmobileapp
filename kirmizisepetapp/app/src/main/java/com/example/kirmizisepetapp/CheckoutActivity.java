package com.example.kirmizisepetapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CheckoutActivity extends AppCompatActivity {

    private EditText etFullName, etAddress, etPhone;
    private Spinner spinnerPaymentMethod;
    private Button btnCompletePurchase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        etFullName = findViewById(R.id.et_full_name);
        etAddress = findViewById(R.id.et_address);
        etPhone = findViewById(R.id.et_phone);
        spinnerPaymentMethod = findViewById(R.id.spinner_payment_method);
        btnCompletePurchase = findViewById(R.id.btn_complete_purchase);

        // Ödeme yöntemi seçeneklerini yükle
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.payment_methods, // res/values/strings.xml içinde tanımlanmalı
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPaymentMethod.setAdapter(adapter);

        // Satın al butonuna tıklama olayı
        btnCompletePurchase.setOnClickListener(v -> onCompletePurchase());
    }

    public void onCompletePurchase() {
        // Kullanıcının girdiği verileri al
        String fullName = etFullName.getText().toString();
        String address = etAddress.getText().toString();
        String phone = etPhone.getText().toString();
        String paymentMethod = spinnerPaymentMethod.getSelectedItem().toString();

        // Alanların boş olup olmadığını kontrol et
        if (TextUtils.isEmpty(fullName) || TextUtils.isEmpty(address) || TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Lütfen tüm alanları doldurun!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Telefon numarasının geçerli formatta olup olmadığını kontrol et
        if (!phone.matches("\\d{10}")) {
            Toast.makeText(this, "Geçerli bir telefon numarası girin!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kullanıcıya başarılı bir mesaj göster
        Toast.makeText(this, "Siparişiniz başarıyla verildi! \n Fatura ekranına yönlendiriliyorsunuz!", Toast.LENGTH_LONG).show();

        // Fatura ekranına geçiş
        Intent intent = new Intent(CheckoutActivity.this, InvoiceActivity.class);
        intent.putExtra("fullName", fullName);
        intent.putExtra("address", address);
        intent.putExtra("phone", phone);
        intent.putExtra("paymentMethod", paymentMethod);
        startActivity(intent); // Yeni activity başlat
    }
}
