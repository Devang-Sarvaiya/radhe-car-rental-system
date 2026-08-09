package com.example.caronrent;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caronrent.Adapter.FavoriteAdapter;
import com.example.caronrent.Adapter.FavoriteAdapter;
import com.example.caronrent.Add_car.ImageModel1;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Favorite extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<ImageModel1> dataList;
    TextView txtdrj;
    private FavoriteAdapter adapter;
    String type, emailShare, mob,eem;

    private DatabaseReference usersRef;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "emailShare";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);


        recyclerView = findViewById(R.id.recyclerView1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataList = new ArrayList<>();
        sharedPreferences =getSharedPreferences("email_1", MODE_PRIVATE); ;

        eem = sharedPreferences.getString("email","AAA");
        String demo = eem;
        String desiredUsername = demo;

        usersRef = FirebaseDatabase.getInstance().getReference("Admin").child("Renters");

        // Retrieve the user's image URL
        usersRef.orderByChild("email").equalTo(desiredUsername).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String imageUrl = userSnapshot.child("imageURLUser").getValue(String.class);

//                    Glide.with(Favorite.this).load(imageUrl).into(img_profile_user);

//                    name = userSnapshot.child("name").getValue(String.class);
//                    email = userSnapshot.child("email").getValue(String.class);
//
//                    pass = userSnapshot.child("pass").getValue(String.class);
                    mob = userSnapshot.child("mobile").getValue(String.class);
//                    city = userSnapshot.child("city").getValue(String.class);
//                    dl = userSnapshot.child("dll").getValue(String.class);
//                    gender = userSnapshot.child("gender").getValue(String.class);
//

//                    role = userSnapshot.child("role").getValue(String.class);

                    // Now you can use the imageUrl in your app, e.g., to load the image using an image loading library like Glide or Picasso.
                }


                type = mob;

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Admin").child("Renters").child(type).child("Favorite");

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            ImageModel1 dataClass = dataSnapshot.getValue(ImageModel1.class);
                            dataList.add(dataClass);
                        }
                        adapter = new FavoriteAdapter(Favorite.this, dataList);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });


//                txtName.setText(name);
//                txtEmailUser.setText(email);
//                txtPassWord.setText(pass);
//                txtMo.setText(mob);
//                txtGend.setText(gender);
//                txtCit.setText(city);
//                txtDll.setText(dl);
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


//
//
//


//        BottomNavigationView nav;
//
//        nav = findViewById(R.id.nav);
//        nav.setSelectedItemId(R.id.fav);
//
//        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @SuppressLint("NonConstantResourceId")
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//                if (item.getItemId() == R.id.home) {
//                    startActivity(new Intent(getApplicationContext(), Home.class));
//                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                    finish();
//                    return true;
//
//                } else if (item.getItemId() == R.id.fav) {
//                    return true;
//
//                } else if (item.getItemId() == R.id.watch) {
//                    startActivity(new Intent(getApplicationContext(), Watch.class));
//                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                    finish();
//                    return true;
//                } else if (item.getItemId() == R.id.history) {
//                    startActivity(new Intent(getApplicationContext(), History.class));
//                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                    finish();
//                    return true;
//                } else if (item.getItemId() == R.id.setting) {
//                    startActivity(new Intent(getApplicationContext(), Settings.class));
//                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                    finish();
//                    return true;
//                }
//                return false;
//            }
//        });


        //if(ttl==0){
        //    FirebaseDatabase.getInstance().getReference().child("users").child(name).child("AddToCart").child(dataList.get(position).getTitle()).removeValue();


    }
}
