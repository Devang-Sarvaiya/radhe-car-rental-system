package com.example.caronrent.PaymentHistory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.example.caronrent.E_commerce.gadget_history;
import com.example.caronrent.E_commerce.order_Gadget;
import com.example.caronrent.E_commerce.order_adapter;
import com.example.caronrent.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class expences extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "emailShare";
    String emailShare, name, isVer, mob,eem;

    private DatabaseReference usersRef;
    TextView txtUname;
    private order_adapter adapter;
    private DatabaseReference databaseReference;
    private RecyclerView recyclerViewPopular;
    private ShimmerFrameLayout shimmerFrameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expences);

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


                databaseReference = FirebaseDatabase.getInstance().getReference("Admin").child("Renters").child(mob).child("Gadget_Purchasing_Orders");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<order_Gadget> dataList = new ArrayList<>();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                            order_Gadget dataClass = dataSnapshot.getValue(order_Gadget.class);
                            dataList.add(dataClass);


                        }

                        adapter = new order_adapter(expences.this, dataList);
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




    }
}
