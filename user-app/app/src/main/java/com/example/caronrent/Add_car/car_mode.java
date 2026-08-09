package com.example.caronrent.Add_car;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.caronrent.CompleteProfile;
import com.example.caronrent.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class car_mode extends AppCompatActivity {

    LinearLayout add_car;
    ConstraintLayout show_car;
    String na;
    DatabaseReference usersRef2;
    SharedPreferences sharedPreferences;
    String eem,aadhar_no;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_mode);



        Intent in = getIntent();
         na = in.getStringExtra("na");

        add_car = findViewById(R.id.add_car);
        show_car = findViewById(R.id.show_car);

        usersRef2 = FirebaseDatabase.getInstance().getReference("Admin").child("Renters");
        sharedPreferences = getSharedPreferences("email_1", MODE_PRIVATE);

        eem = sharedPreferences.getString("email", "AAA");

        add_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                usersRef2.orderByChild("email").equalTo(eem).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                            aadhar_no = userSnapshot.child("aadhaar_card_number").getValue(String.class);
//                    isVer1 = userSnapshot.child("isVerified").getValue(String.class);

//                    if (isVer1.equals("false")) {
//                        isVerified.setVisibility(View.VISIBLE);
//                        showAlertDialog3();
//                    } else {
//                        isVerified.setVisibility(View.GONE);
//                    }
                            if (TextUtils.isEmpty(aadhar_no)) {
                                showAlertDialog5();
                            }
                            else
                            {
                                Intent intent = new Intent(car_mode.this, add_car.class);
                                intent.putExtra("em",na);
                                startActivity(intent);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }
        });

        show_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(car_mode.this, show_car.class);
                intent.putExtra("em",na);
                startActivity(intent);
            }
        });
    }
    private void showAlertDialog5() {
        new MaterialAlertDialogBuilder(this, R.style.AlertDialogTheme)
                .setTitle("Verification Alert")
                .setMessage("Please complete your profile")
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                       startActivity(new Intent(car_mode.this, CompleteProfile.class));
                        finish();
                    }
                })
                .setCancelable(false)
                .show();
    }
}