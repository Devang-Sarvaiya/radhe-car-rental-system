package com.example.caronrentrenter;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.caronrentrenter.CompassFragment;
import com.example.caronrentrenter.FavoriteFragment;
import com.example.caronrentrenter.HomeFragment;
import com.example.caronrentrenter.ProfileFragment;
import com.example.caronrentrenter.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainFragment extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

        bottomNavigationView = findViewById(R.id.bottomNavView);
        frameLayout = findViewById(R.id.frameLayout);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();

                if (itemId == R.id.navHome) {
                    loadFragment(new HomeFragment(), false);
                } else if (itemId == R.id.navFavourite) {
                    loadFragment(new FavoriteFragment(), false);
                } else if (itemId == R.id.navCompass) {
                    loadFragment(new CompassFragment(), false);
                } else {
                    loadFragment(new ProfileFragment(), false);
                }

                return true;
            }
        });

        loadFragment(new HomeFragment(), true);
    }

    private void loadFragment(Fragment fragment, boolean isAppInitialized) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (isAppInitialized) {
            fragmentTransaction.add(R.id.frameLayout, fragment);
        } else {
            fragmentTransaction.replace(R.id.frameLayout, fragment);
        }

        fragmentTransaction.commit();
    }

    // Optional: Implement method to show/hide bottom navigation
    public void hideBottomNavigation() {
        bottomNavigationView.setVisibility(View.GONE);
    }

    public void showBottomNavigation() {
        bottomNavigationView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        // Get the current fragment
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frameLayout);

        // Check if the current fragment is the CompassFragment
        if (currentFragment instanceof CompassFragment) {
            loadFragment(new HomeFragment(), false);
            // If the current fragment is the CompassFragment, navigate to HomeFragment
            bottomNavigationView.setSelectedItemId(R.id.navHome);
        } else if (currentFragment instanceof FavoriteFragment) {
            loadFragment(new HomeFragment(), false);
            // If the current fragment is the CompassFragment, navigate to HomeFragment
            bottomNavigationView.setSelectedItemId(R.id.navHome);
        }
        else if (currentFragment instanceof ProfileFragment) {
            loadFragment(new HomeFragment(), false);
            // If the current fragment is the CompassFragment, navigate to HomeFragment
            bottomNavigationView.setSelectedItemId(R.id.navHome);
        }

        else {
            // If the current fragment is not the CompassFragment,
            // let the system handle the back press event
            super.onBackPressed();
        }
    }
}
