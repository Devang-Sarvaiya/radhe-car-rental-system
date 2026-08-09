package com.example.caronrent.Payment;

//import static androidx.core.app.NotificationCompatJellybean.TAG;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.caronrent.R;
import com.example.caronrent.ThankYou;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class Main_Payment extends AppCompatActivity implements PaymentResultListener {

    String price;
    int pay;


    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_payment);

//        button = findViewById(R.id.button2);

        Checkout.preload(getApplicationContext());

        Intent i=getIntent();
        price=i.getStringExtra("totalAmt");

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
        startActivity(new Intent(Main_Payment.this, ThankYou.class));
    }

    @Override
    public void onPaymentError(int i, String s) {
        Log.d("ONERROR", "onPaymentError: "+ s);
    }
}