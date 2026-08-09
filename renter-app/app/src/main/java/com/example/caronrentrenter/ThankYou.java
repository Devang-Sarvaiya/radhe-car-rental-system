package com.example.caronrentrenter;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ThankYou extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you);
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Admin").child("Booking").child("User");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Admin").child("Car").child("General");

        List<String> startDates = new ArrayList<>();
        List<String> endDates = new ArrayList<>();

        // Replace these with your given start and end dates in dd/mm/yyyy format
        String givenStartDate = "15/03/2024";
        String givenEndDate = "16/03/2024";

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot slotSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot slotSnapshot1 : slotSnapshot.getChildren()) {
                        for (DataSnapshot slotSnapshot2 : slotSnapshot1.getChildren()) {
                            for (DataSnapshot slotSnapshot3 : slotSnapshot2.getChildren()) {
                                for (DataSnapshot slotSnapshot4 : slotSnapshot3.getChildren()) {
                                    for (DataSnapshot slotSnapshot5 : slotSnapshot4.getChildren()) {
                                        for (DataSnapshot slotSnapshot6 : slotSnapshot5.getChildren()) {

                                            String startDate = slotSnapshot6.child("startDate").getValue(String.class);
                                            String endDate = slotSnapshot6.child("endDate").getValue(String.class);

// Add null checks
                                            if (startDate != null && endDate != null) {
                                                // Convert string dates to Date objects for comparison
                                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                                try {
                                                    Date startDateTime = sdf.parse(startDate);
                                                    Date endDateTime = sdf.parse(endDate);
                                                    Date givenStartDateTime = sdf.parse(givenStartDate);
                                                    Date givenEndDateTime = sdf.parse(givenEndDate);

                                                    // Check if the date range overlaps with the given date range
                                                    if (startDateTime.compareTo(givenEndDateTime) > 0 || endDateTime.compareTo(givenStartDateTime) < 0) {
                                                        // Date range does not overlap, log or display the dates
                                                        Log.d("ThankYou", "Start Date: " + startDate + ", End Date: " + endDate);
                                                    }
                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                }
                                            } else {
                                                Log.e("ThankYou", "startDate or endDate is null");
                                            }

                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error (if any)
            }
        });
    }
}