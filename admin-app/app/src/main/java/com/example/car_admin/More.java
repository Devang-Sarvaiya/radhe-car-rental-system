package com.example.car_admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.car_admin.Cars.All_cars1;
import com.example.car_admin.Cars.C_ItemAdapter;
import com.example.car_admin.Cars.ImageModel1;
import com.example.car_admin.Cars.car_list_mode;
import com.example.car_admin.Driver.driver_mode;
import com.example.car_admin.Safety_feature.Safety_Data_add;
import com.example.car_admin.Tourism.Tourism_Data_add;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class More extends Fragment {

    ImageView driver, payment,book, all_car,add_saftey,add_tour_place;


    public More() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_more, container, false);

        driver = view.findViewById(R.id.show_all_driver);
        payment = view.findViewById(R.id.show_all_payment_history);
        book = view.findViewById(R.id.show_all_book_history);
        all_car = view.findViewById(R.id.show_all_car);
        add_tour_place = view.findViewById(R.id.add_tour_place);
        add_saftey = view.findViewById(R.id.add_saftey);

        driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), driver_mode.class));
            }
        });

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Payments.class));
            }
        });

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Booked_cars.class));
            }
        });

        all_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), car_list_mode.class));
            }
        });

        add_saftey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Safety_Data_add.class));
            }
        });

        add_tour_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Tourism_Data_add.class));
            }
        });




        // Inflate the layout for this fragment
        return view;
    }
}
