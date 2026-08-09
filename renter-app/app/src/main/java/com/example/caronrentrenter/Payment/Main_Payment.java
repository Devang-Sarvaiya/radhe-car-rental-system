package com.example.caronrentrenter.Payment;

//import static androidx.core.app.NotificationCompatJellybean.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.caronrentrenter.BookHistory.bookAdmin;
import com.example.caronrentrenter.BookHistory.bookingHistory;
import com.example.caronrentrenter.Detail;
import com.example.caronrentrenter.Login;
import com.example.caronrentrenter.MainFragment;
import com.example.caronrentrenter.R;
import com.example.caronrentrenter.TNC.MainTNC;
import com.example.caronrentrenter.ThankYou;
import com.google.android.material.snackbar.Snackbar;
import com.google.api.LogDescriptor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.Locale;

public class Main_Payment extends AppCompatActivity implements PaymentResultListener {

    String price, mob, slt, isCon,isReqCan, modelName, startDate, endDate, rMo, modelImageUrl, cType, cCom, pPoint, dri, tot, aaaamt,isCan,rentPerDay;
    int pay;
    double to;
    String st;

    private DatabaseReference usersRef, usersRef1;
    TextView txtPrice, txtDriver, txtDeposit, txtGst, txtTotal, txt_read_more, txt_status;
    CheckBox checkBox;
    String terms, speech = "yes";
    private Button button,btnPayNow;
    TextToSpeech texttospeech;
    TextView read;
    boolean ad = false, check = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_payment);


        button = findViewById(R.id.button2);
        btnPayNow = findViewById(R.id.btnPayNow);

        txtPrice = findViewById(R.id.txt_price);
        txtDriver = findViewById(R.id.txtDriver);
        txtDeposit = findViewById(R.id.txt_deposit);
        txtGst = findViewById(R.id.txt_gst);
        txtTotal = findViewById(R.id.txt_amt);
        txt_status = findViewById(R.id.txt_status);
        checkBox = findViewById(R.id.checkBox);

        read = findViewById(R.id.txt_read_more);


        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Main_Payment.this, MainTNC.class));
            }
        });


        terms = "no";


        Checkout.preload(getApplicationContext());

        Intent i = getIntent();
        price = i.getStringExtra("price");
        mob = i.getStringExtra("uMob");
        slt = i.getStringExtra("slot");
        modelName = i.getStringExtra("modelName");
        startDate = i.getStringExtra("sdate");
        endDate = i.getStringExtra("edate");
        rMo = i.getStringExtra("rMo");
        modelImageUrl = i.getStringExtra("modelImageUrl");
        cType = i.getStringExtra("cType");
        cCom = i.getStringExtra("cCom");
        pPoint = i.getStringExtra("pPoint");
        dri = i.getStringExtra("dri");
        rentPerDay = i.getStringExtra("mmmprice");


        txtPrice.setText(price);
        txtDeposit.setText("10,000");

        pay = Integer.parseInt(price);

        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        double dr = pay - (pay * 5) / 100f;
        double dr1 = pay - dr;
        String drr = String.valueOf(dr1);
        String drr1 = decimalFormat.format(dr1);
        if (dri.equals("no")) {
            dr1 = 0;
            txtDriver.setText("0");
        } else {
            txtDriver.setText(drr1);
        }


        double gs = pay - (pay * 7) / 100f;
        double gs1 = pay - gs;
        String gst = String.valueOf(gs1);

        String gst2 = decimalFormat.format(gs1);
        txtGst.setText(gst2);

        to = pay + dr1 + gs1 + 10000f;
        System.out.println("/////////////////////////////**************************************************"+to);
        int ss=(int)(to);
        tot = String.valueOf(ss);

        txtTotal.setText(tot);
        int tt=Integer.parseInt(tot);
        tt=tt*100;
        st=String.valueOf(tt);
        aaaamt = tot;


//        pay1 = String.valueOf(pay);

//        int ta = Integer.parseInt(tot);
        to = to * 100;

//        pay = Integer.parseInt(price);
//
//
//        double dr = pay - (pay * 5) / 100f;
//        double dr1 = pay - dr;
//        String drr = String.valueOf(dr1);
//
//        if (dri.equals("no")) {
//            dr1 = 0;
//            txtDriver.setText("0");
//        } else {
//            txtDriver.setText(drr);
//        }
//
//
//        double gs = pay - (pay * 7) / 100f;
//        double gs1 = pay - gs;
//        String gst = String.valueOf(gs1);
//        txtGst.setText(gst);
//
//        to = pay + dr1 + gs1 + 10000f;
//        tot = String.valueOf(to);
//        txtTotal.setText(tot);
//        aaaamt = tot;
//zz
//
////        pay1 = String.valueOf(pay);
//
////        int ta = Integer.parseInt(tot);
//        to = to * 100;

        texttospeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i != TextToSpeech.ERROR) {
                    texttospeech.setLanguage(Locale.UK);
                }
            }
        });

        String gettext = "Thank you";
        texttospeech.speak(gettext, TextToSpeech.QUEUE_FLUSH, null);

        usersRef = FirebaseDatabase.getInstance().getReference("Admin").child("Payment").child("Income").child("Car_book").child("User").child(mob).child(modelName).child("slot" + slt);

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                isCon = dataSnapshot.child("isConfirmed").getValue(String.class);
                isReqCan = dataSnapshot.child("isRequestCanceld").getValue(String.class);
                isCan = dataSnapshot.child("isBooked").getValue(String.class);

                if (isCan.equals("false") && isReqCan.equals("false")) {
                    if (isCon.equals("true")) {
                        txt_status.setText("Your ride is confirmed");
                        ad = true;
                        call();
                    } else {
                        txt_status.setText("Wait for Confirmation");
                        button.setVisibility(View.GONE);
                    }

                }else {
                    txt_status.setText("Your ride is Cancelled");
                    btnPayNow.setVisibility(View.VISIBLE);

                }

//                if (isCon.equals("true")) {
//
//                    txt_status.setText("Your ride is confirmed");
//                    ad = true;
//                    call();
//                } else {
//                    txt_status.setText("Wait for Confirmation");
//                    button.setVisibility(View.GONE);
//                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }


        });


//        if (txt_status.getText().toString().equals("Your ride is confirmed")) {

        btnPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main_Payment.this, MainFragment.class));
            }
        });
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked && ad) {
                    // Checkbox is checked
                    terms = "yes";
//                    Toast.makeText(Main_Payment.this, "true", Toast.LENGTH_SHORT).show();
                    button.setVisibility(View.VISIBLE);
                    check = true;
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startPayment();
                            String gettext = "Thank you";
                            texttospeech.speak(gettext, TextToSpeech.QUEUE_FLUSH, null);
                        }
                    });
                } else if (isChecked) {
                    check = true;
                } else {
                    // Checkbox is unchecked
                    terms = "no";
//                    Toast.makeText(Main_Payment.this, "false", Toast.LENGTH_SHORT).show();

                    button.setVisibility(View.GONE);

                }
            }

        });
//        } else {
//            Snackbar.make(checkBox, "Please wait until your payment will be confirmed", Snackbar.LENGTH_LONG).show();
//        }

    }


    void call() {
        if (ad && check) {
            // Checkbox is checked
            terms = "yes";
//            Toast.makeText(Main_Payment.this, "true", Toast.LENGTH_SHORT).show();
            button.setVisibility(View.VISIBLE);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startPayment();
                    String gettext = "Thank you";
                    texttospeech.speak(gettext, TextToSpeech.QUEUE_FLUSH, null);
                }
            });
        } else {
            // Checkbox is unchecked
            terms = "no";
//            Toast.makeText(Main_Payment.this, "false", Toast.LENGTH_SHORT).show();

            button.setVisibility(View.GONE);

        }
    }


    private void startPayment() {

        /**
         * Instantiate Checkout
         */
        Checkout checkout = new Checkout();
        checkout.setKeyID("YOUR_RAZORPAY_KEY_ID"); // Razorpay Key ID — configure your own test/live key from the Razorpay dashboard
        /**
         * Set your logo here
         */
        checkout.setImage(R.drawable.web_logo);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            options.put("name", "RadheCar");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg");
            //  options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", st);//pass amount in currency subunits
            options.put("prefill.email", "YOUR_EMAIL@gmail.com");
            options.put("prefill.contact", "YOUR_PHONE_NUMBER");
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch (Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }


    @Override
    public void onPaymentSuccess(String s) {
        Log.d("ONSUCCESS", "onPaymentSuccess: " + s);

        usersRef = FirebaseDatabase.getInstance().getReference("Admin").child("Payment").child("Income").child("Car_book").child("User").child(mob).child(modelName).child("slot" + slt);
        usersRef.child("isBooked").setValue("true");

        bookAdmin pay = new bookAdmin(modelImageUrl, rMo, mob, modelName, startDate, endDate, aaaamt, pPoint, dri, "false", "false", "false", "slot" + slt,"false","false","false","false","false","false","false","false","false","false",rentPerDay);
        usersRef1 = FirebaseDatabase.getInstance().getReference("Admin").child("Booking").child("User").child(mob).child(modelName).child("slot" + slt);
        usersRef1.setValue(pay);
        usersRef1 = FirebaseDatabase.getInstance().getReference("Admin").child("Renters").child(rMo).child("Car").child(cType).child("Company").child(cCom).child(modelName).child("Details").child("Booking").child("slot" + slt);
        usersRef1.setValue(pay);
        usersRef1 = FirebaseDatabase.getInstance().getReference("Admin").child("Renters").child(rMo).child("Booking").child(modelName).child("slot" + slt);
        usersRef1.setValue(pay);
        usersRef1 = FirebaseDatabase.getInstance().getReference("Admin").child("Users").child(mob).child("Booking").child(modelName).child("slot" + slt);
        usersRef1.setValue(pay);


        //new Handler().postDelayed(new Runnable() {
            //@Override
            //public void run() {
                // Speak "Payment successful"
             //   speak("Payment successful");

                // Start the new activity after payment success=]
                Intent intent = new Intent(Main_Payment.this, bookingHistory.class);
                intent.putExtra("uMob", mob);
                intent.putExtra("yes", "yes");
                startActivity(intent);
                finish();
           // }
        //}, 3000);

        speak("Thank you");
//        Intent intent = new Intent(Main_Payment.this, Payment_History.class);
//        intent.putExtra("uMob", mob);
//        intent.putExtra("yes", "yes");
//        intent.putExtra("txtPrice", txtPrice.getText().toString());
//        intent.putExtra("txtDriver", txtDriver.getText().toString());
//        intent.putExtra("txtDeposit", txtDeposit.getText().toString());
//        intent.putExtra("txtGst", txtGst.getText().toString());
//        intent.putExtra("txtTotal", txtTotal.getText().toString());
//        startActivity(intent);
    }

    private void speak(String text) {
        texttospeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onPaymentError(int i, String s) {
        Log.d("ONERROR", "onPaymentError: " + s);
        usersRef = FirebaseDatabase.getInstance().getReference("Admin").child("Payment").child("Income").child("Car_book").child("User").child(mob).child(modelName).child("slot" + slt);
        usersRef.child("isBooked").setValue("false");
    }

    @Override
    protected void onPause() {
        if (texttospeech != null) {
            texttospeech.stop();
            texttospeech.shutdown();
        }
        super.onPause();
    }

}