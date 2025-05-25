package com.example.kirmizisepetapp;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;

public class PasswordResetActivity extends Activity {

    private EditText etEmail, etPhone;
    private Button btnReset;

    // Firebase Authentication oluşturuluyor
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        // FirebaseAuth başlatılıyor
        mAuth = FirebaseAuth.getInstance();
        initViews();

        // Butona tıklanınca şifre sıfırlama işlemini yapan fonksiyon çağrılıyor
        btnReset.setOnClickListener(v -> handleReset());
    }

    private void initViews() {
        // XML'deki view'lar burada tanımlanıyor
        etEmail = findViewById(R.id.et_email);
        etPhone = findViewById(R.id.et_phone);
        btnReset = findViewById(R.id.btn_reset);
    }

    private void handleReset() {
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();

        // Eğer herhangi bir alan boşsa uyarı veriyor
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Lütfen e-posta ve telefon numarasını girin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Firebase üzerinden e-posta adresine şifre sıfırlama bağlantısı gönderiliyor
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Şifre sıfırlama bağlantısı e-posta adresinize gönderildi", Toast.LENGTH_LONG).show();
                        finish(); // İşlem başarılıysa önceki sayfaya dön
                    } else {
                        Toast.makeText(this, "Hata: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
