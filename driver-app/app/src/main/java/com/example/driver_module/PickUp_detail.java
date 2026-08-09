package com.example.driver_module;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.driver_module.BookHistory.Book_History_show;
import com.example.driver_module.BookHistory.bookAdapter;
import com.example.driver_module.BookHistory.bookAdmin;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PickUp_detail extends AppCompatActivity {

    private RecyclerView recyclerViewPopular;

    private final DatabaseReference databaseReference_High = FirebaseDatabase.getInstance().getReference("Admin").child("Booking").child("User");
    private bookAdapter adapter;
    String pickup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pick_up_detail);

        Intent in=getIntent();
        pickup= in.getStringExtra("pick");


        recyclerViewPopular = findViewById(R.id.recyclerView);

        recyclerViewPopular.setHasFixedSize(true);
        recyclerViewPopular.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        databaseReference_High.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<bookAdmin> dataList = new ArrayList<>();

//                shimmerFrameLayout.startShimmer();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                            bookAdmin dataClass = dataSnapshot2.getValue(bookAdmin.class);
                            if (dataClass.getPickupPoint().equals(pickup) && dataClass.getIsCarReceived().equals("false")) {
                                dataList.add(dataClass);
                            }
                        }
                    }
                }
                adapter = new bookAdapter(PickUp_detail.this, dataList);
                recyclerViewPopular.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                recyclerViewPopular.setVisibility(View.VISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
}