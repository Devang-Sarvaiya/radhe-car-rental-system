package com.example.car_admin.Tourism;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.car_admin.R;
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