package com.example.car_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.example.car_admin.PaymentHistory.MyButtonClickListener;
import com.example.car_admin.PaymentHistory.MySwipeHelper;
import com.example.car_admin.PaymentHistory.payAdapter;
import com.example.car_admin.PaymentHistory.payAdmin;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Payments extends AppCompatActivity {
    final private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Admin").child("Payment");
    private payAdapter adapter;
    private RecyclerView recyclerViewPopular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);
        recyclerViewPopular = findViewById(R.id.recyclerView1);

        recyclerViewPopular.setHasFixedSize(true);
        recyclerViewPopular.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        MySwipeHelper swipeHelper = new MySwipeHelper(this,recyclerViewPopular,400) {
            @Override
            public void instantiateMyButton(RecyclerView.ViewHolder viewHolder, List<MySwipeHelper.MyButton> buffer) {
                buffer.add(new MyButton(Payments.this,
                        "Delete",
                        50,
                        0,
                        Color.parseColor("#FF3c30"),
                        new MyButtonClickListener(){
                            @Override
                            public void onClick(int pos) {
                                Toast.makeText(Payments.this, "Delete click", Toast.LENGTH_SHORT).show();
                                deleteItem(pos);

                            }

                        }));
//
//                buffer.add(new MyButton(Payments.this,
//                        "Update",
//                        30,
//                        R.drawable.baseline_search_24,
//                        Color.parseColor("#FF9502"),
//                        new MyButtonClickListener(){
//                            @Override
//                            public void onClick(int pos) {
//                                Toast.makeText(Payments.this, "Update click", Toast.LENGTH_SHORT).show();
//                            }
//                        }));
            }
        };

        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<payAdmin> dataList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                            for (DataSnapshot dataSnapshot3 : dataSnapshot2.getChildren()) {
                                for (DataSnapshot dataSnapshot4 : dataSnapshot3.getChildren()) {
                                    for (DataSnapshot dataSnapshot5 : dataSnapshot4.getChildren()) {

                                        payAdmin dataClass = dataSnapshot5.getValue(payAdmin.class);
                                        dataList.add(dataClass);

                                    }
                                }
                            }
                        }
                    }
                }

                adapter = new payAdapter(Payments.this, dataList);
                recyclerViewPopular.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error, if any.
            }
        });

    }
    private void deleteItem(int position) {
        // Get the selected payAdmin item
        payAdmin selectedPayAdmin = adapter.getItem(position);

        // Get the key of the selected item in the database
        String key = selectedPayAdmin.getSlot(); // You should have a getKey() method in your payAdmin class
        String key2 = selectedPayAdmin.getCarModelName(); // You should have a getKey() method in your payAdmin class
        String key1 = selectedPayAdmin.getUserMobile(); // You should have a getKey() method in your payAdmin class

        // Remove the item from the database
        databaseReference.child("Income").child("Car_book").child("User").child(key1).child(key2).child("slot"+key).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(Payments.this, "Item deleted successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Payments.this, "Failed to delete item", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}