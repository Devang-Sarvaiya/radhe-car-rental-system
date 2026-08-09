package com.example.caronrent.Driver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caronrent.Driver.D_ItemAdapter;
import com.example.caronrent.Driver.ImageModel_1_driver;
import com.example.caronrent.Driver.ImageModel_driver;
import com.example.caronrent.Driver.ImageAdapter_driver;
import com.example.caronrent.Driver.add_driver;
import com.example.caronrent.Driver.show_driver;
import com.example.caronrent.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class show_driver extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "emailShare";
    private DatabaseReference usersRef;
    String emailShare, ema;
    String mob;
    TextView txtUname;
    SearchView searchView;
    private ArrayList<ImageModel_1_driver> dataList;
    private D_ItemAdapter adapter;
    private DatabaseReference databaseReference_High;
    private RecyclerView recyclerViewPopular;
    private ShimmerFrameLayout shimmerFrameLayout;


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

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        emailShare = sharedPreferences.getString(KEY_NAME, null);


        Intent in = getIntent();
        String na = in.getStringExtra("em");
//        Toast.makeText(this, na, Toast.LENGTH_SHORT).show();

        usersRef = FirebaseDatabase.getInstance().getReference("Admin").child("Renters");

        usersRef.orderByChild("email").equalTo(na).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    mob = userSnapshot.child("mobile").getValue(String.class);

                }

                databaseReference_High = FirebaseDatabase.getInstance().getReference("Admin").child("Drivers").child("General");
                // Fetch popular cars from the database
                databaseReference_High.addValueEventListener(new ValueEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        dataList = new ArrayList<>();

                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                ImageModel_1_driver dataClass = dataSnapshot.getValue(ImageModel_1_driver.class);
                                if(dataClass.getCarRenterMobile().equals(mob)){
                                    dataList.add(dataClass);
                            }
                        }

                        // Initialize the adapter before using it
                        adapter = new D_ItemAdapter(show_driver.this, dataList);
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


//         Set up RecyclerView for popular cars

    }

    public void searchList(String text) {
        ArrayList<ImageModel_1_driver> searchList = new ArrayList<>();
        for (ImageModel_1_driver dataClass : dataList) {
            if (dataClass.getName().toLowerCase().contains(text.toLowerCase())) {
                searchList.add(dataClass);
            }
        }
        adapter.searchdatalist(searchList);
    }
}
