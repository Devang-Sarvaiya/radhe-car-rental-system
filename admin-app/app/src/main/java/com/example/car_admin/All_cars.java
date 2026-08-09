package com.example.car_admin;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.example.car_admin.Cars.C_ItemAdapter;
import com.example.car_admin.Cars.ImageModel1;
import com.example.car_admin.PaymentHistory.MyButtonClickListener;
import com.example.car_admin.PaymentHistory.MySwipeHelper;
import com.example.car_admin.PaymentHistory.payAdmin;
import com.example.car_admin.R;
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


public class All_cars extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "emailShare";
    String emailShare, name, isVer, mob;

    private DatabaseReference usersRef;
    TextView txtUname;
    private C_ItemAdapter adapter;
    private final DatabaseReference databaseReference_High = FirebaseDatabase.getInstance().getReference("Admin").child("Car").child("General");
    private RecyclerView recyclerViewPopular;
    private ShimmerFrameLayout shimmerFrameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_cars);


        recyclerViewPopular = findViewById(R.id.recyclerView_e_com);
        shimmerFrameLayout = findViewById(R.id.shimmer_e_com);




        MySwipeHelper swipeHelper = new MySwipeHelper(this,recyclerViewPopular,400) {
            @Override
            public void instantiateMyButton(RecyclerView.ViewHolder viewHolder, List<MyButton> buffer) {
                buffer.add(new MyButton(All_cars.this,
                        "Delete",
                        50,
                        0,
                        Color.parseColor("#FF3c30"),
                        new MyButtonClickListener(){
                            @Override
                            public void onClick(int pos) {
                                Toast.makeText(All_cars.this, "Delete click", Toast.LENGTH_SHORT).show();
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

        // Fetch user details and set the profile image using Glide
        // Uncomment and adapt the code below according to your requirements


        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        emailShare = sharedPreferences.getString(KEY_NAME, null);

        usersRef = FirebaseDatabase.getInstance().getReference("Admin").child("Renters");


        usersRef.orderByChild("email").equalTo(emailShare).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    //                    Glide.with(Car_item_add.this).load(imageUrl).into(showProfilePic);

                    mob = userSnapshot.child("mobile").getValue(String.class);

                    //                    usern = userSnapshot.child("name").getValue(String.class);
                    //                    userName = userSnapshot.child("username").getValue(String.class);
                    //                    userEmail = userSnapshot.child("email").getValue(String.class);
                    //                    userPassword = userSnapshot.child("password").getValue(String.class);
                    //
                    //                    role = userSnapshot.child("role").getValue(String.class);

                    // Now you can use the imageUrl in your app, e.g., to load the image using an image loading library like Glide or Picasso.
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle the error, if any.
            }
        });


        // Set up RecyclerView for popular cars
        recyclerViewPopular.setHasFixedSize(true);
        recyclerViewPopular.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        // Fetch popular cars from the database
        databaseReference_High.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<ImageModel1> dataList = new ArrayList<>();


                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                            for (DataSnapshot dataSnapshot3 : dataSnapshot2.getChildren()) {
                                for (DataSnapshot dataSnapshot4 : dataSnapshot3.getChildren()) {
                                    ImageModel1 dataClass = dataSnapshot4.getValue(ImageModel1.class);
                                    if (dataClass.getIsVerified().equals("true")) {
                                        dataList.add(dataClass);
                                    }
                                }
                            }
                        }
                    }
                }

                // Initialize the adapter before using it
                adapter = new C_ItemAdapter(All_cars.this, dataList);
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




    private void deleteItem(int position) {
        // Get the selected ImageModel1 item
        ImageModel1 selectedImage = adapter.getItem(position);

        if (selectedImage != null) {
            String carType = selectedImage.getCarType();
            String carCompany = selectedImage.getCarCompany();
            String modelName = selectedImage.getModelName();
            String renterMobile = selectedImage.getRenterMobile();

            DatabaseReference carRef = FirebaseDatabase.getInstance().getReference("Admin").child("Car").child("General").child(carType).child(carCompany);
            carRef.child(modelName).removeValue()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(All_cars.this, "Car deleted successfully", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(All_cars.this, "Failed to delete Car", Toast.LENGTH_SHORT).show();
                        }
                    });

            DatabaseReference renterCarRef = FirebaseDatabase.getInstance().getReference("Admin").child("Renters").child(renterMobile).child("Car").child(carType).child(carCompany);
            renterCarRef.child(modelName).removeValue()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            // No need to show Toast here, as it's already shown for the car deletion
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(All_cars.this, "Failed to delete Car", Toast.LENGTH_SHORT).show();
                        }
                    });

            DatabaseReference carCompanyRef = FirebaseDatabase.getInstance().getReference("Admin").child("Car").child(carType).child("Company").child(carCompany);
            carCompanyRef.child(modelName).removeValue()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            // No need to show Toast here, as it's already shown for the car deletion
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(All_cars.this, "Failed to delete Car", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

//    private void deleteItem(int position) {
//        // Get the selected payAdmin item
//        ImageModel1 selectedPayAdmin = adapter.getItem(position);
//
//        // Get the key of the selected item in the database
//        String key = selectedPayAdmin.getCarType(); // You should have a getKey() method in your payAdmin class
//        String key2 = selectedPayAdmin.getCarCompany(); // You should have a getKey() method in your payAdmin class
//        String key1 = selectedPayAdmin.getModelName(); // You should have a getKey() method in your payAdmin class
//
//        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Admin").child("Car").child("General").child(key).child("Company").child(key2);
//        // Remove the item from the database
//        db.child(key1).removeValue()
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        Toast.makeText(All_cars.this, "Car deleted successfully", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(All_cars.this, "Failed to delete Car", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//
//        DatabaseReference db1 = FirebaseDatabase.getInstance().getReference("Admin").child("Car").child(key).child("Company").child(key2);
//        // Remove the item from the database
//        db1.child(key1).removeValue()
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        Toast.makeText(All_cars.this, "Car deleted successfully", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(All_cars.this, "Failed to delete Car", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//
////        String key = selectedPayAdmin.getCarType(); // You should have a getKey() method in your payAdmin class
////        String key2 = selectedPayAdmin.getCarCompany(); // You should have a getKey() method in your payAdmin class
////        String key1 = selectedPayAdmin.getModelName();
//        String key3 = selectedPayAdmin.getRenterMobile();
//        DatabaseReference db2 = FirebaseDatabase.getInstance().getReference("Admin").child("Renters").child(key3).child("Car").child(key).child("Company").child(key2);
//        // Remove the item from the database
//        db2.child(key1).removeValue()
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        Toast.makeText(All_cars.this, "Car deleted successfully", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(All_cars.this, "Failed to delete Car", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//
//    }
}
