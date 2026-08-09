package com.example.caronrent.E_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.caronrent.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class e_com_Renters_gadgets extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "emailShare";
    String emailShare, name, isVer, eem, mob;

    private DatabaseReference usersRef;
    TextView txtUname;
    SearchView searchView;
    private ArrayList<ImageModel_1_e_com> dataList;
    private G_ItemAdapter adapter;
    DatabaseReference databaseReference_High;
    private RecyclerView recyclerViewPopular;
    private ShimmerFrameLayout shimmerFrameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecom_renters_gadgets);
        searchView=findViewById(R.id.search);
        searchView.clearFocus();

        sharedPreferences = getSharedPreferences("email_1", MODE_PRIVATE);


        eem = sharedPreferences.getString("email", "AAA");
        txtUname = findViewById(R.id.txtUname);
        recyclerViewPopular = findViewById(R.id.recyclerView_e_com);
        shimmerFrameLayout = findViewById(R.id.shimmer_e_com);

        recyclerViewPopular.setHasFixedSize(true);
        recyclerViewPopular.setLayoutManager(new GridLayoutManager(this, 2));

        usersRef = FirebaseDatabase.getInstance().getReference("Admin").child("Renters");

        usersRef.orderByChild("email").equalTo(eem).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    name = userSnapshot.child("name").getValue(String.class);
                    isVer = userSnapshot.child("isVerified").getValue(String.class);
                    mob = userSnapshot.child("mobile").getValue(String.class);
                }

                // Uncomment and adapt the code below according to your requirements
                // txtUname.setText(name);

                databaseReference_High = FirebaseDatabase.getInstance().getReference("Admin").child("Renters").child(mob).child("Gadgets");
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
                                            ImageModel_1_e_com dataClass = dataSnapshot4.getValue(ImageModel_1_e_com.class);
                                            if(dataClass.getRenterMobile().equals(mob)){
                                                dataList.add(dataClass);
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        // Initialize the adapter before using it
                        adapter = new G_ItemAdapter(e_com_Renters_gadgets.this, dataList);
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
        // Set up RecyclerView for popular cars


    }
    public void searchList(String text) {
        ArrayList<ImageModel_1_e_com> searchList = new ArrayList<>();
        for (ImageModel_1_e_com dataClass : dataList) {
            if (dataClass.getModelName().toLowerCase().contains(text.toLowerCase())) {
                searchList.add(dataClass);
            }
        }
        adapter.searchdatalist(searchList);
    }
}
