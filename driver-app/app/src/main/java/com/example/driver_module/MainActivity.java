package com.example.driver_module;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.bumptech.glide.Glide;
import com.example.driver_module.BookHistory.Book_History_show;
import com.example.driver_module.Live_location.Location_info;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    ImageView img_Profile, img_car;
    TextView txt_unav, txtUname, txtCarName;
    AppCompatButton btnLive, btnBookHistory,btn;

    SharedPreferences sharedPreferences;
    private DatabaseReference usersRef;
    String rMob,cName;
    String emailShare, name, email, eem, mob, gender, city, dl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        img_Profile = findViewById(R.id.img_Profile);
//
//        img_Profile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this,Location_info.class));
//            }
//        });
        img_car = findViewById(R.id.img_car);
        txt_unav = findViewById(R.id.txt_unav);
        txtUname = findViewById(R.id.txtUname);
        txtCarName = findViewById(R.id.txtCarName);
        btnLive = findViewById(R.id.btnLive);
        btnBookHistory = findViewById(R.id.btnBookHistory);
//        btn = findViewById(R.id.button3);

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
                            Glide.with(MainActivity.this)
                                    .load(imageUrl)
                                    .into(img_Profile);
                        }
                        String imageUri1 = dataSnapshot.child("carImage").getValue(String.class);
                        Glide.with(MainActivity.this)
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

                         rMob = dataSnapshot.child("carRenterMobile").getValue(String.class);
                        txt_unav.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(MainActivity.this, statusChanged.class);
                                intent.putExtra("rMob", rMob);
                                intent.putExtra("mName", cName);
                                intent.putExtra("mType", cType);
                                intent.putExtra("mCom", cCom);
                                intent.putExtra("unav", unav);
                                intent.putExtra("dMob", mob);
                                startActivity(intent);
                            }
                        });

                        btnLive.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(MainActivity.this, Location_info.class);
                                intent.putExtra("Mob", mob);
                                startActivity(intent);
                            }
                        });
                        btnBookHistory.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(MainActivity.this, Book_History_show.class);
                                intent.putExtra("rMob", rMob);
                                intent.putExtra("mName", cName);
                                startActivity(intent);

//                finish();
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


//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, Location_info.class);
//                intent.putExtra("Mob", "9000000007");
//                startActivity(intent);
//            }
//        });
    }
}
