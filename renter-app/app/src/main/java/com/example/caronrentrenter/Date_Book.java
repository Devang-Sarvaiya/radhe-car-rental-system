package com.example.caronrentrenter;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.caronrentrenter.Payment.payAdmin;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.Executor;

public class Date_Book extends AppCompatActivity {

    EditText dp1, dp2;
    Calendar calendar;
    Button btn;
    Calendar calendar2;
    String imageUrl, start_date, end_date;
    int count, s, e;
    String d1, d2;
    TextView day, rent, total, txtModelName, txtaddress;
    String ans, ottp;
    ImageView img_Car_Book;
    BiometricPrompt biometricPrompt;
    BiometricPrompt.PromptInfo promptInfo;
    ScrollView onMainlayout;
    int slotNumber;
    private DatabaseReference usersRef;
    private DatabaseReference databaseReference_Admin = FirebaseDatabase.getInstance().getReference("Admin");
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Admin").child("Renters");
    DatabaseReference databaseReference_Renter = FirebaseDatabase.getInstance().getReference("Admin").child("Renters");
    DatabaseReference databaseReference_Car = FirebaseDatabase.getInstance().getReference("Admin").child("Car");
    DatabaseReference databaseReference_General = FirebaseDatabase.getInstance().getReference("Admin").child("Car");
    SharedPreferences sharedPreferences;
    String eem, mob, gettext, pPoint;
    TextToSpeech texttospeech;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_book);

        dp1 = findViewById(R.id.dp);
        btn = findViewById(R.id.button);
        day = findViewById(R.id.day);
        rent = findViewById(R.id.rent);
        total = findViewById(R.id.totalRent);
        txtModelName = findViewById(R.id.txtModelName);
        txtaddress = findViewById(R.id.txtaddress);
        img_Car_Book = findViewById(R.id.img_Car_Book);
        calendar = Calendar.getInstance();

        Intent in = getIntent();
        String modelname = in.getStringExtra("moname");
        pPoint = in.getStringExtra("pPoint");
        txtaddress.setText(pPoint);
        imageUrl = in.getStringExtra("mpic");
        Glide.with(Date_Book.this).load(imageUrl).into(img_Car_Book);
        txtModelName.setText(modelname);

        sharedPreferences = getSharedPreferences("email_1", MODE_PRIVATE);
        eem = sharedPreferences.getString("email", "AAA");
        usersRef = FirebaseDatabase.getInstance().getReference("Admin").child("Users");
        usersRef.orderByChild("email").equalTo(eem).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    mob = userSnapshot.child("mobile").getValue(String.class);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle the error, if any.
            }
        });

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
                String format = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
                dp1.setText(sdf.format(calendar.getTime()));
                start_date = sdf.format(calendar.getTime());
            }
        };

        dp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Date_Book.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

        dp2 = findViewById(R.id.dp2);
        calendar2 = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year2, int month2, int dayOfMonth2) {
                calendar2.set(Calendar.YEAR, year2);
                calendar2.set(Calendar.MONTH, month2);
                calendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth2);

                e = dayOfMonth2;

                updateCalendar2();

                count = e - s;

                d1 = dp1.getText().toString();
                d2 = dp2.getText().toString();


                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

                try {
                    java.util.Date datee1 = simpleDateFormat.parse(d1);
                    java.util.Date datee2 = simpleDateFormat.parse(d2);

                    if (datee2.getDate() == calendar.get(Calendar.DAY_OF_MONTH)) {
                        Toast.makeText(Date_Book.this, "Selected day is not allowed", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    long different = (Math.abs(datee2.getTime()) - datee1.getTime());

                    long diffday = (different / (24 * 60 * 60 * 1000));
                    Log.i("Test", "Days" + diffday);
                    String diff = String.valueOf(diffday);

                    day.setText("DAYS : " + diff);
                    Intent in = getIntent();
                    String mmprice = in.getStringExtra("mprice");

                    rent.setText("RENT : " + mmprice);
                    int mmprice_1 = Integer.parseInt(mmprice);
                    long tot = diffday * mmprice_1;

                    ans = String.valueOf(tot);

                    total.setText("TOTAL : " + ans);

                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            }

            private void updateCalendar2() {
                String format = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
                dp2.setText(sdf.format(calendar2.getTime()));
                end_date = sdf.format(calendar2.getTime());
            }
        };

        dp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog2 = new DatePickerDialog(Date_Book.this, date2, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog2.getDatePicker().setMinDate(calendar.getTimeInMillis() + (24 * 60 * 60 * 1000));
                datePickerDialog2.show();
                sendEmail();
            }
        });

        Executor executor = ContextCompat.getMainExecutor(Date_Book.this);
        texttospeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i != TextToSpeech.ERROR) {
                    texttospeech.setLanguage(Locale.UK);
                }
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (d1 != null && d2 != null) {


                    DatabaseReference userReference = databaseReference_Admin.child("Payment").child("Income").child("Car_book").child("User");
                    userReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // Reset the slot number
                            long timestamp = System.currentTimeMillis();

                            // Construct the slot number using the timestamp
                            String slotNumber = String.valueOf(timestamp);

                            Intent inn = getIntent();
                            String rMO = inn.getStringExtra("rMo");
                            String mType = inn.getStringExtra("mType");
                            String mCom = inn.getStringExtra("mCom");
                            String pPoint = inn.getStringExtra("pPoint");
                            String dri = inn.getStringExtra("dri");

//                        int slt = slotNumber + 1;
//                        String sllt = String.valueOf(slt);


                            // Proceed with your data insertion logic using the unique slot number
                            payAdmin pay = new payAdmin(imageUrl, rMO, mob, modelname, d1, d2, ans, "false", slotNumber, "false", pPoint, dri,"false");

                            userReference.child(mob).child(modelname).child("slot" + slotNumber).setValue(pay);
                            databaseReference_Renter.child(rMO).child("Car").child(mType).child("Company").child(mCom).child(modelname).child("Details").child("slot" + slotNumber).setValue(pay);
                            databaseReference_Car.child("General").child(mType).child("Company").child(mCom).child(modelname).child("Details").child("slot" + slotNumber).setValue(pay);
                            databaseReference_General.child(mType).child("Company").child(mCom).child(modelname).child("Details").child("slot" + slotNumber).setValue(pay);

                            databaseReference.child(rMO).child("Car").child("Notifications").child("Company").setValue(mCom);
                            databaseReference.child(rMO).child("Car").child("Notifications").child("Type").setValue(mType);
                            databaseReference.child(rMO).child("Car").child("Notifications").child("Start_Date").setValue(start_date);
                            databaseReference.child(rMO).child("Car").child("Notifications").child("End_Date").setValue(end_date);
                            databaseReference.child(rMO).child("Car").child("Notifications").child("Total_Price").setValue(ans);

                            Intent in = getIntent();
                            String mmprice = in.getStringExtra("mprice");

                            Intent t = new Intent(Date_Book.this, Demo2.class);
                            t.putExtra("mmmprice", mmprice);
                            t.putExtra("uMob", mob);
                            t.putExtra("slot", slotNumber);
                            t.putExtra("modelName", modelname);
                            t.putExtra("sdate", d1);
                            t.putExtra("edate", d2);
                            t.putExtra("rMo", rMO);
                            t.putExtra("modelImageUrl", imageUrl);
                            t.putExtra("cType", mType);
                            t.putExtra("cCom", mCom);
                            t.putExtra("pPoint", pPoint);
                            t.putExtra("dri", dri);

                            BiometricManager biometricManager = BiometricManager.from(Date_Book.this);
                            switch (biometricManager.canAuthenticate()) {
                                case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                                    Toast.makeText(getApplicationContext(), "Device doesn't have fingerprints", Toast.LENGTH_SHORT).show();
                                    break;
                                case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                                    Toast.makeText(getApplicationContext(), "Biometric hardware is unavailable", Toast.LENGTH_SHORT).show();
                                    break;
                                case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                                    Toast.makeText(getApplicationContext(), "No fingerprint enrolled", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                            String getText = "Now you will have to pay a total of " + ans + " Rupees";
                            texttospeech.speak(getText, TextToSpeech.QUEUE_FLUSH, null);

                            biometricPrompt = new BiometricPrompt(Date_Book.this, executor, new BiometricPrompt.AuthenticationCallback() {
                                @Override
                                public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                                    super.onAuthenticationError(errorCode, errString);
                                }

                                @Override
                                public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                                    super.onAuthenticationSucceeded(result);
                                    Toast.makeText(Date_Book.this, ans, Toast.LENGTH_SHORT).show();
                                    t.putExtra("price", ans);
                                    t.putExtra("otp", ottp);
                                    if(ottp!=null)
                                    {
                                        startActivity(t);

                                    }
                                }

                                @Override
                                public void onAuthenticationFailed() {
                                    super.onAuthenticationFailed();
                                }
                            });

                            BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                                    .setTitle("Biometric Login")
                                    .setSubtitle("Authenticate to pay")
                                    .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG | BiometricManager.Authenticators.DEVICE_CREDENTIAL)
                                    .build();

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    // Your biometric authentication code here
                                    if (biometricPrompt != null) {
                                        biometricPrompt.authenticate(promptInfo);
                                    }
                                }
                            }, 3000);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Handle onCancelled
                        }
                    });
                } else {

                    if (d1 == null) {
                        Snackbar.make(txtModelName, "Please select a Starting Date", Snackbar.LENGTH_LONG);
                        Toast.makeText(Date_Book.this, "Please select a Starting Date", Toast.LENGTH_SHORT).show();
                    } else {
                        Snackbar.make(txtModelName, "Please select a Ending Date", Snackbar.LENGTH_LONG);
                        Toast.makeText(Date_Book.this, "Please select a Ending Date", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
//                        payAdmin pay = new payAdmin(imageUrl, rMO, mob, modelname, d1, d2, ans, "false", slotNumber, "false", pPoint, dri);
//
//                        userReference.child(mob).child(modelname).child("slot" + (slotNumber + 1)).setValue(pay);
//
//                        databaseReference_Renter.child(rMO).child("Car").child(mType).child("Company").child(mCom).child(modelname).child("Details").child("slot" + (slotNumber + 1)).setValue(pay);
//                        databaseReference_Car.child("General").child(mType).child("Company").child(mCom).child(modelname).child("Details").child("slot" + (slotNumber + 1)).setValue(pay);
//                        databaseReference_General.child(mType).child("Company").child(mCom).child(modelname).child("Details").child("slot" + (slotNumber + 1)).setValue(pay);
//
//                        databaseReference.child(rMO).child("Car").child("Notifications").child("Company").setValue(mCom);
//                        databaseReference.child(rMO).child("Car").child("Notifications").child("Type").setValue(mType);
//                        databaseReference.child(rMO).child("Car").child("Notifications").child("Start_Date").setValue(start_date);
//                        databaseReference.child(rMO).child("Car").child("Notifications").child("End_Date").setValue(end_date);
//                        databaseReference.child(rMO).child("Car").child("Notifications").child("Total_Price").setValue(ans);
//
//                        Intent in = getIntent();
//                        String mmprice = in.getStringExtra("mprice");
//
//
//                        Intent t = new Intent(Date_Book.this, Main_Payment.class);
//
//                        t.putExtra("mmmprice", mmprice);
//
//                        t.putExtra("uMob", mob);
//                        t.putExtra("slot", sllt);
//                        t.putExtra("modelName", modelname);
//                        t.putExtra("sdate", d1);
//                        t.putExtra("edate", d2);
//                        t.putExtra("rMo", rMO);
//                        t.putExtra("modelImageUrl", imageUrl);
//                        t.putExtra("cType", mType);
//                        t.putExtra("cCom", mCom);
//                        t.putExtra("pPoint", pPoint);
//                        t.putExtra("dri", dri);
//
//                        BiometricManager biometricManager = BiometricManager.from(Date_Book.this);
//                        switch (biometricManager.canAuthenticate()) {
//                            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
//                                Toast.makeText(getApplicationContext(), "Device Dosen`t have fingerprints", Toast.LENGTH_SHORT).show();
//                                break;
//                            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
//                                Toast.makeText(getApplicationContext(), "Not Working", Toast.LENGTH_SHORT).show();
//                                break;
//
//                            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
//                                Toast.makeText(getApplicationContext(), "No FingerPrint Assigned", Toast.LENGTH_SHORT).show();
//                                break;
//                        }
//                        String gettext = "Now you will have to pay total" + ans + " Rupees";
//                        texttospeech.speak(gettext, TextToSpeech.QUEUE_FLUSH, null);
//
//                        biometricPrompt = new BiometricPrompt(Date_Book.this, executor, new BiometricPrompt.AuthenticationCallback() {
//                            @Override
//                            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
//                                super.onAuthenticationError(errorCode, errString);
//                            }
//
//                            @Override
//                            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
//                                super.onAuthenticationSucceeded(result);
//                                Toast.makeText(Date_Book.this, ans, Toast.LENGTH_SHORT).show();
//                                t.putExtra("price", ans);
//                                startActivity(t);
//                            }
//
//                            @Override
//                            public void onAuthenticationFailed() {
//                                super.onAuthenticationFailed();
//                            }
//                        });
//
//                        promptInfo = new BiometricPrompt.PromptInfo.Builder()
//                                .setTitle("Biometric Login")
//                                .setSubtitle("Authenticate to pay")
//                                .setDeviceCredentialAllowed(true)
//                                .build();
//
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                // Your biometric authentication code here
//                                if (biometricPrompt != null) {
//                                    biometricPrompt.authenticate(promptInfo);
//                                }
//                            }
//                        }, 3000);
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                    }
//
//                });
//
//            }
//        });
    }

    @Override
    protected void onPause() {
        if (texttospeech != null) {
            texttospeech.stop();
            texttospeech.shutdown();
        }
        super.onPause();
    }

    private void sendEmail() {
        new EmailSender(Date_Book.this) {
            @Override
            protected void onPostExecute(String otp) {
                super.onPostExecute(otp);
                ottp = otp;
                // Do something with the OTP if needed
//                Toast.makeText(Date_Book.this, ottp, Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }
}
