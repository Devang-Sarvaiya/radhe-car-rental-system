package com.example.driver_module.Login_Registration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.driver_module.PickUp_detail;
import com.example.driver_module.Pick_upOTP;
import com.example.driver_module.R;

import java.util.ArrayList;
import java.util.List;

public class Login_PickUp extends AppCompatActivity {
    Spinner spinner;
    List<String> items2;
    String item2;
    AppCompatButton btn_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_pick_up);

        spinner = findViewById(R.id.spinner);
        btn_login = findViewById(R.id.btn_login);


        items2 = new ArrayList<>();
        items2.add(0, "PickUp Points");
        items2.add("A-011,Ambani Complex,Varachha,Surat");
        items2.add("B-022,Chitra Enterprise,Nana varachha,Surat");
        items2.add("C-033,Kalyan Complex,Katargam,Surat");
        items2.add("D-044,Rajhans Complex,Kamrej,Surat");
        items2.add("E-055,Silver Trade Center,Mota varachha,Surat");

        spinner.setAdapter(new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, items2));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!(spinner.getSelectedItem().toString().equals("PickUp Points"))) {
                    item2 = spinner.getSelectedItem().toString();
                    Toast.makeText(Login_PickUp.this, item2, Toast.LENGTH_SHORT).show();

                } else {
                    // Handle the case when "Car Company Category" is selected
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Login_PickUp.this, Pick_upOTP.class);
                Intent intent = new Intent(Login_PickUp.this, Pick_upOTP.class);
                intent.putExtra("pick",item2);
                startActivity(intent);
            }
        });

    }
}