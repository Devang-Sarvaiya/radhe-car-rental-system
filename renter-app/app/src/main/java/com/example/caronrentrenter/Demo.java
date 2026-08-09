// Demo.java
package com.example.caronrentrenter;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Demo extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        button = findViewById(R.id.button3);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail();
            }
        });
    }

    private void sendEmail() {
        new EmailSender(Demo.this) {
            @Override
            protected void onPostExecute(String otp) {
                super.onPostExecute(otp);
                // Do something with the OTP if needed
            }
        }.execute();
    }
}
