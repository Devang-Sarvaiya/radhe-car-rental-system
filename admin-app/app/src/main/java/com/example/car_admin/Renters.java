package com.example.car_admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.car_admin.Cars.All_cars1;
import com.example.car_admin.Cars.car_list_mode;
import com.example.car_admin.PaymentHistory.MyButtonClickListener;
import com.example.car_admin.PaymentHistory.MySwipeHelper;
import com.example.car_admin.PaymentHistory.payAdmin;
import com.example.car_admin.Renters_Details.UnVerified_renters;
import com.example.car_admin.Renters_Details.Verified_renters;
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

public class Renters extends Fragment {
    private DatabaseReference usersRef;
    ImageView imageView;
    String name, name1;
    Button btnDeny, btnApprove;
    TextView textView;
    private ShimmerFrameLayout shimmerFrameLayout, shimmerFrameLayout1;
    private RenterAdapter adapter1;


    private final DatabaseReference databaseReference_High1 = FirebaseDatabase.getInstance().getReference("Admin").child("Renters");
//    private final DatabaseReference databaseReference_High2 = FirebaseDatabase.getInstance().getReference("Car").child("General");
//    private DatabaseReference usersRef;

    private RecyclerView recyclerViewNew;
    LinearLayout add_car;
    ConstraintLayout show_car;
    String na;

    public Renters() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_renters, container, false);



        add_car = view.findViewById(R.id.add_car);
        show_car = view.findViewById(R.id.show_car);

        add_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), Verified_renters.class);

                startActivity(intent);
            }
        });

        show_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), UnVerified_renters.class);
                startActivity(intent);
            }
        });




//
//        recyclerViewNew = view.findViewById(R.id.recyclerView);
//
//        recyclerViewNew.setHasFixedSize(true);
//        recyclerViewNew.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false));
//
//        MySwipeHelper swipeHelper = new MySwipeHelper(requireActivity(),recyclerViewNew,400) {
//            @Override
//            public void instantiateMyButton(RecyclerView.ViewHolder viewHolder, List<MyButton> buffer) {
//                buffer.add(new MyButton(requireActivity(),
//                        "Delete",
//                        50,
//                        0,
//                        Color.parseColor("#FF3c30"),
//                        new MyButtonClickListener(){
//                            @Override
//                            public void onClick(int pos) {
//                                Toast.makeText(requireActivity(), "Delete click", Toast.LENGTH_SHORT).show();
//                                deleteItem(pos);
//
//                            }
//
//                        }));
////
////                buffer.add(new MyButton(Payments.this,
////                        "Update",
////                        30,
////                        R.drawable.baseline_search_24,
////                        Color.parseColor("#FF9502"),
////                        new MyButtonClickListener(){
////                            @Override
////                            public void onClick(int pos) {
////                                Toast.makeText(Payments.this, "Update click", Toast.LENGTH_SHORT).show();
////                            }
////                        }));
//            }
//        };
//
//        databaseReference_High1.addValueEventListener(new ValueEventListener() {
//            @SuppressLint("NotifyDataSetChanged")
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                ArrayList<ReadWriteUserDetails> dataList1 = new ArrayList<>();
//
////                shimmerFrameLayout1.startShimmer();
//
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//
//                        ReadWriteUserDetails dataClass = dataSnapshot.getValue(ReadWriteUserDetails.class);
////                        databaseReference_High1.child(dataSnapshot.getKey()).setValue(dataClass);
//                        dataList1.add(dataClass);
//
//                }
//
//                adapter1 = new RenterAdapter(getContext(), dataList1);
//                recyclerViewNew.setAdapter(adapter1);
//                adapter1.notifyDataSetChanged();
//
////                shimmerFrameLayout1.stopShimmer();
////                shimmerFrameLayout1.setVisibility(View.GONE);
//                recyclerViewNew.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });





        return view;
    }

    private void deleteItem(int position) {
        // Get the selected payAdmin item
        ReadWriteUserDetails selectedPayAdmin = adapter1.getItem(position);

        // Get the key of the selected item in the database
        String key = selectedPayAdmin.getMobile(); // You should have a getKey() method in your payAdmin class
//        String key2 = selectedPayAdmin.getCarModelName(); // You should have a getKey() method in your payAdmin class
//        String key1 = selectedPayAdmin.getUserMobile(); // You should have a getKey() method in your payAdmin class

        // Remove the item from the database
        databaseReference_High1.child(key).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(requireActivity(), "Renter deleted successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(requireActivity(), "Failed to delete renter", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
