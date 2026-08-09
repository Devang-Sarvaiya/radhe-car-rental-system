package com.example.caronrentrenter;

import static androidx.core.content.ContentProviderCompat.requireContext;




import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;

import com.example.caronrentrenter.Adapter.MenuAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class Car_Menu extends AppCompatActivity {


    private RecyclerView recyclerView;
    private static final int REQUEST_CODE_SPEECH_INPUT = 1234;

    private ArrayList<DataClass> dataList;
    TextView txtdrj;
    private MenuAdapter adapter;
    String type, type1;
    private DataClass1 object;
    ImageButton mVoiceBtn;

    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_menu);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataList = new ArrayList<>();
        adapter = new MenuAdapter(this, dataList);
        recyclerView.setAdapter(adapter);
        searchView = findViewById(R.id.search);
        mVoiceBtn = findViewById(R.id.btnimg);

        searchView.clearFocus();
        Intent intent = getIntent();

        type = intent.getStringExtra("car");
        mVoiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speak();
            }
        });


//            if (type.toString().equals("General")) {
//                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Admin").child("Car").child("General");
//                databaseReference.addValueEventListener(new ValueEventListener() {
//                    @SuppressLint("NotifyDataSetChanged")
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                                for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
//                                    for (DataSnapshot dataSnapshot3 : dataSnapshot2.getChildren()) {
//
//                                        DataClass dataClass = dataSnapshot3.getValue(DataClass.class);
//                                        dataList.add(dataClass);
//
//
//                                    }
//
//
//                                }
//
//                            }
//                        }
//                        adapter.notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                    }
//                });
//
//            } else {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Admin").child("Car").child("General");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                            for (DataSnapshot dataSnapshot3 : dataSnapshot2.getChildren()) {
                                for (DataSnapshot dataSnapshot4 : dataSnapshot3.getChildren()) {
                                    DataClass dataClass = dataSnapshot4.getValue(DataClass.class);
                                    if(dataClass.getCarCompany().equals(type)){
                                        dataList.add(dataClass);
                                    }
//                                    dataList.add(dataClass);
                                }
                            }
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

//            }


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
        ArrayList<DataClass> searchList = new ArrayList<>();
        for (DataClass dataClass : dataList) {
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
//            Toast.makeText(requireContext(), "" + e.getMessage(), Toast.LENGTH_LONG).show();

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
                    searchView.setQuery(speechResult, false);
                } else {
//                    Toast.makeText(requireContext(), "Error in speech recognition: " + resultCode, Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}