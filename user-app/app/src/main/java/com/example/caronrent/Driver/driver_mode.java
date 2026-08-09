package com.example.caronrent.Driver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.caronrent.Add_car.add_car;
import com.example.caronrent.Add_car.car_mode;
import com.example.caronrent.Add_car.show_car;
import com.example.caronrent.R;
import com.google.firebase.database.DatabaseReference;

public class driver_mode extends AppCompatActivity {

    LinearLayout add_driver;
    ConstraintLayout show_driver;

    String na;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_mode);

//        add_driver = findViewById(R.id.add_driver);
        show_driver = findViewById(R.id.show_driver);

        Intent in = getIntent();
        na = in.getStringExtra("na");

        add_driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(driver_mode.this, add_driver.class);
                intent.putExtra("em",na);
                startActivity(intent);
            }
        });

        show_driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(driver_mode.this, show_driver.class);
                intent.putExtra("em",na);
                startActivity(intent);
            }
        });
    }
}