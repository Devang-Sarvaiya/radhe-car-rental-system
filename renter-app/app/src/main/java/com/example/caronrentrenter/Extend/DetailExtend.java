package com.example.caronrentrenter.Extend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.bumptech.glide.Glide;
import com.example.caronrentrenter.BookHistory.bookAdmin;
import com.example.caronrentrenter.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailExtend extends AppCompatActivity {

    bookAdmin object;
    ImageView imgCar;
    TextView txtMName, txtToAmt, txtRentPerDay, txtNewToAmt, txtNewEndDate, txtExtraCharge, txtNewPaybleAmount;
    RadioGroup rg1;
    RadioButton rdb1, rdb2;
    String selectedOption, selectedOption1, isReq, isCan;
    ProgressBar progressBar3;
    String tott;
    String newEndDate;
    AppCompatButton btnSendRequest, btnPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_extend);

        imgCar = findViewById(R.id.imgCar);
        txtMName = findViewById(R.id.txtMName);
        txtToAmt = findViewById(R.id.txtToAmt);
        txtRentPerDay = findViewById(R.id.txtRentPerDay);
        txtNewToAmt = findViewById(R.id.txtNewToAmt);
        txtNewEndDate = findViewById(R.id.txtNewEndDate);
        txtExtraCharge = findViewById(R.id.txtExtraCharge);
        txtNewPaybleAmount = findViewById(R.id.txtNewPaybleAmount);
        rg1 = findViewById(R.id.rg1);
        rdb1 = findViewById(R.id.rdb1);
        rdb2 = findViewById(R.id.rdb2);
        btnSendRequest = findViewById(R.id.btnSendRequest);
        btnPay = findViewById(R.id.btnPay);


        object = (bookAdmin) getIntent().getSerializableExtra("object");

        String imageUrl = object.getModelImageUrl();
        Glide.with(DetailExtend.this).load(imageUrl).into(imgCar);

        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Check which radio button is selected
                RadioButton radioButton = findViewById(checkedId);

                if (radioButton != null) {
                    selectedOption1 = radioButton.getText().toString();
                    if (selectedOption1.equals("1 Day ")) {
                        selectedOption = "1";
                    } else {
                        selectedOption = "2";
                    }
//                    Toast.makeText(DetailExtend.this, "Selected Day: " + selectedOption, Toast.LENGTH_SHORT).show();

                    // Move the relevant calculations and logic here
                    if (selectedOption != null && !selectedOption.isEmpty()) {
                        // Perform calculations and logic based on selected option
                        int ne = Integer.parseInt(object.getRentPerDay());
                        int d = Integer.parseInt(selectedOption);
                        int toned = ne * d;
                        String toned1 = String.valueOf(toned);
                        txtNewToAmt.setText(toned1);

                        // Logic for adding values to end date
                        // This part depends on selectedOption, so it should be inside this block
                        String da = object.getEndDate();
                        int selectedDays = Integer.parseInt(selectedOption);
                        // Parse the original end date
                        String[] dateParts = da.split("/");
                        int day = Integer.parseInt(dateParts[0]);
                        int month = Integer.parseInt(dateParts[1]);
                        int year = Integer.parseInt(dateParts[2]);

                        // Add the selected days to the day
                        day += selectedDays;

                        // Perform necessary adjustments if the days exceed the maximum days in the month
                        // Here you might need a more sophisticated approach if the date could span across months or years
                        // For simplicity, let's assume all months have 30 days
                        if (day > 30) {
                            day -= 30;
                            month++;
                            if (month > 12) {
                                month -= 12;
                                year++;
                            }
                        }

                        // Format the new date back to string
                        newEndDate = String.format("%02d/%02d/%04d", day, month, year);
                        txtNewEndDate.setText(newEndDate);

                        txtExtraCharge.setText("500");
                        int tot = toned + 500;
                        tott = String.valueOf(tot);
                        txtNewPaybleAmount.setText(tott);
                    }
                }
            }
        });


        txtMName.setText(object.getCarModelName());
        txtToAmt.setText(object.getTotalAmount());
        txtRentPerDay.setText(object.getRentPerDay());

        btnPay.setVisibility(View.GONE);

        btnSendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selectedOption != null) {
                    DatabaseReference db = FirebaseDatabase.getInstance().getReference("Admin").child("Booking").child("User").child(object.getUserMobile()).child(object.getCarModelName()).child(object.getSlot());
                    db.child("isExtended").setValue("true");
                    DatabaseReference db1 = FirebaseDatabase.getInstance().getReference("Admin").child("Renters").child(object.getRenterMobile()).child("Booking").child(object.getCarModelName()).child(object.getSlot());
                    db1.child("isExtended").setValue("true");
                    DatabaseReference db2 = FirebaseDatabase.getInstance().getReference("Admin").child("Users").child(object.getUserMobile()).child("Booking").child(object.getCarModelName()).child(object.getSlot());
                    db2.child("isExtended").setValue("true");

                    btnSendRequest.setVisibility(View.GONE);


//                progressBar3.setVisibility(View.VISIBLE);
                } else {
                    Snackbar.make(txtMName, "Please select day", Snackbar.LENGTH_SHORT).show();
                }


            }
        });


        DatabaseReference db4 = FirebaseDatabase.getInstance().getReference("Admin").child("Users").child(object.getUserMobile()).child("Booking").child(object.getCarModelName()).child(object.getSlot());
        db4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                isReq = snapshot.child("isRenterAccepted").getValue(String.class);
                isCan = snapshot.child("isExtended").getValue(String.class);
                if (isReq.equals("true")) {
                    btnPay.setVisibility(View.VISIBLE);
                    btnSendRequest.setVisibility(View.INVISIBLE);

                    btnPay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String rmob = object.getRenterMobile();
                            String umob = object.getUserMobile();
                            String carname = object.getCarModelName();
                            String slott = object.getSlot();

                            Intent in = new Intent(DetailExtend.this, Extend_new_payment.class);
                            in.putExtra("totalAmt", tott);
                            in.putExtra("rMob", rmob);
                            in.putExtra("uMob", umob);
                            in.putExtra("carName", carname);
                            in.putExtra("slot", slott);
                            in.putExtra("newdate", newEndDate);
                            startActivity(in);
                            finish();
                        }
                    });

                } else {
                    btnPay.setVisibility(View.GONE);

                    btnSendRequest.setVisibility(View.VISIBLE);

                }
//                if (isCan.equals("false")) {
//                    btnSendRequest.setVisibility(View.VISIBLE);
//                    btnPay.setVisibility(View.GONE);
//                }else{
//
//                    btnSendRequest.setVisibility(View.GONE);
//                    btnPay.setVisibility(View.GONE);
//
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}