package com.example.caronrent.PaymentHistory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.caronrent.Add_car.add_car;
import com.example.caronrent.Add_car.car_mode;
import com.example.caronrent.Add_car.show_car;
import com.example.caronrent.E_commerce.gadget_history;
import com.example.caronrent.R;

public class payment_history_mode extends AppCompatActivity {

    LinearLayout income;
    ConstraintLayout expences;
    String na;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history_mode);

        income = findViewById(R.id.income);
        expences = findViewById(R.id.expences);

        income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(payment_history_mode.this, income_mode.class);
                startActivity(intent);
            }
        });

        expences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(payment_history_mode.this, expences.class);
                startActivity(intent);
            }
        });
    }
}