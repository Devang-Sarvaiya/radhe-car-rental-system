package com.example.caronrentrenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caronrentrenter.Adapter.FavoriteAdapter;
import com.example.caronrentrenter.Adapter.ItemAdapter;
import com.example.caronrentrenter.Adapter.ItemAdapter1;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class CompassFragment extends Fragment {

    private static final int REQUEST_CODE_SPEECH_INPUT = 1234;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "emailShare";
    TextView txtdrj;
    //    SearchView search;
    String type, emailShare, mob, eem;
    ImageButton mVoiceBtn;
    SharedPreferences sharedPreferences;
    SearchView searchView;
    private RecyclerView recyclerView;
    private ArrayList<DataClass> dataList;
    private ItemAdapter1 adapter;
    private DatabaseReference usersRef;
    private ShimmerFrameLayout shimmerFrameLayout;
    private final DatabaseReference databaseReference_High = FirebaseDatabase.getInstance().getReference("Admin").child("Car").child("General");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);

        // Initialize UI elements
        recyclerView = view.findViewById(R.id.recyclerView1);
//        search = view.findViewById(R.id.search);
//        searchView.clearFocus();
        mVoiceBtn = view.findViewById(R.id.btnimg);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        dataList = new ArrayList<>();
        searchView = view.findViewById(R.id.search);
        searchView.clearFocus();


        shimmerFrameLayout = view.findViewById(R.id.shimmer_fav);
        shimmerFrameLayout.startShimmer();

        sharedPreferences = getContext().getSharedPreferences("email_1", Context.MODE_PRIVATE);


        eem = sharedPreferences.getString("email", "AAA");


        // Retrieve the user's mobile number


//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Admin").child("Users").child(mob).child("Favorite");

        // Fetch favorite items from the database
        databaseReference_High.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                ArrayList<DataClass> dataList = new ArrayList<>();
                dataList = new ArrayList<>();

                shimmerFrameLayout.startShimmer();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                            for (DataSnapshot dataSnapshot3 : dataSnapshot2.getChildren()) {
                                for (DataSnapshot dataSnapshot4 : dataSnapshot3.getChildren()) {
                                    DataClass dataClass = dataSnapshot4.getValue(DataClass.class);
                                    dataList.add(dataClass);
                                }
                            }
                        }
                    }
                }

                adapter = new ItemAdapter1(getContext(), dataList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                searchList(newText);
                return true;
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
        return view;

    }

    public void searchList(String text) {
        ArrayList<DataClass> searchList = new ArrayList<>();
        for (DataClass dataClass : dataList) {
            if (dataClass.getModelName().toLowerCase().contains(text.toLowerCase())) {
                searchList.add(dataClass);
            }
        }
        adapter.searchdatalist(searchList);
    }

}
