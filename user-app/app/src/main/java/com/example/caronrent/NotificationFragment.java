package com.example.caronrent;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.caronrent.Add_car.car_mode;
import com.example.caronrent.BookingHistory.bookHistory;
import com.example.caronrent.BookingHistory.bookHistory1;
import com.example.caronrent.Driver.add_driver;
import com.example.caronrent.Driver.driver_mode;
import com.example.caronrent.Driver.show_driver;
import com.example.caronrent.E_commerce.e_com_mode;
import com.example.caronrent.E_commerce.e_commerce_gadgets;
import com.example.caronrent.E_commerce.e_commerce_item_add;
import com.example.caronrent.E_commerce.gadget_history;
import com.example.caronrent.PaymentHistory.payment_history_mode;
import com.example.caronrent.Report.Report_show;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class NotificationFragment extends Fragment{

//    ImageView compass;
    TextView txtdegree,txtrr;
    SensorManager sensorManager;
    private DatabaseReference usersRef;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "emailShare";
    String emailShare, name, isVer,mob,eem;



    LinearLayout add_car,see_all_car,car_gadgets,drivers,compass,car_history,payment_history,settings,gadget_history,report_history;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_notification, container, false);
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        add_car = view.findViewById(R.id.add_car);
//        txtrr = view.findViewById(R.id.txtrr);



        car_gadgets = view.findViewById(R.id.car_gadgets);
        drivers = view.findViewById(R.id.drivers);
        compass = view.findViewById(R.id.compass);
        car_history = view.findViewById(R.id.car_history);
        payment_history = view.findViewById(R.id.payment_history);
        gadget_history = view.findViewById(R.id.gadget_history);
        report_history = view.findViewById(R.id.report_history);
//        settings = view.findViewById(R.id.settings);


        sharedPreferences =getContext().getSharedPreferences("email_1", MODE_PRIVATE); ;

        eem = sharedPreferences.getString("email","AAA");
        usersRef = FirebaseDatabase.getInstance().getReference("Admin").child("Renters");

        usersRef.orderByChild("email").equalTo(eem).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    name = userSnapshot.child("email").getValue(String.class);
                    isVer = userSnapshot.child("isVerified").getValue(String.class);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle the error, if any.
            }
        });


        add_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), car_mode.class);
                intent.putExtra("na",name);
                startActivity(intent);

            }
        });

        car_gadgets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), e_com_mode.class);
                intent.putExtra("na",name);
                startActivity(intent);

            }
        });


        drivers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), show_driver.class);
                intent.putExtra("em",name);
                startActivity(intent);
            }
        });

        car_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), bookHistory1.class);
                intent.putExtra("na",name);
                startActivity(intent);
            }
        });


        compass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Payments.class);
                intent.putExtra("na",name);
                startActivity(intent);
            }
        });

        payment_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), payment_history_mode.class);
                intent.putExtra("na",name);
                startActivity(intent);
            }
        });


        report_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Report_show.class);
//                intent.putExtra("na",name);
                startActivity(intent);
            }
        });

        gadget_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), com.example.caronrent.E_commerce.gadget_history.class);
                intent.putExtra("na",name);
                startActivity(intent);
            }
        });

        return view;
    }
}