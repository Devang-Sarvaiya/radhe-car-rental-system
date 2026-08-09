package com.example.caronrent.For_new_Project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caronrent.HomeFragment;
import com.example.caronrent.MainFragment;
import com.example.caronrent.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class date_selection extends AppCompatActivity {

    EditText dp1, dp2;
    Calendar calendar, calendar2;
    Button btn,btn_skip;
    String d1, d2;
    TextView day;

    SharedPreferences sharedPreferences;
    private static final String KEY_NAME = "emailShare";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_selection);

        // Initialize sharedPreferences before trying to use it
        sharedPreferences = getSharedPreferences("date", MODE_PRIVATE);
        SharedPreferences sharedPreferences1 = getSharedPreferences("email_1", MODE_PRIVATE);
        String eem = sharedPreferences1.getString("email","AAA");

        System.out.println("*/*/*/*/*/*/*/*/*//*//*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*"+eem);


        dp1 = findViewById(R.id.edt_start_date);
        dp2 = findViewById(R.id.edt_end_date);
        btn = findViewById(R.id.btn_find);
        btn_skip = findViewById(R.id.btn_skip);
        day = findViewById(R.id.txt_total_day);

        calendar = Calendar.getInstance();
        calendar2 = Calendar.getInstance();

        String em = sharedPreferences.getString(KEY_NAME, null);

        calendar = Calendar.getInstance(); // Remove the redundant declaration

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year3, int month3, int dayOfMonth3) {
                calendar.set(Calendar.YEAR, year3);
                calendar.set(Calendar.MONTH, month3);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth3);

                SharedPreferences.Editor sedit = sharedPreferences.edit();
                sedit.putInt("day1", dayOfMonth3);
                sedit.putInt("mon1", month3); // Removed the increment here
                sedit.putInt("year1", year3);
                sedit.apply();

                updateCalendar2();

                d1 = dp1.getText().toString();
                d2 = dp2.getText().toString();

                calculateDays();
            }

            private void updateCalendar2() {
                String format = "dd/MM/yyyy"; // Corrected date format pattern
                SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
                dp1.setText(sdf.format(calendar.getTime()));
            }
        };

        dp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(date_selection.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

        calendar2 = Calendar.getInstance(); // Remove the redundant declaration

        DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year2, int month2, int dayOfMonth2) {
                calendar2.set(Calendar.YEAR, year2);
                calendar2.set(Calendar.MONTH, month2);
                calendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth2);

                SharedPreferences.Editor sedit = sharedPreferences.edit();
                sedit.putInt("day", dayOfMonth2);
                sedit.putInt("mon", ++month2);
                sedit.putInt("year", year2);
                sedit.apply();

                updateCalendar2();

                d1 = dp1.getText().toString();
                d2 = dp2.getText().toString();

                calculateDays();
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
                DatePickerDialog datePickerDialog2 = new DatePickerDialog(date_selection.this, date2, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog2.getDatePicker().setMinDate(calendar.getTimeInMillis() + (24 * 60 * 60 * 1000));
                datePickerDialog2.show();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (eem != null) {
                    // Use the retrieved value as needed
                    Toast.makeText(date_selection.this, eem, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(date_selection.this, filter_data_show.class);
                    i.putExtra("email", em);
                    startActivity(i);
                } else {
                    // Handle the case when em is null
                    Toast.makeText(date_selection.this, "Email is null", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (eem != null) {
                    // Use the retrieved value as needed
                    Toast.makeText(date_selection.this, eem, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(date_selection.this, MainFragment.class);

                    startActivity(i);
                } else {
                    // Handle the case when em is null
                    Toast.makeText(date_selection.this, "Skip Date selection", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Method to calculate and display the number of days
    private void calculateDays() {
        d1 = dp1.getText().toString();
        d2 = dp2.getText().toString();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {
            java.util.Date datee1 = simpleDateFormat.parse(d1);
            java.util.Date datee2 = simpleDateFormat.parse(d2);

            long different = (Math.abs(datee2.getTime()) - datee1.getTime());

            long diffday = (different / (24 * 60 * 60 * 1000));
            Log.i("Test", "Days" + diffday);
            String diff = String.valueOf(diffday);

            day.setText("DAYS : " + diff);

        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }
}
