package com.example.car_admin.Driver;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.car_admin.Drivers;
import com.example.car_admin.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Connected_drivers extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "emailShare";
    private DatabaseReference usersRef;
    String emailShare, ema;
    String mob,CarName,CarType,CarCom,rMOB,carImage;
    TextView txtUname;
    private D_itemAdapter1 adapter;
    private final DatabaseReference databaseReference_High = FirebaseDatabase.getInstance().getReference("Admin").child("Drivers").child("General");
    private RecyclerView recyclerViewPopular;
    private ShimmerFrameLayout shimmerFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_connected_drivers);

//        txtUname = findViewById(R.id.txtUname);
        recyclerViewPopular = findViewById(R.id.recyclerView_driver);
        shimmerFrameLayout = findViewById(R.id.shimmer_e_com);

        // Fetch user details and set the profile image using Glide
        // Uncomment and adapt the code below according to your requirements

        recyclerViewPopular.setHasFixedSize(true);
        recyclerViewPopular.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        emailShare = sharedPreferences.getString(KEY_NAME, null);


        Intent intent = getIntent();
        CarCom = intent.getStringExtra("CarCom");
        CarName = intent.getStringExtra("CarName");
        CarType = intent.getStringExtra("CarType");
        rMOB = intent.getStringExtra("CarRenterMobile");
        carImage = intent.getStringExtra("CarImage");

//        Intent in = getIntent();
//        in.putExtra("CarName", CarName);
//        in.putExtra("CarCom",CarCom);
//        in.putExtra("CarType", CarType);
//        in.putExtra("rMob", rMOB);
//        startActivity(in);




        // Fetch popular cars from the database
        databaseReference_High.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<ImageModel_1_driver> dataList = new ArrayList<>();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ImageModel_1_driver dataClass = dataSnapshot.getValue(ImageModel_1_driver.class);
                    if(dataClass.getIsCoonectedWithCar().equals("false")){
                        dataList.add(dataClass);
                    }
                }

                // Initialize the adapter before using it
                adapter = new D_itemAdapter1(Connected_drivers.this, dataList, CarName, CarCom, CarType, rMOB,carImage);
                recyclerViewPopular.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                recyclerViewPopular.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error, if any.
            }
        });
    }


}

