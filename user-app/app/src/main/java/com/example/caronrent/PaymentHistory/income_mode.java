package com.example.caronrent.PaymentHistory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.caronrent.BookingHistory.bookHistory;
import com.example.caronrent.BookingHistory.bookHistory1;
import com.example.caronrent.BookingHistory.bookHistory2;
import com.example.caronrent.E_commerce.gadget_history;
import com.example.caronrent.R;

public class income_mode extends AppCompatActivity {
    LinearLayout from_car;
    ConstraintLayout from_gadgets;
    String na;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_mode);


        from_car = findViewById(R.id.from_car);
        from_gadgets = findViewById(R.id.from_gadgets);

        from_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(income_mode.this, bookHistory2.class);

                startActivity(intent);
            }
        });

        from_gadgets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(income_mode.this, gadget_history.class);

                startActivity(intent);
            }
        });
    }
}