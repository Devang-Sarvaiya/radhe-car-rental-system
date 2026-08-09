package com.example.caronrent.E_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.example.caronrent.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class gadget_history extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "emailShare";
    String emailShare, name, isVer, mob,eem;

    private DatabaseReference usersRef;
    TextView txtUname;
    private order_adapter adapter;
    SearchView searchView;
    private ArrayList<order_Gadget> dataList;
    private DatabaseReference databaseReference;
    private RecyclerView recyclerViewPopular;
    private ShimmerFrameLayout shimmerFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gadget_history);
        searchView=findViewById(R.id.search);
        searchView.clearFocus();
        recyclerViewPopular = findViewById(R.id.recyclerView1);

        recyclerViewPopular.setHasFixedSize(true);
        recyclerViewPopular.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        sharedPreferences =getSharedPreferences("email_1", MODE_PRIVATE); ;

        eem = sharedPreferences.getString("email","AAA");
        usersRef = FirebaseDatabase.getInstance().getReference("Admin").child("Renters");

        usersRef.orderByChild("email").equalTo(eem).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
//                    name = userSnapshot.child("email").getValue(String.class);
                    mob = userSnapshot.child("mobile").getValue(String.class);

                }


                databaseReference = FirebaseDatabase.getInstance().getReference("Admin").child("Renters").child(mob).child("Gadget_Selling_Orders");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                         dataList = new ArrayList<>();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                            order_Gadget dataClass = dataSnapshot1.getValue(order_Gadget.class);
                            dataList.add(dataClass);


                        }}

                        adapter = new order_adapter(gadget_history.this, dataList);
                        recyclerViewPopular.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
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
        ArrayList<order_Gadget> searchList = new ArrayList<>();
        for (order_Gadget dataClass : dataList) {
            if (dataClass.getGadgetName().toLowerCase().contains(text.toLowerCase())) {
                searchList.add(dataClass);
            }
        }
        adapter.searchdatalist(searchList);
    }
}
