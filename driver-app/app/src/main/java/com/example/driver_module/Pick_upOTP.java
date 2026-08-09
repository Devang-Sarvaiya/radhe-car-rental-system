package com.example.driver_module;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.telephony.SmsManager;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.pm.PackageManager;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;
import java.util.Random;

public class Pick_upOTP extends AppCompatActivity {
    EditText editText;
    AppCompatButton button;
    String senderPhoneNumber = "+919000000000"; // Configure your own SMS gateway/sender number
    // Pick-up point contact numbers — replace with your own. Original personal numbers redacted.
    String[] recipientPhoneNumbers = {"+919000000001", "+919000000002", "+919000000003", "+919000000004", "+919000000005", "+919000000006"};
    String message, pickup = "b"; // Message will contain OTP generated later
    String otpp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pick_up_otp);

        button = findViewById(R.id.button2);
        editText = findViewById(R.id.editTextPhone2);

        requestPermissions(new String[]{android.Manifest.permission.SEND_SMS}, 1);

        Intent in = getIntent();
        pickup = in.getStringExtra("pick");


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                // Permission already granted, generate OTP and send the SMS
                generateAndSendOTP();
            } else {
                // Request permission
                requestPermissions(new String[]{android.Manifest.permission.SEND_SMS}, 1);
            }
        } else {
            // No need to request permission for versions below Marshmallow
//            generateAndSendOTP();
        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String ottp = editText.getText().toString();

                if (ottp.isEmpty()) {
                    Snackbar.make(button, "Please enter an OTP", Snackbar.LENGTH_LONG).show();

                } else if (ottp.equals(otpp)) {
                    Intent intent = new Intent(Pick_upOTP.this, PickUp_detail.class);
                    intent.putExtra("pick", pickup);
                    startActivity(intent);
                    finish();

                } else {
                    Snackbar.make(button, "OTP not match!!!", Snackbar.LENGTH_LONG).show();

                }

            }
        });
    }

    private void generateAndSendOTP() {
        // Generate a random OTP
        otpp = generateOTP();

        // Set the OTP as the message
        message = "Your OTP for log in as Pick-up Point checker : " + otpp;

        // Send the SMS

        if (Objects.equals(pickup, "A-011,Ambani Complex,Varachha,Surat")) {
            recipientPhoneNumbers = new String[]{"+919000000001"};
        } else if (Objects.equals(pickup, "B-022,Chitra Enterprise,Nana varachha,Surat")) {
            recipientPhoneNumbers = new String[]{"+919000000002"};

        } else if (Objects.equals(pickup, "C-033,Kalyan Complex,Katargam,Surat")) {
            recipientPhoneNumbers = new String[]{"+919000000004"};

        } else if (Objects.equals(pickup, "D-044,Rajhans Complex,Kamrej,Surat")) {
            recipientPhoneNumbers = new String[]{"+919000000005"};

        } else {
            recipientPhoneNumbers = new String[]{"+919000000006"};
        }
        sendSMS(senderPhoneNumber, recipientPhoneNumbers, message);
    }

    private String generateOTP() {
        // Length of the OTP
        int length = 6;

        // Characters to be used for generating OTP
        String numbers = "0123456789";

        // Using StringBuilder to efficiently append characters
        StringBuilder otp = new StringBuilder();

        // Create a random object
        Random random = new Random();

        // Generate OTP of specified length
        for (int i = 0; i < length; i++) {
            // Generate a random index between 0 and length of characters
            int index = random.nextInt(numbers.length());

            // Append the character at the generated index to OTP
            otp.append(numbers.charAt(index));
        }

        // Convert StringBuilder to String and return OTP
        return otp.toString();
    }

    private void sendSMS(String senderPhoneNumber, String[] recipientPhoneNumbers, String message) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            for (String recipientPhoneNumber : recipientPhoneNumbers) {
                smsManager.sendTextMessage(recipientPhoneNumber, senderPhoneNumber, message, null, null);
                Log.d("SMS", "SMS sent to " + recipientPhoneNumber);
            }
            Toast.makeText(this, "SENT", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("SMS", "Failed to send SMS", e);
            Toast.makeText(this, "Failed to send SMS", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, generate OTP and send the SMS
//                generateAndSendOTP();
            } else {
                // Permission denied, show a message or take appropriate action
                Toast.makeText(this, "Permission denied to send SMS", Toast.LENGTH_SHORT).show();
            }
        }
    }
}