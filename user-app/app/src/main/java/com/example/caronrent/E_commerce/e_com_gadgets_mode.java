package com.example.caronrent.E_commerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.caronrent.R;

public class e_com_gadgets_mode extends AppCompatActivity {
    LinearLayout your_gadgets;
    ConstraintLayout other_gadgets;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecom_gadgets_mode);


        your_gadgets = findViewById(R.id.your_gadgets);
        other_gadgets = findViewById(R.id.other_gadgets);

        your_gadgets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(e_com_gadgets_mode.this, e_com_Renters_gadgets.class);
                startActivity(intent);
            }
        });

        other_gadgets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(e_com_gadgets_mode.this, e_commerce_gadgets.class);
                startActivity(intent);
            }
        });

    }
}