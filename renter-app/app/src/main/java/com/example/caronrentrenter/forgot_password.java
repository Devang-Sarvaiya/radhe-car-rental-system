package com.example.caronrentrenter;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgot_password extends AppCompatActivity {

    EditText new_pass, confirm_pass;
    Button submit;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // Initialize Firebase Authentication
        auth = FirebaseAuth.getInstance();

        new_pass = findViewById(R.id.edtpass);
        confirm_pass = findViewById(R.id.edtcpass);
        submit = findViewById(R.id.btn_login);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = getIntent().getStringExtra("email");
                String newPassword = confirm_pass.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(forgot_password.this, "Email address is required.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(newPassword)) {
                    Toast.makeText(forgot_password.this, "Please enter a new password.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Additional validation for password length, etc., can be added here

                updatePassword(email, newPassword);
            }
        });
    }

    private void updatePassword(String email, String newPassword) {
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(forgot_password.this, "Password reset email sent. Please check your email.", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(forgot_password.this, "Failed to send password reset email. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

