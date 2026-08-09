package com.example.caronrentrenter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.caronrentrenter.Payment.Main_Payment;
import com.google.android.material.snackbar.Snackbar;

public class Demo2 extends AppCompatActivity {
    EditText editText;
    Button button;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_demo2);

        editText = findViewById(R.id.editTextText2);

        button = findViewById(R.id.button3);

//        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
//        String eem = sharedPreferences.getString("otp", "");


        Intent t = getIntent();
        String mmprice = t.getStringExtra("mmmprice");
        String mob = t.getStringExtra("uMob");
        String slotNumber = t.getStringExtra("slot");
        String modelname = t.getStringExtra("modelName");
        String d1 = t.getStringExtra("sdate");
        String d2 = t.getStringExtra("edate");
        String rMO = t.getStringExtra("rMo");
        String imageUrl = t.getStringExtra("modelImageUrl");
        String mType = t.getStringExtra("cType");
        String mCom = t.getStringExtra("cCom");
        String pPoint = t.getStringExtra("pPoint");
        String dri = t.getStringExtra("dri");
        String ans = t.getStringExtra("price");
        String ottp = t.getStringExtra("otp");
        
//        Snackbar.make(editText,ottp,Snackbar.LENGTH_LONG);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otp = editText.getText().toString();
//                Toast.makeText(Demo2.this, eem, Toast.LENGTH_SHORT).show();

                if (otp == null) {
                    Snackbar.make(button, "Please enter an OTP", Snackbar.LENGTH_LONG);
                    Toast.makeText(Demo2.this, "Please enter an OTP", Toast.LENGTH_SHORT).show();
                } else if (ottp.equals(otp)) {
                    Intent t = new Intent(Demo2.this, Main_Payment.class);
                    t.putExtra("mmmprice", mmprice);
                    t.putExtra("uMob", mob);
                    t.putExtra("slot", slotNumber);
                    t.putExtra("modelName", modelname);
                    t.putExtra("sdate", d1);
                    t.putExtra("edate", d2);
                    t.putExtra("rMo", rMO);
                    t.putExtra("modelImageUrl", imageUrl);
                    t.putExtra("cType", mType);
                    t.putExtra("cCom", mCom);
                    t.putExtra("pPoint", pPoint);
                    t.putExtra("dri", dri);
                    t.putExtra("price", ans);
                    startActivity(t);
                } else {
                    Snackbar.make(button, "OTP not match !!!", Snackbar.LENGTH_LONG);
                    Toast.makeText(Demo2.this, "OTP not match !!!", Toast.LENGTH_SHORT).show();


                }
            }
        });

    }
}