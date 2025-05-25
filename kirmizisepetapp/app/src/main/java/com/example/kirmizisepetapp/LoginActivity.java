package com.example.kirmizisepetapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends Activity {

    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private TextView tvRegister;
    private TextView tvForgot;
    private CheckBox cbRememberMe;
    private FirebaseAuth mAuth; // Firebase Authentication nesnesi

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        initViews(); // XML'deki view'lar tanımlanıyor

        btnLogin.setOnClickListener(v -> handleLogin());

        tvRegister.setOnClickListener(v ->
                startActivity(new Intent(this, RegisterActivity.class)));

        tvForgot.setOnClickListener(v ->
                startActivity(new Intent(this, PasswordResetActivity.class)));
    }
    // Gerektiğinde kolayca güncellenebilmesi için
    // kullanıcı arayüzü bileşenlerini bir noktada topladım
    private void initViews() {
        etUsername   = findViewById(R.id.et_username);
        etPassword   = findViewById(R.id.et_password);
        btnLogin     = findViewById(R.id.btn_login);
        tvRegister   = findViewById(R.id.tv_register);
        tvForgot     = findViewById(R.id.tv_forgot);
        cbRememberMe = findViewById(R.id.cb_remember); // şu an kullanılmıyor
    }

    // Kullanıcı girişini kontrol eder
    private void handleLogin() {
        String email = etUsername.getText().toString().trim();
        String pass  = etPassword.getText().toString();

        if (email.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Lütfen tüm alanları doldurun!", Toast.LENGTH_SHORT).show();
            return; // Eğer alanlar boşsa giriş denemesi yapma
        }

        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Giriş başarılı", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, MainMenuActivity.class));
                        finish();
                    } else {
                        Toast.makeText(this, "Giriş başarısız, böyle bir kullanıcı bulunamadı!", Toast.LENGTH_LONG).show();
                    }
                });
    }
}
