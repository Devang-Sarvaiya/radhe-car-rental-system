package com.example.caronrentrenter.Extend;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.caronrentrenter.BookHistory.bookAdmin;
import com.example.caronrentrenter.BookHistory.bookingHistory;
import com.example.caronrentrenter.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class Extend_new_payment extends AppCompatActivity implements PaymentResultListener {

    String price,rMob,uMob,carName,slot,endDate;
    int pay;
    private DatabaseReference usersRef, usersRef1,usersRef2,usersRef3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_extend_new_payment);

        Checkout.preload(getApplicationContext());

        Intent i=getIntent();
        price=i.getStringExtra("totalAmt");
        rMob=i.getStringExtra("rMob");
        uMob=i.getStringExtra("uMob");
        carName=i.getStringExtra("carName");
        slot=i.getStringExtra("slot");
        endDate=i.getStringExtra("newdate");

        pay=Integer.parseInt(price);

        pay=pay*100;
//

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
//        startActivity(new Intent(Extend_new_payment.this, ThankYou.class));

//        usersRef = FirebaseDatabase.getInstance().getReference("Admin").child("Payment").child("Income").child("Car_book").child("User").child(mob).child(modelName).child("slot" + slt);
//        usersRef.child("isBooked").setValue("true");

//        bookAdmin pay = new bookAdmin(modelImageUrl, rMo, mob, modelName, startDate, endDate, aaaamt, pPoint, dri, "false", "false", "false", "slot" + slt,"false","false","false","false","false","false","false","false","false","false",rentPerDay);
        usersRef1 = FirebaseDatabase.getInstance().getReference("Admin").child("Booking").child("User").child(uMob).child(carName).child(slot);
        usersRef1.child("endDate").setValue(endDate);
        usersRef1.child("isExtended").setValue("true");
        usersRef1.child("isUpdated").setValue("true");
//        usersRef1 = FirebaseDatabase.getInstance().getReference("Admin").child("Renters").child(rMob).child("Car").child(cType).child("Company").child(cCom).child(modelName).child("Details").child("Booking").child("slot" + slt);
//        usersRef1.setValue(pay);
        usersRef2 = FirebaseDatabase.getInstance().getReference("Admin").child("Renters").child(rMob).child("Booking").child(carName).child(slot);
        usersRef2.child("endDate").setValue(endDate);
        usersRef2.child("isExtended").setValue("true");
        usersRef2.child("isUpdated").setValue("true");


        usersRef3 = FirebaseDatabase.getInstance().getReference("Admin").child("Users").child(uMob).child("Booking").child(carName).child(slot);
        usersRef3.child("endDate").setValue(endDate);
        usersRef3.child("isExtended").setValue("true");
        usersRef3.child("isUpdated").setValue("true");

        startActivity(new Intent(Extend_new_payment.this, bookingHistory.class));
        finish();





    }

    @Override
    public void onPaymentError(int i, String s) {
        Log.d("ONERROR", "onPaymentError: "+ s);
    }
}