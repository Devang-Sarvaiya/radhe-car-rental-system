package com.example.caronrentrenter.Verification;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.caronrentrenter.Live_location.Location_info;
import com.example.caronrentrenter.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class verify_your_self extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    String eem,mob,isVar;
    Button btnApply,btn_back;
    TextView txt_verify;
    private DatabaseReference usersRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_verify_your_self);

        btnApply = findViewById(R.id.btnApply);
        btn_back = findViewById(R.id.btn_back);
        txt_verify = findViewById(R.id.txt_verify);

        sharedPreferences =getSharedPreferences("email_1", MODE_PRIVATE); ;

        eem = sharedPreferences.getString("email","AAA");






        usersRef = FirebaseDatabase.getInstance().getReference("Admin").child("Users");

        usersRef.orderByChild("email").equalTo(eem).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
//                        name = userSnapshot.child("name").getValue(String.class);
                        mob = userSnapshot.child("mobile").getValue(String.class);
                        isVar = userSnapshot.child("isVerified").getValue(String.class);
                }
                    if (isVar.equals(true)){
                        txt_verify.setText("You already verified by admin");
                        btnApply.setEnabled(false);
                    }else{
                        txt_verify.setText("Wait for verification");
                    }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle the error, if any.
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}