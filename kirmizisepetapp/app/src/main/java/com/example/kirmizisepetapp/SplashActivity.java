package com.example.kirmizisepetapp;

import android.app.Activity;          // ← değişti
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }, 3500);


        TextView tvAppName = findViewById(R.id.tv_app_name);
        Animation bounce = AnimationUtils.loadAnimation(this, R.anim.bounce_animation);
        tvAppName.startAnimation(bounce);

    }
}
