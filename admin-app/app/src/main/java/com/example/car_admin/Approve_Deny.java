package com.example.car_admin;

import static android.view.View.GONE;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Approve_Deny extends AppCompatActivity {

    private ReadWriteUserDetails object;
    TextView textView;
    ImageView imageView;
    Button btnApprove,btnDeny;
    String mob;
    private DatabaseReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_deny);



//        imageView = findViewById(R.id.imageView);
//        textView = findViewById(R.id.textView);
        btnDeny = findViewById(R.id.btnDeny);
        btnApprove = findViewById(R.id.btnApprove);




//        usersRef = FirebaseDatabase.getInstance().getReference("Renters");
//
//        usersRef.orderByChild("mobile").equalTo("9000000008").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
//                    String imageUrl = userSnapshot.child("imageURLUser").getValue(String.class);
//
//                    Glide.with(Approve_Deny.this).load(imageUrl).into(imageView);
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

        object = (ReadWriteUserDetails) getIntent().getSerializableExtra("no");
        String imageUrl = object.getAadhaar_front();
        Glide.with(Approve_Deny.this).load(imageUrl).into(imageView);

        mob = object.getAadhaar_card_number();
        textView.setText(mob);
        String var = object.getIsVerified();

        if (var != null && var.equals("true")) {
            btnDeny.setEnabled(true);
            btnApprove.setEnabled(false);
            btnApprove.setText("Already Verified");

            usersRef = FirebaseDatabase.getInstance().getReference("Admin").child("Renters");

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

            usersRef = FirebaseDatabase.getInstance().getReference("Admin").child("Renters");

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

    }
}