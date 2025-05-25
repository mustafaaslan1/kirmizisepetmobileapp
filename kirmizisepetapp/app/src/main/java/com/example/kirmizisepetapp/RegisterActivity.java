package com.example.kirmizisepetapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class RegisterActivity extends Activity {

    private EditText etPhone, etEmail, etName, etSurname, etBirth, etPass, etPassRepeat;
    private Button btnSignUp;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        initViews();

        btnSignUp.setOnClickListener(v -> handleRegister());
    }

    private void initViews() {
        etPhone       = findViewById(R.id.et_phone);
        etEmail       = findViewById(R.id.et_email);
        etName        = findViewById(R.id.et_name);
        etSurname     = findViewById(R.id.et_surname);
        etBirth       = findViewById(R.id.et_birth);
        etPass        = findViewById(R.id.et_password);
        etPassRepeat  = findViewById(R.id.et_password_repeat);
        btnSignUp     = findViewById(R.id.btn_sign_up);
    }

    private void handleRegister() {
        String phone   = etPhone.getText().toString().trim();
        String email   = etEmail.getText().toString().trim();
        String name    = etName.getText().toString().trim();
        String surname = etSurname.getText().toString().trim();
        String birth   = etBirth.getText().toString().trim();
        String pass1   = etPass.getText().toString();
        String pass2   = etPassRepeat.getText().toString();

        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(email) ||
                TextUtils.isEmpty(name) || TextUtils.isEmpty(surname) ||
                TextUtils.isEmpty(birth) || TextUtils.isEmpty(pass1) || TextUtils.isEmpty(pass2)) {
            Toast.makeText(this, "Lütfen tüm alanları doldurun", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!pass1.equals(pass2)) {
            Toast.makeText(this, "Şifreler eşleşmiyor", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, pass1)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();

                        if (user != null) {
                            String uid = user.getUid();
                            String joinDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                            // Firebase Realtime Database referansı
                            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(uid);

                            Map<String, Object> userData = new HashMap<>();
                            userData.put("name", name);
                            userData.put("surname", surname);
                            userData.put("email", email);
                            userData.put("phone", phone);
                            userData.put("birth", birth);
                            userData.put("joinDate", joinDate);

                            userRef.setValue(userData)
                                    .addOnCompleteListener(dbTask -> {
                                        if (dbTask.isSuccessful()) {
                                            SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                                            SharedPreferences.Editor editor = prefs.edit();
                                            editor.putString("username", name);
                                            editor.apply();

                                            Toast.makeText(this, "Kayıt başarılı!", Toast.LENGTH_SHORT).show();
                                            finish();
                                        } else {
                                            Toast.makeText(this, "Veritabanına yazılamadı.", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    } else {
                        Toast.makeText(this, "Kayıt başarısız: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
