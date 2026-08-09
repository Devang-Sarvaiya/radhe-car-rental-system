package com.example.car_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference usersRef;
    ImageView imageView;
    String name,name1;
    Button btnDeny, btnApprove;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);
        btnDeny = findViewById(R.id.btnDeny);
        btnApprove = findViewById(R.id.btnApprove);




        usersRef = FirebaseDatabase.getInstance().getReference("Renters");

        usersRef.orderByChild("mobile").equalTo("9000000008").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String imageUrl = userSnapshot.child("imageURLUser").getValue(String.class);

                    Glide.with(MainActivity.this).load(imageUrl).into(imageView);
                    name = userSnapshot.child("dll").getValue(String.class);
                    name1 = userSnapshot.child("isVerified").getValue(String.class);

//                    mob = userSnapshot.child("mobile").getValue(String.class);
                }


                if (name1.equals("true")) {
                    btnDeny.setVisibility(View.GONE);
                    btnApprove.setVisibility(View.GONE);
                }

                textView.setText(name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle the error, if any.
            }
        });



        btnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usersRef.child("9000000008").child("isVerified").setValue("true");
                if (name1.equals("true")) {
                    btnDeny.setVisibility(View.GONE);
                    btnApprove.setVisibility(View.GONE);
                }
            }
        });


    }
}