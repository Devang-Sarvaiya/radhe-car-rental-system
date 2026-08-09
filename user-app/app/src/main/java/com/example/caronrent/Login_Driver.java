package com.example.caronrent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.caronrent.Live_location.Location_info;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login_Driver extends AppCompatActivity {

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
        setContentView(R.layout.activity_login_deriver);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        emailShare = sharedPreferences.getString(KEY_NAME, null);

        btn = findViewById(R.id.btn_login);
        txtact = findViewById(R.id.txt_regis);
        txt_forgotpass = findViewById(R.id.txt_forgotpass);

        sharedPreferences1 = getSharedPreferences("email_1", MODE_PRIVATE);
        eem = sharedPreferences1.getString("email", "");

        txtemail = findViewById(R.id.txt_email_login);
        txtpass = findViewById(R.id.txt_login_pass);

        auth = FirebaseAuth.getInstance();




        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                txtEmail = txtemail.getText().toString();
                txtPass = txtpass.getText().toString();

                usersRef = FirebaseDatabase.getInstance().getReference("Admin").child("Drivers").child("General");

                // Retrieve the user's image URL
                usersRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            email = userSnapshot.child("email").getValue(String.class);
                            id = userSnapshot.child("driver_id").getValue(String.class);
                            pass = userSnapshot.child("driver_pass").getValue(String.class);

                            if (txtEmail.equals(id) && txtPass.equals(pass)) {
                                sharedPreferences = getSharedPreferences("email_1", MODE_PRIVATE);
                                SharedPreferences.Editor sedit = sharedPreferences.edit();
                                sedit.putString("email", email);
                                sedit.apply();
                                Toast.makeText(Login_Driver.this, email, Toast.LENGTH_SHORT).show();
                                ramu = true;
                                break;
                            }
                        }

                        if (ramu == true) {
                            startActivity(new Intent(Login_Driver.this, MainFragment.class));
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