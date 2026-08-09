package com.example.caronrent;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caronrent.Adapter.FavoriteAdapter;
import com.example.caronrent.Add_car.C_ItemAdapter;
import com.example.caronrent.Add_car.ImageModel1;
import com.example.caronrent.PaymentHistory.payAdmin;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class FavoriteFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<ImageModel1> dataList;
    TextView txtdrj;
    SearchView search;
    SearchView searchView;
    private FavoriteAdapter adapter;
    String type, emailShare, mob, eem;
    ImageButton mVoiceBtn;
    private static final int REQUEST_CODE_SPEECH_INPUT = 1234;

    private DatabaseReference usersRef;
    private DatabaseReference databaseReference;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "emailShare";

    private ShimmerFrameLayout shimmerFrameLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);
        searchView=view.findViewById(R.id.search);
        searchView.clearFocus();
        // Initialize UI elements
        recyclerView = view.findViewById(R.id.recyclerView1);
        search = view.findViewById(R.id.search);
        mVoiceBtn = view.findViewById(R.id.btnimg);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
//      recyclerView.setLayoutManager(new GridLayoutManager(requireActivity(),2));


        dataList = new ArrayList<>();


        shimmerFrameLayout = view.findViewById(R.id.shimmer_fav);
        shimmerFrameLayout.startShimmer();

        sharedPreferences = getContext().getSharedPreferences("email_1", MODE_PRIVATE);
        eem = sharedPreferences.getString("email", "AAA");

        String demo = eem;
        String desiredUsername = demo;

        usersRef = FirebaseDatabase.getInstance().getReference("Admin").child("Renters");
        mVoiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speak();
            }
        });

        // Retrieve the user's mobile number
        usersRef.orderByChild("email").equalTo(desiredUsername).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    mob = userSnapshot.child("mobile").getValue(String.class);
                }
                type = mob;

                databaseReference = FirebaseDatabase.getInstance().getReference("Admin").child("Renters").child(mob).child("Car");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        dataList = new ArrayList<>();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                                    for (DataSnapshot dataSnapshot3 : dataSnapshot2.getChildren()) {
                                        for (DataSnapshot dataSnapshot4 : dataSnapshot3.getChildren()) {
                                            ImageModel1 dataClass = dataSnapshot4.getValue(ImageModel1.class);
                                            if (dataClass.getIsUnavailable().equals("false")) {
                                                dataList.add(dataClass);
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        adapter = new FavoriteAdapter(getContext(), dataList);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);

                        recyclerView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle the error, if any.
                    }
                });
//
//                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Admin").child("Renters").child(type).child("Favorite");
//
//                // Fetch favorite items from the database
//                databaseReference.addValueEventListener(new ValueEventListener() {
//                    @SuppressLint("NotifyDataSetChanged")
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//
//                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                            DataClass dataClass = dataSnapshot.getValue(DataClass.class);
//                            dataList.add(dataClass);
//                        }
//                        adapter = new FavoriteAdapter(requireActivity(), dataList);
//                        recyclerView.setAdapter(adapter);
//                        adapter.notifyDataSetChanged();
//
//                        shimmerFrameLayout.stopShimmer();
//                        shimmerFrameLayout.setVisibility(View.GONE);
//
//                        recyclerView.setVisibility(View.VISIBLE);
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        // Handle the error, if any.
//                    }
//                });
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
        return view;
    }
    public void searchList(String text) {
        ArrayList<ImageModel1> searchList = new ArrayList<>();
        for (ImageModel1 dataClass : dataList) {
            if (dataClass.getModelName().toLowerCase().contains(text.toLowerCase())) {
                searchList.add(dataClass);
            }
        }
        adapter.searchdatalist(searchList);
    }
    private void speak() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hii Speak Something");
        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
        } catch (Exception e) {
            Toast.makeText(requireContext(), "" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String speechResult = "";

        switch (requestCode) {
            case REQUEST_CODE_SPEECH_INPUT: {
                if (resultCode == -1 && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    speechResult = result.get(0);
                    search.setQuery(speechResult, false);
                } else {
                    Toast.makeText(requireContext(), "Error in speech recognition: " + resultCode, Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
