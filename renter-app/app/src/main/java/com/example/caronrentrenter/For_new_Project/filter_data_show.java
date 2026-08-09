package com.example.caronrentrenter.For_new_Project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caronrentrenter.Adapter.ItemAdapter;
import com.example.caronrentrenter.DataClass;
import com.example.caronrentrenter.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class filter_data_show extends AppCompatActivity {

    private DatabaseReference databaseReference_High;
    private filterAdapter adapter;
    private RecyclerView recyclerViewPopular;
    private List<BookingDetails> bookingDetailsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_data_show);

        recyclerViewPopular = findViewById(R.id.recyclerView1);
        recyclerViewPopular.setHasFixedSize(true);
        recyclerViewPopular.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        databaseReference_High = FirebaseDatabase.getInstance().getReference("Admin").child("Car").child("General");
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Admin").child("Booking").child("User");

        Intent i = getIntent();
        String givenStartDate = i.getStringExtra("d1");
        String givenEndDate = i.getStringExtra("d2");

        Toast.makeText(this, givenStartDate, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, givenEndDate, Toast.LENGTH_SHORT).show();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bookingDetailsList.clear();
                for (DataSnapshot slotSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot slotSnapshot1 : slotSnapshot.getChildren()) {
                        for (DataSnapshot slotSnapshot2 : slotSnapshot1.getChildren()) {
                            String modelName = slotSnapshot2.child("carModelName").getValue(String.class);
                            String startDate = slotSnapshot2.child("startDate").getValue(String.class);
                            String endDate = slotSnapshot2.child("endDate").getValue(String.class);

                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            try {
                                Date startDateTime = sdf.parse(startDate);
                                Date endDateTime = sdf.parse(endDate);
                                Date givenStartDateTime = sdf.parse(givenStartDate);
                                Date givenEndDateTime = sdf.parse(givenEndDate);

                                if (startDateTime.compareTo(givenEndDateTime) > 0 || endDateTime.compareTo(givenStartDateTime) < 0) {
                                } else {
                                    bookingDetailsList.add(new BookingDetails(modelName, startDate, endDate));
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                loadDataFromFirebase();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error (if any)
            }
        });
    }

    private void loadDataFromFirebase() {
        databaseReference_High.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<DataClass> dataList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                            for (DataSnapshot dataSnapshot3 : dataSnapshot2.getChildren()) {
                                for (DataSnapshot dataSnapshot4 : dataSnapshot3.getChildren()) {
                                    DataClass dataClass = dataSnapshot4.getValue(DataClass.class);
                                    boolean matchFound = false;
                                    for (BookingDetails bookingDetails : bookingDetailsList) {
                                        if (dataClass.getModelName().equals(bookingDetails.getCarModelName())) {
                                            matchFound = true;
                                            break;
                                        }
                                    }
                                    if (!matchFound) {
                                        dataList.add(dataClass);
                                    }
                                }
                            }
                        }
                    }
                }

                if (dataList.isEmpty()) {
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
                }

                adapter = new filterAdapter(filter_data_show.this, dataList);
                recyclerViewPopular.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error (if any)
            }
        });
    }
}
