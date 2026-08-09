package com.example.car_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.example.car_admin.BookHistory.bookAdapter;
import com.example.car_admin.BookHistory.bookAdmin;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Booked_cars extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "emailShare";
    String emailShare, name, isVer, mob;

    private DatabaseReference usersRef;
    TextView txtUname;
    private bookAdapter adapter;
    private DatabaseReference databaseReference;
    private RecyclerView recyclerViewPopular;
    private ShimmerFrameLayout shimmerFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked_cars);
        recyclerViewPopular = findViewById(R.id.recyclerView1);

        recyclerViewPopular.setHasFixedSize(true);
        recyclerViewPopular.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        Intent in = getIntent();
        String mob = in.getStringExtra("uMob");
        databaseReference = FirebaseDatabase.getInstance().getReference("Admin").child("Booking").child("User");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<bookAdmin> dataList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                            bookAdmin dataClass = dataSnapshot2.getValue(bookAdmin.class);
                            dataList.add(dataClass);
                        }
                    }
                }

                adapter = new bookAdapter(Booked_cars.this, dataList);
                recyclerViewPopular.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error, if any.
            }
        });


    }
}
