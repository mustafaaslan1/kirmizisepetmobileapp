package com.example.kirmizisepetapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.kirmizisepetapp.fragments.DiscoverFragment;
import com.example.kirmizisepetapp.CartFragment;
import com.example.kirmizisepetapp.AccountFragment;
import com.example.kirmizisepetapp.HelpFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainMenuActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Varsayılan fragment: DiscoverFragment
        if (savedInstanceState == null) {
            loadFragment(new DiscoverFragment());
        }

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull android.view.MenuItem item) {
                Fragment selectedFragment = null;
                int id = item.getItemId();

                if (id == R.id.nav_discover) {
                    selectedFragment = new DiscoverFragment();
                } else if (id == R.id.nav_cart) {
                    selectedFragment = new CartFragment();
                } else if (id == R.id.nav_account) {
                    selectedFragment = new AccountFragment();
                } else if (id == R.id.nav_help) {
                    selectedFragment = new HelpFragment();
                } else {
                    return false;
                }

                loadFragment(selectedFragment);
                return true;
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, fragment)
                .commit();
    }
}
