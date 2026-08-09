package com.example.caronrent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.caronrent.Adapter.ItemAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "emailShare";
    String emailShare,name;

    private DatabaseReference usersRef;
    TextView txtUname;
    ImageView img_Profile;


    private ItemAdapter adapter;
    final private DatabaseReference databaseReference_High = FirebaseDatabase.getInstance().getReference("Admin").child("Car").child("General");
    private RecyclerView recyclerViewPopular, recyclerViewNew;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        emailShare = sharedPreferences.getString(KEY_NAME, null);
        txtUname = findViewById(R.id.txtUname);

        img_Profile = findViewById(R.id.img_Profile);

//        txtUname.setText(emailShare);

        img_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Profile_Ui.class);
                startActivity(intent);
            }
        });


        String desiredUsername = emailShare;

        usersRef = FirebaseDatabase.getInstance().getReference("users");

        // Retrieve the user's image URL
        usersRef.orderByChild("email").equalTo(desiredUsername).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {

                    String imageUrl = userSnapshot.child("imageURLUser").getValue(String.class);

                    Glide.with(MainActivity.this).load(imageUrl).into(img_Profile);

                    name = userSnapshot.child("name").getValue(String.class);
//                    email = userSnapshot.child("email").getValue(String.class);
//                    pass = userSnapshot.child("pass").getValue(String.class);
//                    mob = userSnapshot.child("mobile").getValue(String.class);
//                    city = userSnapshot.child("city").getValue(String.class);
//                    driving = userSnapshot.child("dll").getValue(String.class);
//                    gender = userSnapshot.child("gender").getValue(String.class);


//                    role = userSnapshot.child("role").getValue(String.class);

                    // Now you can use the imageUrl in your app, e.g., to load the image using an image loading library like Glide or Picasso.
                }


                txtUname.setText(name);
//                editPassword.setText(pass);
//                editMobile.setText(mob);
//                editCity.setText(city);
//                editDriving.setText(driving);
//                editGender.setText(gender);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle the error, if any.
            }
        });

        recyclerViewPopular = findViewById(R.id.viewPopular);
        recyclerViewPopular.setHasFixedSize(true);
        recyclerViewPopular.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        databaseReference_High.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ArrayList<DataClass> dataList = new ArrayList<>();
                //for each lagavvu
                // snapnot.getChildern()


                snapshot.getChildren();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        for (DataSnapshot dataSnapshot2: dataSnapshot1.getChildren()) {
                            for (DataSnapshot dataSnapshot3: dataSnapshot2.getChildren()) {
                                DataClass dataClass = dataSnapshot3.getValue(DataClass.class);
                                dataList.add(dataClass);
                            }
                        }
                    }
                }
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//
//                        DataClass dataClass = dataSnapshot.getValue(DataClass.class);
//                        dataList.add(dataClass);
//
//
//                }
                adapter = new ItemAdapter(MainActivity.this, dataList);
//                recyclerViewPopular.setAdapter(adapter);
//                recyclerViewNew.setAdapter(adapter);


                recyclerViewPopular.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


    }
}