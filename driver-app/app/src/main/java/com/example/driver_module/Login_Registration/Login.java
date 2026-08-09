package com.example.driver_module.Login_Registration;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.driver_module.Live_location.Location_info;
import com.example.driver_module.MainActivity;
import com.example.driver_module.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
import java.util.concurrent.Executor;

public class Login extends AppCompatActivity {
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "emailShare";
    private static final String TAG = "Login";
    SharedPreferences sharedPreferences, sharedPreferences1;
    String emailShare, email_shared, eem, email, txtEmail, txtPass, id, pass, selectedOption;
    AppCompatButton btn;
    TextView txtact, txt_forgotpass;
    TextInputEditText txtemail, txtpass;
    FirebaseAuth auth;
    boolean ramu = false;
    private DatabaseReference usersRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
//        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
//        emailShare = sharedPreferences.getString(KEY_NAME, null);

        btn = findViewById(R.id.btn_login);
//        txtact = findViewById(R.id.txt_regis);
//        txt_forgotpass = findViewById(R.id.txt_forgotpass);

        txtemail = findViewById(R.id.txt_email_login);
        txtpass = findViewById(R.id.txt_login_pass);

        auth = FirebaseAuth.getInstance();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtEmail = txtemail.getText().toString().trim();
                txtPass = txtpass.getText().toString().trim();

                usersRef = FirebaseDatabase.getInstance().getReference("Admin").child("Drivers").child("General");

                // Retrieve the user's image URL
                usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        boolean found = false;
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            email = userSnapshot.child("email").getValue(String.class);
                            id = userSnapshot.child("driver_id").getValue(String.class);
                            pass = userSnapshot.child("driver_pass").getValue(String.class);

                            if (txtEmail.equals(id) && txtPass.equals(pass)) {
                                found = true;
                                break;
                            }
                        }

                        if (found) {
                            sharedPreferences = getSharedPreferences("email_1", MODE_PRIVATE);
                            SharedPreferences.Editor sedit = sharedPreferences.edit();
                            sedit.putString("email", email);
                            sedit.apply();
//                            sedit.apply();
                            startActivity(new Intent(Login.this, MainActivity.class));
                        } else {
                            Toast.makeText(Login.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle the error, if any.
                    }
                });
            }
        });
    }
}
