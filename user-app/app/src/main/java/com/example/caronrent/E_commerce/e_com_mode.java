package com.example.caronrent.E_commerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.caronrent.R;

public class e_com_mode extends AppCompatActivity {
    LinearLayout add_gadgets;
    ConstraintLayout show_gadgets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecom_mode);

        add_gadgets = findViewById(R.id.add_gadgets);
        show_gadgets = findViewById(R.id.show_gadgets);

        add_gadgets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(e_com_mode.this, e_commerce_item_add.class);
                startActivity(intent);
            }
        });

        show_gadgets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(e_com_mode.this, e_com_gadgets_mode.class);
                startActivity(intent);
            }
        });

    }
}