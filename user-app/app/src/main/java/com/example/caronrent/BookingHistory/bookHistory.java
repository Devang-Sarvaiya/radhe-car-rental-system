package com.example.caronrent.BookingHistory;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caronrent.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class bookHistory extends AppCompatActivity {

    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "emailShare";
    SharedPreferences sharedPreferences;
    String emailShare, name, isVer, mob, eem, mName;
    TextView txtUname;
    SearchView searchView;
    private DatabaseReference usersRef;
    private bookAdapter adapter;
    private ArrayList<bookAdmin> dataList;
    private DatabaseReference databaseReference;
    private RecyclerView recyclerViewPopular;
    private ShimmerFrameLayout shimmerFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_history);
        recyclerViewPopular = findViewById(R.id.recyclerView1);
        searchView = findViewById(R.id.search);
        searchView.clearFocus();
        recyclerViewPopular.setHasFixedSize(true);
        recyclerViewPopular.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        sharedPreferences = getSharedPreferences("email_1", MODE_PRIVATE);

        Intent in = getIntent();
        mName = in.getStringExtra("mName");

        eem = sharedPreferences.getString("email", "AAA");
        usersRef = FirebaseDatabase.getInstance().getReference("Admin").child("Renters");

        usersRef.orderByChild("email").equalTo(eem).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    mob = userSnapshot.child("mobile").getValue(String.class);
                }

                System.out.println("******************************************************************************************" + mob);


                databaseReference = FirebaseDatabase.getInstance().getReference("Admin").child("Renters").child(mob).child("Booking").child(mName);
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<bookAdmin> dataList = new ArrayList<>();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {


                            bookAdmin dataClass = dataSnapshot.getValue(bookAdmin.class);
                            dataList.add(dataClass);


                        }

                        adapter = new bookAdapter(bookHistory.this, dataList);
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
        ArrayList<bookAdmin> searchList = new ArrayList<>();
        for (bookAdmin dataClass : dataList) {
            if (dataClass.getStartDate().toLowerCase().contains(text.toLowerCase())) {
                searchList.add(dataClass);
            } else if (dataClass.getEndDate().toLowerCase().contains(text.toLowerCase())) {
                searchList.add(dataClass);
            }
        }
        adapter.searchdatalist(searchList);
    }
}
