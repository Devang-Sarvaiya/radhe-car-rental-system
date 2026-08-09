package com.example.caronrentrenter;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

// DriverID.java
public class DriverID extends AppCompatActivity {
    TextView tv;
    Button btn;
    String ID, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_id);

        tv = findViewById(R.id.tv);
        btn = findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Call the method to send email and receive ID and password
                sendEmail();
            }
        });
    }

    // Method to send email and receive ID and password
    private void sendEmail() {
        new DriverEmail(DriverID.this) {
            @Override
            protected void onPostExecute(String[] result) {
                super.onPostExecute(result);
                if (result != null && result.length == 2) {
                    // Assign the generated random ID and password to the respective variables
                    ID = result[0];
                    pass = result[1];
                    // Construct the message to display in the TextView
                    String message = "Generated ID: " + ID + "\nGenerated Password: " + pass;
                    // Set the message to the TextView
                    tv.setText(message);
                } else {
                    // Handle the case when there's an error or no result returned
//                    Log.e(TAG, "Error or no result returned from AsyncTask");
                }
            }
        }.execute();
    }
}

