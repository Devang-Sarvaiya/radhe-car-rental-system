package com.example.car_admin.Cars;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.car_admin.All_cars;
import com.example.car_admin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class car_list_mode extends AppCompatActivity {

    LinearLayout add_car;
    ConstraintLayout show_car;
    String na;
    private final DatabaseReference databaseReference_High = FirebaseDatabase.getInstance().getReference("Admin").child("Car").child("General");
    private C_ItemAdapter adapter;
    private ArrayList<ImageModel1> dataList = new ArrayList<>(); // Maintain a list of fetched items
    private int previousItemCount = 0; // Variable to store the previous count of items

//    TextView txt_Notify;
    ImageView txt_Notify;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_car_list_mode);

        add_car = findViewById(R.id.add_car);
        show_car = findViewById(R.id.show_car);
        txt_Notify = findViewById(R.id.txt_Notify);


        add_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(car_list_mode.this, All_cars.class);

                startActivity(intent);
            }
        });

        show_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(car_list_mode.this, All_cars1.class);
                startActivity(intent);
            }
        });


        databaseReference_High.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                ArrayList<ImageModel1> dataList = new ArrayList<>();
                ArrayList<ImageModel1> newDataList = new ArrayList<>();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                            for (DataSnapshot dataSnapshot3 : dataSnapshot2.getChildren()) {
                                for (DataSnapshot dataSnapshot4 : dataSnapshot3.getChildren()) {
                                    ImageModel1 dataClass = dataSnapshot4.getValue(ImageModel1.class);
                                    if (dataClass.getIsVerified().equals("false")) {
                                        newDataList.add(dataClass);
                                    }
                                }
                            }
                        }
                    }
                }

                // Count the differences between the new data and the previous data
                int newItemCount = newDataList.size() - previousItemCount;

                // Check if any new items added or changes made
                if (newItemCount > 0) {
//                    txt_Notify.setText(String.valueOf(newItemCount));
                        txt_Notify.setImageResource(R.drawable.bell2);
                    Toast.makeText(car_list_mode.this, newItemCount + " new item(s) added", Toast.LENGTH_SHORT).show();
                } else if (newItemCount < 0) {

                    Toast.makeText(car_list_mode.this, "Some item(s) changed", Toast.LENGTH_SHORT).show();
                }

                // Update the previousItemCount
                previousItemCount = newDataList.size();

                // Update the dataList with the latest fetched data
                dataList.clear();
                dataList.addAll(newDataList);

                // Initialize the adapter if null or update the existing one
                if (adapter == null) {
                    adapter = new C_ItemAdapter(car_list_mode.this, dataList);
//                    recyclerViewPopular.setAdapter(adapter);
                } else {
                    adapter.notifyDataSetChanged();
                }

//                shimmerFrameLayout.stopShimmer();
//                shimmerFrameLayout.setVisibility(View.GONE);
//                recyclerViewPopular.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error, if any.
            }
        });



    }
    @Override
    protected void onResume() {
        super.onResume();
        // Set the value of txt_Notify to zero
        txt_Notify.setImageResource(R.drawable.bell);

    }
}