package com.example.caronrent;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.caronrent.For_new_Project.date_selection;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class Login extends AppCompatActivity {

    SharedPreferences sharedPreferences,sharedPreferences1;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "emailShare";

    String emailShare,email_shared,eem;
    private static final String TAG = "Login";

    Button btn;
    TextView txtact,txt_forgotpass;
    TextInputEditText txtemail, txtpass;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        txt_forgotpass = findViewById(R.id.txt_forgotpass);
        System.out.println("------------------------"+emailShare);

        if (emailShare != null) {
            Intent intent = new Intent(Login.this, MainFragment.class);
            startActivity(intent);
        }
        btn = findViewById(R.id.btn_login);
        txtact = findViewById(R.id.txt_regis);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this, Loc.class);
                startActivity(i);
            }
        });

        txt_forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = txtemail.getText().toString();

                if (TextUtils.isEmpty(mail)) {
//                    Toast.makeText(Login.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                    txtemail.setError("Email is required");
                    txtemail.requestFocus();
                } else {
                    new MaterialAlertDialogBuilder(Login.this)
                            .setTitle("Password Alert")
                            .setMessage("Are you want to sure update your password?")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Code to handle OK button click
                                    updatePassword(mail);
                                }
                            })

                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Code to handle Cancel button click
                                    // You can add any actions you want here
                                    dialog.dismiss(); // Close the dialog
                                }
                            })
                            .setCancelable(false)
                            .show();

                }
            }
        });
        txtact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this, SignUp.class);
                startActivity(i);
            }
        });
        sharedPreferences1 = getSharedPreferences("email_1", MODE_PRIVATE);
        eem = sharedPreferences1.getString("email","");

        txtemail = findViewById(R.id.txt_email_login);
        txtpass = findViewById(R.id.txt_login_pass);

        auth = FirebaseAuth.getInstance();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtEmail = txtemail.getText().toString();
                String txtPass = txtpass.getText().toString();
                email_shared = txtemail.getText().toString();

                sharedPreferences = getSharedPreferences("email_1", MODE_PRIVATE);

                SharedPreferences.Editor sedit = sharedPreferences.edit();
                sedit.putString("email", email_shared);
                System.out.println("/////////"+email_shared);
                sedit.apply();
                sedit.commit();

                sharedPreferences1 = getSharedPreferences("email_1", MODE_PRIVATE);
                eem = sharedPreferences1.getString("email","");

                if (TextUtils.isEmpty(txtEmail)) {
                    Toast.makeText(Login.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                    txtemail.setError("Email is required");
                    txtemail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(txtEmail).matches()) {
                    Toast.makeText(Login.this, "Please Re-Enter Email", Toast.LENGTH_SHORT).show();
                    txtemail.setError("Valid Email is required");
                    txtemail.requestFocus();
                } else if (TextUtils.isEmpty(txtPass)) {
                    Toast.makeText(Login.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                    txtpass.setError("Password is required");
                    txtpass.requestFocus();
                } else {
                    loginuser(txtEmail, txtPass);
                }
            }
        });
    }

    private void loginuser(String txtEmail, String txtPass) {
        auth.signInWithEmailAndPassword(txtEmail, txtPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    if (firebaseUser.isEmailVerified()) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(KEY_NAME, txtemail.getText().toString());
                        editor.apply();
                        Intent intent = new Intent(Login.this, MainFragment.class);
                        intent.putExtra("email", txtemail.getText().toString());
                        startActivity(intent);
                        Toast.makeText(Login.this, "Logged In", Toast.LENGTH_SHORT).show();

                    } else {
                        firebaseUser.sendEmailVerification();
                        auth.signOut();
                        showAlertDialog();
                    }
                } else {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        txtemail.setError("User does not exist or is no longer valid.Please register again");
                        txtemail.requestFocus();
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        txtemail.setError("Invalid credentials.Kindly check and re-enter");
                        txtemail.requestFocus();
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
        builder.setTitle("Email Not Verified");
        builder.setMessage("Please verify your email now. You can not login without email verification");

        builder.setPositiveButton("CONTINUE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        AlertDialog alertDialog = builder.create();

        alertDialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (auth.getCurrentUser() != null && !Objects.equals(eem, "")) {
            System.out.println("///////////////////////////////////  "+eem);


            System.out.println("///////////////////////////////////  2st login");

            Intent intent = new Intent(Login.this, MainFragment.class);
            startActivity(intent);
            finish();
        } else {
        }
    }
    private void updatePassword(String email) {
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Login.this, "Password reset email sent. Please check your email.", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(Login.this, "Failed to send password reset email. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}