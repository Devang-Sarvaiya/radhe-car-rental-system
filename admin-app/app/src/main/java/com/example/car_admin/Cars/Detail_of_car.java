package com.example.car_admin.Cars;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.car_admin.Driver.Connected_drivers;
import com.example.car_admin.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Detail_of_car extends AppCompatActivity {

    private ImageModel1 object;
    ImageView imgChesis, imgInsurance, imgRC;
    TextView txtChesis, txtInsurance, txtRC;
    Button btnVerify, btnDenied,btnConnect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_of_car);

        imgChesis = findViewById(R.id.imgChesis);
        imgInsurance = findViewById(R.id.imgInsurance);
        imgRC = findViewById(R.id.imgRC);
        txtChesis = findViewById(R.id.txtChesis);
        txtInsurance = findViewById(R.id.txtInsurance);
        txtRC = findViewById(R.id.txtRC);
        btnVerify = findViewById(R.id.btnVerify);
        btnDenied = findViewById(R.id.btnDenied);
        btnConnect = findViewById(R.id.btnConnect);
        object = (ImageModel1) getIntent().getSerializableExtra("object");


        String firebaseImageUrl = object.getChassisNumberImageURL();
        Glide.with(this)
                .load(firebaseImageUrl)
                .into(imgChesis);

        txtChesis.setText("Chassis Number");

        String firebaseImageUrl1 = object.getInsuranceImageURL();
        Glide.with(this)
                .load(firebaseImageUrl1)
                .into(imgInsurance);

        txtInsurance.setText("Insurance Number");
        String firebaseImageUrl2 = object.getRcBookImageURL();
        Glide.with(this)
                .load(firebaseImageUrl2)
                .into(imgRC);
        txtRC.setText("Rc book Number");

        if (object.getIsVerified().equals("true")) {

            btnVerify.setEnabled(false);
            btnDenied.setEnabled(true);
            btnDenied.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DatabaseReference db = FirebaseDatabase.getInstance().getReference("Admin").child("Car").child("General").child(object.getCarType()).child("Company").child(object.getCarCompany()).child(object.getModelName()).child("Details").child("isVerified");
                    db.setValue("false");
                    DatabaseReference db1 = FirebaseDatabase.getInstance().getReference("Admin").child("Car").child(object.getCarType()).child("Company").child(object.getCarCompany()).child(object.getModelName()).child("Details").child("isVerified");
                    db1.setValue("false");
                    DatabaseReference db2 = FirebaseDatabase.getInstance().getReference("Admin").child("Renters").child(object.getRenterMobile()).child("Car").child(object.getCarType()).child("Company").child(object.getCarCompany()).child(object.getModelName()).child("Details").child("isVerified");
                    db2.setValue("false");
                    btnDenied.setText("Denied");
                    btnVerify.setVisibility(View.GONE);
                    finish();
                }
            });

        } else {
            btnDenied.setEnabled(false);
            btnVerify.setEnabled(true);
            btnVerify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseReference db = FirebaseDatabase.getInstance().getReference("Admin").child("Car").child("General").child(object.getCarType()).child("Company").child(object.getCarCompany()).child(object.getModelName()).child("Details").child("isVerified");
                    db.setValue("true");
                    DatabaseReference db1 = FirebaseDatabase.getInstance().getReference("Admin").child("Car").child(object.getCarType()).child("Company").child(object.getCarCompany()).child(object.getModelName()).child("Details").child("isVerified");
                    db1.setValue("true");
                    DatabaseReference db2 = FirebaseDatabase.getInstance().getReference("Admin").child("Renters").child(object.getRenterMobile()).child("Car").child(object.getCarType()).child("Company").child(object.getCarCompany()).child(object.getModelName()).child("Details").child("isVerified");
                    db2.setValue("true");
                    btnVerify.setText("Verified");
                    btnDenied.setVisibility(View.GONE);
                    finish();
                }
            });
        }

btnConnect.setVisibility(View.GONE);
        if (object.getIsDriverConnectedWithCar().equals("true")) {
            btnConnect.setVisibility(View.GONE);
        }else {
            btnConnect.setVisibility(View.VISIBLE);
            btnConnect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Detail_of_car.this,Connected_drivers.class);
                    intent.putExtra("CarCom",object.getCarCompany());
                    intent.putExtra("CarType",object.getCarType());
                    intent.putExtra("CarName",object.getModelName());
                    intent.putExtra("CarRenterMobile",object.getRenterMobile());
                    intent.putExtra("CarImage",object.getImageURL());
                    startActivity(intent);
                }
            });
        }




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}