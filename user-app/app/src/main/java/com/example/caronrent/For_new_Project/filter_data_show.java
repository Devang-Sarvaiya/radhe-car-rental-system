package com.example.caronrent.For_new_Project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.caronrent.Add_car.C_ItemAdapter;
import com.example.caronrent.Add_car.ImageModel1;
import com.example.caronrent.DataClass;
import com.example.caronrent.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class filter_data_show extends AppCompatActivity {
    final private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Admin").child("Renters");
    private filterAdapter adapter;
    private RecyclerView recyclerViewPopular;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_data_show);


        recyclerViewPopular = findViewById(R.id.recyclerView1);

        recyclerViewPopular.setHasFixedSize(true);
        recyclerViewPopular.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));

        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int day, mon, year;
                int day1, mon1, year1;

                SharedPreferences sharedPreferences1 = getSharedPreferences("date", MODE_PRIVATE);
                day = sharedPreferences1.getInt("day", 0);
                mon = sharedPreferences1.getInt("mon", 0);
                year = sharedPreferences1.getInt("year", 0);
                day1 = sharedPreferences1.getInt("day1", 0);
                mon1 = sharedPreferences1.getInt("mon1", 0);
                year1 = sharedPreferences1.getInt("year1", 0);

                ArrayList<DataClass> dataList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                            for (DataSnapshot dataSnapshot3 : dataSnapshot2.getChildren()) {
                                for (DataSnapshot dataSnapshot4 : dataSnapshot3.getChildren()) {
                                    for (DataSnapshot dataSnapshot5 : dataSnapshot4.getChildren()) {
                                        for (DataSnapshot dataSnapshot6 : dataSnapshot5.getChildren()) {
                                            DataClass dataClass = dataSnapshot6.getValue(DataClass.class);

                                            String[] arr1 = dataClass.getStart_date().toString().split("/");
                                            String[] arr = dataClass.getEnd_date().toString().split("/");
                                            if (Integer.parseInt(arr1[1]) == (mon1 + 1) || Integer.parseInt(arr[1]) == mon) {
//                                                System.out.println("********--------********---------********----------*****" + arr1[1]);
//                                                System.out.println("********--------********---------********----------*****" + (mon1 + 1));
//
//                                                System.out.println("**********************//////////////////////************************" + arr1[0]);
                                                System.out.println("**********************//////////////////////************************" + day1);
                                                int i;
                                                boolean tf = true;
                                                for (i = day1; i <= day; i++) {
                                                    if (i == Integer.parseInt(arr1[0]) || i == Integer.parseInt(arr[0])) {
                                                        tf = false;
                                                        System.out.println("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq" + i);
                                                        break;
                                                    }
                                                }

                                                if (tf) {
//                                                    System.out.println("**********************//////////////////////************************" + day);
//                                                    System.out.println("**********************//////////////////////************************" + arr[0]);
//                                                    System.out.println("**********************//////////////////////************************" + mon);
//                                                    System.out.println("**********************//////////////////////************************" + arr[1]);
                                                    dataList.add(dataClass);
                                                } else {
                                                    System.out.println("No such a car");
                                                }
                                            }
                                            else
                                            {
                                                dataList.add(dataClass);
                                            }
                                        }
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
                // Handle the error, if any.
            }
        });

    }
}