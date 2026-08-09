package com.example.caronrent.Add_car;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caronrent.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class show_car extends AppCompatActivity {

    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "emailShare";
    SharedPreferences sharedPreferences;
    String emailShare, ema;
    String mob;
    TextView txtUname;
    SearchView searchView;
    private DatabaseReference usersRef;
    private C_ItemAdapter adapter;
    private ArrayList<ImageModel1> dataList;
    private DatabaseReference databaseReference_High;
    private RecyclerView recyclerViewPopular;
    private ShimmerFrameLayout shimmerFrameLayout;

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Admin").child("Renters");
    private DatabaseReference databaseReference_car_general = FirebaseDatabase.getInstance().getReference("Admin").child("Car").child("General");
    private DatabaseReference databaseReference_car = FirebaseDatabase.getInstance().getReference("Admin").child("Car");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_driver);

        searchView = findViewById(R.id.search);
        searchView.clearFocus();
        txtUname = findViewById(R.id.txtUname);
        recyclerViewPopular = findViewById(R.id.recyclerView_driver);
        shimmerFrameLayout = findViewById(R.id.shimmer_e_com);

        // Fetch user details and set the profile image using Glide
        // Uncomment and adapt the code below according to your requirements

        recyclerViewPopular.setHasFixedSize(true);
        recyclerViewPopular.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        MySwipeHelper swipeHelper = new MySwipeHelper(this, recyclerViewPopular, 400) {
            @Override
            public void instantiateMyButton(RecyclerView.ViewHolder viewHolder, List<MyButton> buffer) {
                buffer.add(new MyButton(show_car.this,
                        "Delete",
                        50,
                        0,
                        Color.parseColor("#FF3c30"),
                        new MyButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
//                                Toast.makeText(show_car.this, "Delete click", Toast.LENGTH_SHORT).show();
                                deleteItem(pos);

                            }

                        }));

                buffer.add(new MyButton(show_car.this,
                        "Update",
                        30,
                        R.drawable.baseline_search_24,
                        Color.parseColor("#FF9502"),
                        new MyButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                Toast.makeText(show_car.this, "Update click", Toast.LENGTH_SHORT).show();
                            }
                        }));
            }
        };
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        emailShare = sharedPreferences.getString(KEY_NAME, null);


        Intent in = getIntent();
        String na = in.getStringExtra("em");

        usersRef = FirebaseDatabase.getInstance().getReference("Admin").child("Renters");

        usersRef.orderByChild("email").equalTo(na).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    mob = userSnapshot.child("mobile").getValue(String.class);

                }

                databaseReference_High = FirebaseDatabase.getInstance().getReference("Admin").child("Renters").child(mob).child("Car");
                // Fetch popular cars from the database
                databaseReference_High.addValueEventListener(new ValueEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        dataList = new ArrayList<>();

                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                                    for (DataSnapshot dataSnapshot3 : dataSnapshot2.getChildren()) {
                                        for (DataSnapshot dataSnapshot4 : dataSnapshot3.getChildren()) {
                                            ImageModel1 dataClass = dataSnapshot4.getValue(ImageModel1.class);
                                            dataList.add(dataClass);
                                        }
                                    }
                                }
                            }
                        }

                        // Initialize the adapter before using it
                        adapter = new C_ItemAdapter(show_car.this, dataList);
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


            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle the error, if any.
            }
        });


//         Set up RecyclerView for popular cars
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
        ArrayList<ImageModel1> searchList = new ArrayList<>();
        for (ImageModel1 dataClass : dataList) {
            if (dataClass.getModelName().toLowerCase().contains(text.toLowerCase())) {
                searchList.add(dataClass);
            }
        }
        adapter.searchdatalist(searchList);
    }
    private void deleteItem(int position) {
        // Get the selected payAdmin item
        ImageModel1 selectedPayAdmin = adapter.getItem(position);

        // Get the key of the selected item in the database
        String key = selectedPayAdmin.getRenterMobile(); // You should have a getKey() method in your payAdmin class
        String key2 = selectedPayAdmin.getCarType(); // You should have a getKey() method in your payAdmin class
        String key1 = selectedPayAdmin.getCarCompany(); // You should have a getKey() method in your payAdmin class
        String key3 = selectedPayAdmin.getModelName(); // You should have a getKey() method in your payAdmin class

        // Remove the item from the database
        databaseReference.child(key).child("Car").child(key2).child("Company").child(key1).child(key3).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(show_car.this,key3+" deleted successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(show_car.this, "Failed to delete "+key3, Toast.LENGTH_SHORT).show();
                    }
                });

        databaseReference_car_general.child(key2).child("Company").child(key1).child(key3).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
//                        Toast.makeText(show_car.this, key3+" deleted successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(show_car.this, "Failed to delete "+key3, Toast.LENGTH_SHORT).show();
                    }
                });

        databaseReference_car.child(key2).child("Company").child(key1).child(key3).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //Toast.makeText(show_car.this, key3+" deleted successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(show_car.this, "Failed to delete "+key3, Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
