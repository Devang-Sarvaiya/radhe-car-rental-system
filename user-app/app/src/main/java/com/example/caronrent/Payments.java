package com.example.caronrent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.caronrent.BookingHistory.bookAdapter;
import com.example.caronrent.BookingHistory.bookAdmin;
import com.example.caronrent.BookingHistory.bookHistory;
import com.example.caronrent.PaymentHistory.MyButtonClickListener;
import com.example.caronrent.PaymentHistory.MySwipeHelper;
import com.example.caronrent.PaymentHistory.payAdapter;
import com.example.caronrent.PaymentHistory.payAdmin;
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
    private payAdapter adapter;
    private RecyclerView recyclerViewPopular;
    SharedPreferences sharedPreferences;
    SearchView searchView;
    private ArrayList<payAdmin> dataList;
    String emailShare, name, isVer, mob,eem,mName;
    private DatabaseReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);
        searchView=findViewById(R.id.search);
        searchView.clearFocus();
        recyclerViewPopular = findViewById(R.id.recyclerView1);

        recyclerViewPopular.setHasFixedSize(true);
        recyclerViewPopular.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        MySwipeHelper swipeHelper = new MySwipeHelper(this, recyclerViewPopular, 400) {
            @Override
            public void instantiateMyButton(RecyclerView.ViewHolder viewHolder, List<MyButton> buffer) {
                buffer.add(new MyButton(Payments.this,
                        "Delete",
                        50,
                        0,
                        Color.parseColor("#FF3c30"),
                        new MyButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                Toast.makeText(Payments.this, "Delete click", Toast.LENGTH_SHORT).show();
                                deleteItem(pos);
                            }
                        }));
            }
        };



        sharedPreferences =getSharedPreferences("email_1", MODE_PRIVATE);
        eem = sharedPreferences.getString("email","AAA");
        usersRef = FirebaseDatabase.getInstance().getReference("Admin").child("Renters");

        usersRef.orderByChild("email").equalTo(eem).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    mob = userSnapshot.child("mobile").getValue(String.class);
                }

                System.out.println("******************************************************************************************" + mob);
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Admin").child("Payment");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                         dataList = new ArrayList<>();
//                        String mob = "9000000009"; // Mobile number to match

                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                                    for (DataSnapshot dataSnapshot3 : dataSnapshot2.getChildren()) {
                                        for (DataSnapshot dataSnapshot4 : dataSnapshot3.getChildren()) {
                                            for (DataSnapshot dataSnapshot5 : dataSnapshot4.getChildren()) {
                                                payAdmin dataClass = dataSnapshot5.getValue(payAdmin.class);

                                                // Check if the mobile number matches
                                                if (dataClass.getRenterMobile().equals(mob)) {
                                                    if(dataClass.getIsRequestCanceld().equals("false") && dataClass.getIsConfirmed().equals("false")){
                                                        dataList.add(dataClass); // Add to dataList
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        adapter = new payAdapter(Payments.this, dataList);
                        recyclerViewPopular.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        recyclerViewPopular.setVisibility(View.VISIBLE);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle the error, if any.
                    }
                });


            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle the error, if any.
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });

    }
    public void searchList(String text) {
        ArrayList<payAdmin> searchList = new ArrayList<>();
        for (payAdmin dataClass : dataList) {
            if (dataClass.getCarModelName().toLowerCase().contains(text.toLowerCase())) {
                searchList.add(dataClass);
            }
        }
        adapter.searchdatalist(searchList);
    }
    private void deleteItem(int position) {
        // Get the selected payAdmin item
        payAdmin selectedPayAdmin = adapter.getItem(position);

        // Get the key of the selected item in the database
        String key = selectedPayAdmin.getSlot();
        String key2 = selectedPayAdmin.getCarModelName();
        String key1 = selectedPayAdmin.getUserMobile();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Admin").child("Payment");
        // Remove the item from the database
        databaseReference.child("Income").child("Car_book").child("User").child(key1).child(key2).child("slot" + key).removeValue()
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