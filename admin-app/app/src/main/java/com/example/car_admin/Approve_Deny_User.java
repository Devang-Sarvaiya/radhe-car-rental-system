package com.example.car_admin;

import static android.view.View.GONE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.car_admin.Location_Of_Car.Live_Data_Of_Location;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Approve_Deny_User extends AppCompatActivity {

    private ReadWriteUserDetails1 object;
    TextView textView;
    ImageView imageView;
    Button btnApprove,btnDeny,btnBlock,btnLoc;
    String mob;
    private DatabaseReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_deny_user);


        btnDeny = findViewById(R.id.btnDeny);
        btnApprove = findViewById(R.id.btnApprove);
        btnLoc = findViewById(R.id.loc);




//        usersRef = FirebaseDatabase.getInstance().getReference("Renters");
//
//        usersRef.orderByChild("mobile").equalTo("9000000008").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
//                    String imageUrl = userSnapshot.child("imageURLUser").getValue(String.class);
//
//                    Glide.with(Approve_Deny_User.this).load(imageUrl).into(imageView);
//                    name = userSnapshot.child("dll").getValue(String.class);
//                    name1 = userSnapshot.child("isVerified").getValue(String.class);
//
//                    // mob = userSnapshot.child("mobile").getValue(String.class);
//                }
//
//                if (name1 != null && name1.equals("true")) {
//                    btnDeny.setVisibility(GONE);
//                    btnApprove.setVisibility(GONE);
//                }
//
//                textsetText(name);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Handle the error, if any.
//            }
//        });
//
//        btnApprove.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                usersRef.child("9000000008").child("isVerified").setValue("true");
//                if (name1 != null && name1.equals("true")) {
//                    btnDeny.setVisibility(GONE);
//                    btnApprove.setVisibility(GONE);
//                }
//            }
//        });
//

        textView = findViewById(R.id.textView);
        imageView = findViewById(R.id.imageView);

        object = (ReadWriteUserDetails1) getIntent().getSerializableExtra("no");
        String imageUrl = object.getDlPic();
        Glide.with(Approve_Deny_User.this).load(imageUrl).into(imageView);

        mob = object.getDll();
        textView.setText(mob);
        String var = object.getIsVerified();

        if (var != null && var.equals("true")) {
            btnDeny.setEnabled(true);
            btnApprove.setEnabled(false);
            btnApprove.setText("Already Verified");

            usersRef = FirebaseDatabase.getInstance().getReference("Admin").child("Users");

            String mob1 = object.getMobile();
            btnDeny.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    usersRef.child(mob1).child("isVerified").setValue("false");

                    btnDeny.setEnabled(true);
                    btnDeny.setText("Denied");
                    btnApprove.setVisibility(GONE);
                    finish();
                }
            });

        }else{

            btnDeny.setEnabled(false);
            btnApprove.setEnabled(true);

            btnDeny.setText("Already Denied");

            usersRef = FirebaseDatabase.getInstance().getReference("Admin").child("Users");

            String mob1 = object.getMobile();
            btnApprove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    usersRef.child(mob1).child("isVerified").setValue("true");

                    btnApprove.setEnabled(false);
                    btnApprove.setText("Verfied");
                    btnDeny.setVisibility(GONE);
                    finish();

                }
            });

        }


        usersRef = FirebaseDatabase.getInstance().getReference("Admin").child("Users");

        String mob1 = object.getMobile();
//        btnBlock.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                usersRef.child(mob1).child("isVerified").setValue("false");
//                if (var != null && var.equals("false")) {
//                    btnDeny.setVisibility(View.VISIBLE);
//                    btnApprove.setVisibility(View.VISIBLE);
//                    finish();
//                }
//            }
//        });


        btnLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Approve_Deny_User.this, Live_Data_Of_Location.class);
                intent.putExtra("mob",mob1);
                startActivity(intent);
            }
        });

    }
}