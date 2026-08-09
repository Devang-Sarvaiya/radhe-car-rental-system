package com.example.caronrent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Login_Mode extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton rdb1, rdb2;
    String selectedOption;
    AppCompatButton btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_mode);

        radioGroup = findViewById(R.id.radioGroup);
        rdb1 = findViewById(R.id.rdb1);
        rdb2 = findViewById(R.id.rdb2);
        btn = findViewById(R.id.btn_login_mode);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Check which radio button is selected
                RadioButton radioButton = findViewById(checkedId);

                if (radioButton != null) {
                    selectedOption = radioButton.getText().toString();
                    Toast.makeText(Login_Mode.this, "Selected option: " + selectedOption, Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedOption.equals("Renters")){
                    startActivity(new Intent(Login_Mode.this, Login.class));
                }else{
                    startActivity(new Intent(Login_Mode.this, Login_Driver.class));
                }
            }
        });



    }
}