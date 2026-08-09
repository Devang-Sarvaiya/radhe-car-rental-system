package com.example.car_admin.Driver;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.car_admin.Drivers;
import com.example.car_admin.R;

public class driver_mode extends AppCompatActivity {

    LinearLayout add_driver;
    ConstraintLayout show_driver;

    String na;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_mode);

        add_driver = findViewById(R.id.add_driver);
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
                Intent intent = new Intent(driver_mode.this, Drivers.class);
                intent.putExtra("em",na);
                startActivity(intent);
            }
        });
    }
}