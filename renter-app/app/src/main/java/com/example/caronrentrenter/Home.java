package com.example.caronrentrenter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView nav;

        nav=findViewById(R.id.nav);
        nav.setSelectedItemId(R.id.home);

        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.home) {
                    return true;
                } else if (item.getItemId() == R.id.fav) {
                    startActivity(new Intent(getApplicationContext(), Favorite.class));
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                    finish();
                    return true;
                } else if (item.getItemId() == R.id.watch) {
                    startActivity(new Intent(getApplicationContext(), Watch.class));
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                    finish();
                    return true;
                } else if (item.getItemId() == R.id.history) {
                    startActivity(new Intent(getApplicationContext(), History.class));
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                    finish();
                    return true;
                } else if (item.getItemId() == R.id.setting) {
                    startActivity(new Intent(getApplicationContext(), Settings.class));
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                    finish();
                    return true;
                }
                return false;
            }
        });

    }
    @SuppressLint("MissingSuperCall")
    public void onBackPressed() {
        finishAffinity();
        Home.this.finish();
        //System.exit(0);
    }
}