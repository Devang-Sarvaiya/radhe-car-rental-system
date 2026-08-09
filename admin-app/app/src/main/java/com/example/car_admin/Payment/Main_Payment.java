package com.example.car_admin.Payment;

//import static androidx.core.app.NotificationCompatJellybean.TAG;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.car_admin.BookHistory.bookAdmin;
import com.example.car_admin.R;
//import com.example.car_admin.ThankYou;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class Main_Payment extends AppCompatActivity implements PaymentResultListener {

    String price,mob,payy,mName,slot,status,side,rMob;
    private bookAdmin object;
    private DatabaseReference usersRef, usersRef1,usersRef2,usersRef3;

    double pay;



    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_payment);

        object = (bookAdmin) getIntent().getSerializableExtra("object");
        mob = object.getUserMobile();
        rMob = object.getRenterMobile();

        mName = object.getCarModelName();
        slot = object.getSlot();

//        button = findViewById(R.id.button2);


//        String pay = object.getTotalAmount(
        Checkout.preload(getApplicationContext());

        Intent i=getIntent();
        price=i.getStringExtra("amt");
        payy=i.getStringExtra("pay");
//
//        int ppay = Integer.parseInt(price);
        pay=Double.parseDouble(price);

        pay=pay*100;

        if(payy.equals("renter")){
            status = "yes";
            side = "isRenterPaymentDone";
        }
        if(payy.equals("deposit")){
            status = "yes";
            side = "isDepositPaymentDone";
        }
        if(payy.equals("driver")){
            status = "yes";
            side = "isDriverPaymentDone";
        }
        if(payy.equals("user")){
            status = "yes";
            side = "isRefundDone";
        }


        startPayment();





//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startPayment();
//            }
//        });
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
            options.put("amount", pay);//pass amount in currency subunits
            options.put("prefill.email", "YOUR_EMAIL@gmail.com");
            options.put("prefill.contact","YOUR_PHONE_NUMBER");
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Log.d("ONSUCCESS","onPaymentSuccess: " + s);
        Toast.makeText(this, "Payment Done", Toast.LENGTH_SHORT).show();

//        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Admin").child("Booking").child("User").child(mob).child(mName).child(slot);
//        db.child(side).setValue(status);

        usersRef1 = FirebaseDatabase.getInstance().getReference("Admin").child("Booking").child("User").child(mob).child(mName).child(slot);
        usersRef1.child(side).setValue(status);

        usersRef2 = FirebaseDatabase.getInstance().getReference("Admin").child("Renters").child(rMob).child("Booking").child(mName).child(slot);
        usersRef2.child(side).setValue(status);

        usersRef3 = FirebaseDatabase.getInstance().getReference("Admin").child("Users").child(mob).child("Booking").child(mName).child(slot);
        usersRef3.child(side).setValue(status);



        if(object.getIsSelfCanceld().equals("true")){
            usersRef1 = FirebaseDatabase.getInstance().getReference("Admin").child("Booking").child("User").child(mob).child(mName).child(slot);
            usersRef1.child("isRefund").setValue("true");
            usersRef2 = FirebaseDatabase.getInstance().getReference("Admin").child("Renters").child(rMob).child("Booking").child(mName).child(slot);
            usersRef2.child("isRefund").setValue("true");

            usersRef3 = FirebaseDatabase.getInstance().getReference("Admin").child("Users").child(mob).child("Booking").child(mName).child(slot);
            usersRef3.child("isRefund").setValue("true");
            finish();
        }else{
            usersRef1 = FirebaseDatabase.getInstance().getReference("Admin").child("Booking").child("User").child(mob).child(mName).child(slot);
            usersRef1.child("isRefundDone").setValue("true");
            usersRef2 = FirebaseDatabase.getInstance().getReference("Admin").child("Renters").child(rMob).child("Booking").child(mName).child(slot);
            usersRef2.child("isRefundDone").setValue("true");

            usersRef3 = FirebaseDatabase.getInstance().getReference("Admin").child("Users").child(mob).child("Booking").child(mName).child(slot);
            usersRef3.child("isRefundDone").setValue("true");
            finish();
        }


        finish();



//        startActivity(new Intent(Main_Payment.this, ThankYou.class));
    }

    @Override
    public void onPaymentError(int i, String s) {
        Log.d("ONERROR", "onPaymentError: "+ s);
    }
}