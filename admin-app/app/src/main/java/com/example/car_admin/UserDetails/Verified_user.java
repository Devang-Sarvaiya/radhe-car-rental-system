package com.example.car_admin.UserDetails;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.car_admin.PaymentHistory.MyButtonClickListener;
import com.example.car_admin.PaymentHistory.MySwipeHelper;
import com.example.car_admin.R;
import com.example.car_admin.ReadWriteUserDetails1;
import com.example.car_admin.UserAdapter;
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

public class Verified_user extends AppCompatActivity {


    private DatabaseReference usersRef;
    ImageView imageView;
    String name, name1;
    Button btnDeny, btnApprove;
    TextView textView;
    private ShimmerFrameLayout shimmerFrameLayout, shimmerFrameLayout1;
    private UserAdapter adapter1;


    private final DatabaseReference databaseReference_High1 = FirebaseDatabase.getInstance().getReference("Admin").child("Users");
//    private final DatabaseReference databaseReference_High2 = FirebaseDatabase.getInstance().getReference("Car").child("General");
//    private DatabaseReference usersRef;

    private RecyclerView recyclerViewNew;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_verified_user);

        recyclerViewNew = findViewById(R.id.recyclerView);

        recyclerViewNew.setHasFixedSize(true);
        recyclerViewNew.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        MySwipeHelper swipeHelper = new MySwipeHelper(this,recyclerViewNew,400) {
            @Override
            public void instantiateMyButton(RecyclerView.ViewHolder viewHolder, List<MyButton> buffer) {
                buffer.add(new MyButton(Verified_user.this,
                        "Delete",
                        50,
                        0,
                        Color.parseColor("#FF3c30"),
                        new MyButtonClickListener(){
                            @Override
                            public void onClick(int pos) {
                                Toast.makeText(Verified_user.this, "Delete click", Toast.LENGTH_SHORT).show();
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
        databaseReference_High1.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<ReadWriteUserDetails1> dataList1 = new ArrayList<>();

//                shimmerFrameLayout1.startShimmer();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    ReadWriteUserDetails1 dataClass = dataSnapshot.getValue(ReadWriteUserDetails1.class);
//                        databaseReference_High1.child(dataSnapshot.getKey()).setValue(dataClass);
                    if(dataClass.getIsVerified().equals("true")){
                        dataList1.add(dataClass);

                    }

                }

                adapter1 = new UserAdapter(Verified_user.this, dataList1);
                recyclerViewNew.setAdapter(adapter1);
                adapter1.notifyDataSetChanged();

//                shimmerFrameLayout1.stopShimmer();
//                shimmerFrameLayout1.setVisibility(View.GONE);
                recyclerViewNew.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });





//        imageView = view.findViewById(R.id.imageView);
//        textView = view.findViewById(R.id.textView);
//        btnDeny = view.findViewById(R.id.btnDeny);
//        btnApprove = view.findViewById(R.id.btnApprove);




//        usersRef = FirebaseDatabase.getInstance().getReference("Renters");
//
//        usersRef.orderByChild("mobile").equalTo("9000000008").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
//                    String imageUrl = userSnapshot.child("imageURLUser").getValue(String.class);
//
//                    Glide.with(requireContext()).load(imageUrl).into(imageView);
//                    name = userSnapshot.child("dll").getValue(String.class);
//                    name1 = userSnapshot.child("isVerified").getValue(String.class);
//
//                    // mob = userSnapshot.child("mobile").getValue(String.class);
//                }
//
//                if (name1 != null && name1.equals("true")) {
//                    btnDeny.setVisibility(View.GONE);
//                    btnApprove.setVisibility(View.GONE);
//                }
//
//                textView.setText(name);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Handle the error, if any.
//            }
//        });
//
//        btnApprove.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                usersRef.child("9000000008").child("isVerified").setValue("true");
//                if (name1 != null && name1.equals("true")) {
//                    btnDeny.setVisibility(View.GONE);
//                    btnApprove.setVisibility(View.GONE);
//                }
//            }
//        });

    }


    private void deleteItem(int position) {
        // Get the selected payAdmin item
        ReadWriteUserDetails1 selectedPayAdmin = adapter1.getItem(position);

        // Get the key of the selected item in the database
        String key = selectedPayAdmin.getMobile(); // You should have a getKey() method in your payAdmin class
//        String key2 = selectedPayAdmin.getCarModelName(); // You should have a getKey() method in your payAdmin class
//        String key1 = selectedPayAdmin.getUserMobile(); // You should have a getKey() method in your payAdmin class

        // Remove the item from the database
        databaseReference_High1.child(key).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(Verified_user.this, "User deleted successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Verified_user.this, "Failed to delete User", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

