package com.example.caronrent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.caronrent.Live_location.Location_info;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class driver_detail extends AppCompatActivity {

    ImageView img_Profile, img_car;
    TextView txt_unav, txtUname, txtCarName;
    Button btnLive, btnBookHistory,btn;

    SharedPreferences sharedPreferences;
    private DatabaseReference usersRef;
    String emailShare, name, email, eem, mob, gender, city, dl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_driver_detail);

        img_Profile = findViewById(R.id.img_Profile);

        img_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(driver_detail.this, Location_info.class));
            }
        });
        img_car = findViewById(R.id.img_car);
        txt_unav = findViewById(R.id.txt_unav);
        txtUname = findViewById(R.id.txtUname);
        txtCarName = findViewById(R.id.txtCarName);
        btnLive = findViewById(R.id.btnLive);
        btnBookHistory = findViewById(R.id.btnBookHistory);
        btn = findViewById(R.id.button3);

        sharedPreferences = getSharedPreferences("email_1", MODE_PRIVATE);
        eem = sharedPreferences.getString("email", "AAA");
        usersRef = FirebaseDatabase.getInstance().getReference("Admin").child("Drivers").child("General");

        // Retrieve the user's image URL
        usersRef.orderByChild("email").equalTo(eem).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    mob = userSnapshot.child("mobile_dr").getValue(String.class);
                }



                DatabaseReference db = FirebaseDatabase.getInstance().getReference("Admin").child("Drivers").child("General").child(mob);
                db.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String imageUrl = dataSnapshot.child("imageURLUser").getValue(String.class);
                        if (!isDestroyed()) {
                            Glide.with(driver_detail.this)
                                    .load(imageUrl)
                                    .into(img_Profile);
                        }
                        String imageUri1 = dataSnapshot.child("carImage").getValue(String.class);
                        Glide.with(driver_detail.this)
                                .load(imageUri1)
                                .into(img_car);

                        txtUname.setText(dataSnapshot.child("name").getValue(String.class));
                        txtCarName.setText(dataSnapshot.child("carName").getValue(String.class));
                        String cName = dataSnapshot.child("carName").getValue(String.class);
                        String cType = dataSnapshot.child("carType").getValue(String.class);
                        String cCom = dataSnapshot.child("carCompany").getValue(String.class);
                        String unav = dataSnapshot.child("isUnAvailable").getValue(String.class);
                        if (unav.equals("false")) {
                            txt_unav.setText("Available");
                        } else {
                            txt_unav.setText("UnAvailable");
                        }

                        String rMob = dataSnapshot.child("carRenterMobile").getValue(String.class);
                        txt_unav.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(driver_detail.this, statusChanged.class);
                                intent.putExtra("rMob", rMob);
                                intent.putExtra("mName", cName);
                                intent.putExtra("mType", cType);
                                intent.putExtra("mCom", cCom);
                                intent.putExtra("unav", unav);
                                intent.putExtra("dMob", mob);
                                startActivity(intent);
                            }
                        });


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle the error, if any.
                    }
                });
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle data retrieval failure
            }
        });

        btnLive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(driver_detail.this, Location_info.class);
                intent.putExtra("Mob", mob);
                startActivity(intent);
            }
        });
        btnBookHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(driver_detail.this, Location_info.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); // or Intent.FLAG_ACTIVITY_CLEAR_TOP
                intent.putExtra("Mob", "9000000007");
                startActivity(intent);

//                finish();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(driver_detail.this, Location_info.class);
                intent.putExtra("Mob", "9000000007");
                startActivity(intent);
            }
        });
    }
}
