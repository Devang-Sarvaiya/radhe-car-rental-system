package com.example.caronrent.Tourism;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.caronrent.Add_car.C_ItemAdapter;
import com.example.caronrent.Add_car.ImageModel1;
import com.example.caronrent.DataClass;
import com.example.caronrent.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import com.example.caronrent.statusChanged;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Tourism_place_show extends AppCompatActivity {

    RecyclerView recyclerView;
    private DatabaseReference usersRef;
    ArrayList<TourClass> dataList;
    Tourism_adapter adapter;
    String eem, mob;
    SharedPreferences sharedPreferences;
    private DatabaseReference databaseReference;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tourism_place_show);
        fab = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        dataList = new ArrayList<>();
        adapter = new Tourism_adapter(dataList, this);
        recyclerView.setAdapter(adapter); // Set adapter first
        sharedPreferences = getSharedPreferences("email_1", MODE_PRIVATE);
        eem = sharedPreferences.getString("email", "AAA");

//        usersRef = FirebaseDatabase.getInstance().getReference("Admin").child("Renters");
//
//        usersRef.orderByChild("email").equalTo(eem).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
//                    mob = userSnapshot.child("mobile").getValue(String.class);
//                }
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Handle the error, if any.
//            }
//        });
//
//

        databaseReference = FirebaseDatabase.getInstance().getReference("Admin").child("Tourism_Place");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear(); // Clear previous data
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        TourClass dataClass = dataSnapshot1.getValue(TourClass.class);
                        dataList.add(dataClass);
                    }
                }
                adapter.notifyDataSetChanged(); // Notify adapter of dataset changes
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error, if any.
            }
        });


//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
//                    ImageModel1 dataClass = dataSnapshot.getValue(ImageModel1.class);
//                    dataList.add(dataClass);
//                }
//                adapter.notifyDataSetChanged();
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Tourism_place_show.this, UploadActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
    }
}