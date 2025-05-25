package com.example.kirmizisepetapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HelpFragment extends Fragment {

    public HelpFragment() {
        // Boş constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_help, container, false);

        Button contactSupportButton = view.findViewById(R.id.contact_support);
        contactSupportButton.setOnClickListener(v -> sendSupportEmail());

        return view;
    }

    private void sendSupportEmail() {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"destek@kirmizisepet.com"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Destek Talebi");

        try {
            startActivity(Intent.createChooser(emailIntent, "E-posta ile gönder"));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getContext(), "E-posta uygulaması bulunamadı.", Toast.LENGTH_SHORT).show();
        }
    }
}
