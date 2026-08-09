package com.example.driver_module.BookHistory;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.driver_module.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Book_History_show extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "emailShare";
    String emailShare, name, isVer, mob,eem,mName;
    private DatabaseReference usersRef;
    TextView txtUname;
    private bookAdapter adapter;
    private  DatabaseReference databaseReference ;
    private RecyclerView recyclerViewPopular;
//    private ShimmerFrameLayout shimmerFrameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book_history_show);
        recyclerViewPopular = findViewById(R.id.recyclerView1);

        recyclerViewPopular.setHasFixedSize(true);
        recyclerViewPopular.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        Intent in = getIntent();
        mob = in.getStringExtra("rMob");
        mName = in.getStringExtra("mName");
//
//        sharedPreferences =getSharedPreferences("email_1", MODE_PRIVATE);
//
//        eem = sharedPreferences.getString("email","AAA");
//        usersRef = FirebaseDatabase.getInstance().getReference("Admin").child("Renters");
//
//        usersRef.orderByChild("email").equalTo(eem).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
//                    mob = userSnapshot.child("mobile").getValue(String.class);
//                }
//                System.out.println("******************************************************************************************" + mob);
//                databaseReference = FirebaseDatabase.getInstance().getReference("Admin").child("Renters").child(mob).child("Booking").child(mName);
//                databaseReference.addValueEventListener(new ValueEventListener() {
//                    @SuppressLint("NotifyDataSetChanged")
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        ArrayList<bookAdmin> dataList = new ArrayList<>();
//                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                            bookAdmin dataClass = dataSnapshot.getValue(bookAdmin.class);
//                            dataList.add(dataClass);
//                        }
//
//                        adapter = new bookAdapter(Book_History_show.this, dataList);
//                        recyclerViewPopular.setAdapter(adapter);
//                        adapter.notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        // Handle the error, if any.
//                    }
//                });
//
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Handle the error, if any.
//            }
//        });

        databaseReference = FirebaseDatabase.getInstance().getReference("Admin").child("Renters").child(mob).child("Booking").child(mName);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<bookAdmin> dataList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    bookAdmin dataClass = dataSnapshot.getValue(bookAdmin.class);
                    if(dataClass.getDriver().equals("yes")){
                        dataList.add(dataClass);
                    }

                }

                adapter = new bookAdapter(Book_History_show.this, dataList);
                recyclerViewPopular.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error, if any.
            }
        });
    }


}
