package com.example.caronrent;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.caronrent.Payment.Main_Payment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Locale;

public class BookingDateSelection extends AppCompatActivity {
    EditText dp1, dp2;
    Calendar calendar;
    Button btn;
    Calendar calendar2;
    int count, s, e;
    int dt1,dt2,dt3;
    String d1, d2;
    TextView day, rent, total, txtModelName;
    String ans;
    ImageView img_Car_Book;

    String modelname,imageUrl,mCom,mType,rMO;
    private DatabaseReference usersRef;
    final private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Admin").child("Renters");
    final private DatabaseReference databaseReference_admin = FirebaseDatabase.getInstance().getReference("Admin");
    final private DatabaseReference databaseReference_Car = FirebaseDatabase.getInstance().getReference("Admin").child("Car");
    final private DatabaseReference databaseReference_Date = FirebaseDatabase.getInstance().getReference("Admin").child("date");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hiren);

        dp1 = findViewById(R.id.dp);
        btn = findViewById(R.id.button);
        day = findViewById(R.id.day);
        rent = findViewById(R.id.rent);
        total = findViewById(R.id.totalRent);
        txtModelName = findViewById(R.id.txtModelName);
        img_Car_Book = findViewById(R.id.img_Car_Book);
        calendar = Calendar.getInstance();


        Intent in = getIntent();
        modelname = in.getStringExtra("moname");
        mCom = in.getStringExtra("mCompany");
        mType = in.getStringExtra("mType");
        rMO = in.getStringExtra("rMo");


        imageUrl = in.getStringExtra("mpic");


        Glide.with(BookingDateSelection.this).load(imageUrl).into(img_Car_Book);
        txtModelName.setText(modelname);



        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {

                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                s = dayOfMonth;

                updateCalendar();
            }

            private void updateCalendar() {
                String format = "dd/MM/yyyy"; // Corrected date format pattern
                SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);

                dp1.setText(sdf.format(calendar.getTime()));
            }
        };

        dp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(BookingDateSelection.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

        dp2 = findViewById(R.id.dp2);
        calendar2 = Calendar.getInstance(); // Remove the redundant declaration

        DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year2, int month2, int dayOfMonth2) {
                calendar2.set(Calendar.YEAR, year2);
                calendar2.set(Calendar.MONTH, month2);
                calendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth2);
                SharedPreferences sharedPreferences=getSharedPreferences("date",MODE_PRIVATE);
                SharedPreferences.Editor sedit=sharedPreferences.edit();
                sedit.putInt("day",dayOfMonth2);
                sedit.putInt("mon",++month2);
                sedit.putInt("year",year2);
                sedit.apply();
                sedit.commit();

                e = dayOfMonth2;

                updateCalendar2();

                count = e - s;

                d1 = dp1.getText().toString();
                d2 = dp2.getText().toString();

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

                try {
                    java.util.Date datee1 = simpleDateFormat.parse(d1);
                    java.util.Date datee2 = simpleDateFormat.parse(d2);

                    // Check if selected day is the same as dp1's day
                    if (datee2.getDate() == calendar.get(Calendar.DAY_OF_MONTH)) {
                        Toast.makeText(BookingDateSelection.this, "Selected day is not allowed", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    long different = (Math.abs(datee2.getTime()) - datee1.getTime());

                    long diffday = (different / (24 * 60 * 60 * 1000));
                    Log.i("Test", "Days" + diffday);
                    String diff = String.valueOf(diffday);
                    String mmprice = in.getStringExtra("mprice");

                    rent.setText("RENT : " + mmprice);
                    int mmprice_1 = Integer.parseInt(mmprice.toString());

                    day.setText("DAYS : " + diff);
                    rent.setText("RENT : " + mmprice_1);
                    long tot = diffday * mmprice_1;

                    ans = String.valueOf(tot);

                    total.setText("TOTAL : " + ans);

                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            }

            private void updateCalendar2() {
                String format = "dd/MM/yyyy"; // Corrected date format pattern
                SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
                dp2.setText(sdf.format(calendar2.getTime()));
            }
        };

        dp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog2 = new DatePickerDialog(BookingDateSelection.this, date2, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

                // Set the minimum date for datePickerDialog2 as selected date from datePickerDialog plus one day
                datePickerDialog2.getDatePicker().setMinDate(calendar.getTimeInMillis() + (24 * 60 * 60 * 1000));

                datePickerDialog2.show();
            }
        });







        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                databaseReference.child(rMO).child("Car").child(mType).child("Company").child(mCom).child(modelname).child("Details").child("start_date").setValue(d1);
                databaseReference.child(rMO).child("Car").child(mType).child("Company").child(mCom).child(modelname).child("Details").child("end_date").setValue(d2);
                databaseReference_Car.child("General").child(mType).child("Company").child(mCom).child(modelname).child("Details").child("start_date").setValue(d1);
                databaseReference_Car.child("General").child(mType).child("Company").child(mCom).child(modelname).child("Details").child("end_date").setValue(d2);
                databaseReference_Date.child(modelname).child("start_date").setValue(d1);
                databaseReference_Date.child(modelname).child("end_date").setValue(d2);

                databaseReference_admin.child("Payment").child(rMO).child("Car").child(mType).child("Company").child(mCom).child(modelname).child("Details").child("rentersMobile").setValue("false");



                // Implement the logic for handling the booking action
                Intent i=new Intent(BookingDateSelection.this, Main_Payment.class);


                i.putExtra("mmmprice",ans);
                i.putExtra("price",ans);
                startActivity(i);
            }
        });
    }
}