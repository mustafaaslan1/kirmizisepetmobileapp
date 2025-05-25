package com.example.kirmizisepetapp;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AccountFragment extends Fragment {

    private TextView tvName, tvEmail, tvPhone, tvBirth, tvRegisterDate, tvEmailStatus;
    private Button btnVerifyEmail, btnResetPassword;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseReference userRef;

    public AccountFragment() {
        // Boş constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_account, container, false);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        userRef = FirebaseDatabase.getInstance().getReference("users").child(currentUser.getUid());

        tvName = view.findViewById(R.id.tv_name);
        tvEmail = view.findViewById(R.id.tv_email);
        tvPhone = view.findViewById(R.id.tv_phone);
        tvBirth = view.findViewById(R.id.tv_birth);
        tvRegisterDate = view.findViewById(R.id.tv_register_date);
        tvEmailStatus = view.findViewById(R.id.tv_email_status);
        btnVerifyEmail = view.findViewById(R.id.btn_verify_email);
        btnResetPassword = view.findViewById(R.id.btn_reset_password);

        loadUserData();
        checkEmailVerification();

        btnVerifyEmail.setOnClickListener(v -> currentUser.sendEmailVerification()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "Doğrulama e-postası gönderildi!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "E-posta gönderilemedi: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }));

        btnResetPassword.setOnClickListener(v -> {
            String email = currentUser.getEmail();
            if (email != null) {
                mAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "Şifre yenileme e-postası gönderildi.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Hata: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        return view;
    }

    private void loadUserData() {
        userRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String name = task.getResult().child("name").getValue(String.class);
                String surname = task.getResult().child("surname").getValue(String.class);
                String email = task.getResult().child("email").getValue(String.class);
                String phone = task.getResult().child("phone").getValue(String.class);
                String birth = task.getResult().child("birth").getValue(String.class);
                String joinDate = task.getResult().child("joinDate").getValue(String.class);

                tvName.setText("Adı Soyadı: " + name + " " + surname);
                tvEmail.setText("E-posta: " + email);
                tvPhone.setText("Telefon: " + phone);
                tvBirth.setText("Doğum Tarihi: " + birth);
                tvRegisterDate.setText("Kayıt Tarihi: " + joinDate);
            } else {
                Toast.makeText(getContext(), "Kullanıcı bilgileri alınamadı.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkEmailVerification() {
        currentUser.reload().addOnCompleteListener(task -> {
            if (currentUser.isEmailVerified()) {
                tvEmailStatus.setText("E-posta Durumu: ✅ Doğrulandı");
                tvEmailStatus.setTextColor(Color.parseColor("#388E3C"));
            } else {
                tvEmailStatus.setText("E-posta Durumu: ❌ Doğrulanmadı");
                tvEmailStatus.setTextColor(Color.parseColor("#D32F2F"));
            }
        });
    }
}
