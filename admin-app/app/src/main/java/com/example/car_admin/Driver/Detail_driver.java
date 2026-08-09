package com.example.car_admin.Driver;

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
import com.example.car_admin.Approve_Deny_User;
import com.example.car_admin.Location_Of_Car.Live_Data_Of_Location;
import com.example.car_admin.R;
import com.example.car_admin.ReadWriteUserDetails1;
import com.google.firebase.database.DatabaseReference;

public class Detail_driver extends AppCompatActivity {

    private ImageModel_1_driver object;
    TextView textView;
    ImageView imageView;
    Button btnApprove,btnDeny,btnBlock,btnLoc;
    String mob;
    private DatabaseReference usersRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_driver);

        btnDeny = findViewById(R.id.btnDeny);
        btnApprove = findViewById(R.id.btnApprove);
        btnLoc = findViewById(R.id.loc);

        textView = findViewById(R.id.textView);
        imageView = findViewById(R.id.imageView);

        object = (ImageModel_1_driver) getIntent().getSerializableExtra("object");
        String imageUrl = object.getImageURLUser();
        Glide.with(Detail_driver.this).load(imageUrl).into(imageView);

        mob = object.getEmail();
        textView.setText(mob);

        String mob1 = object.getMobile_dr();


        btnLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Detail_driver.this, Live_Data_Of_Location.class);
                intent.putExtra("mob",mob1);
                startActivity(intent);
            }
        });

    }
}