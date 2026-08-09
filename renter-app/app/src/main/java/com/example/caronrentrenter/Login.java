package com.example.caronrentrenter;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.example.caronrentrenter.For_new_Project.date_selection;
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
import java.util.concurrent.Executor;

public class Login extends AppCompatActivity {

    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "emailShare";
    private static final String TAG = "Login";
    SharedPreferences sharedPreferences, sharedPreferences1;
    String emailShare, email_shared, eem;
    AppCompatButton btn;
    TextView txtact, txt_forgotpass;
    TextInputEditText txtemail, txtpass;
    FirebaseAuth auth;
    BiometricPrompt biometricPrompt;
    BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        emailShare = sharedPreferences.getString(KEY_NAME, null);

        btn = findViewById(R.id.btn_login);
        txtact = findViewById(R.id.txt_regis);
        txt_forgotpass = findViewById(R.id.txt_forgotpass);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this, Loc.class);
                startActivity(i);
            }
        });

        txtact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this, SignUp.class);
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

        sharedPreferences1 = getSharedPreferences("email_1", MODE_PRIVATE);
        eem = sharedPreferences1.getString("email", "");

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
                sedit.apply();

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
                        String newPassword = txtpass.getText().toString();
                        firebaseUser.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> updateTask) {
                                if (updateTask.isSuccessful()) {
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString(KEY_NAME, txtemail.getText().toString());
                                    editor.apply();

                                    BiometricManager biometricManager = BiometricManager.from(Login.this);
                                    switch (biometricManager.canAuthenticate()) {
                                        case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                                            Toast.makeText(getApplicationContext(), "Device doesn't have fingerprints", Toast.LENGTH_SHORT).show();
                                            break;
                                        case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                                            Toast.makeText(getApplicationContext(), "Biometric hardware is unavailable", Toast.LENGTH_SHORT).show();
                                            break;
                                        case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                                            Toast.makeText(getApplicationContext(), "No biometric enrolled. Please check security settings", Toast.LENGTH_SHORT).show();
                                            break;
                                        default:
                                            Executor executor = ContextCompat.getMainExecutor(Login.this);
                                            biometricPrompt = new BiometricPrompt(Login.this, executor, new BiometricPrompt.AuthenticationCallback() {
                                                @Override
                                                public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                                                    super.onAuthenticationError(errorCode, errString);
                                                    // Handle authentication errors
                                                    Toast.makeText(Login.this, "Authentication error: " + errString, Toast.LENGTH_SHORT).show();
                                                }

                                                @Override
                                                public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                                                    super.onAuthenticationSucceeded(result);
                                                    // Authentication succeeded, start next activity
                                                    startActivity(new Intent(Login.this, MainFragment.class));
                                                }

                                                @Override
                                                public void onAuthenticationFailed() {
                                                    super.onAuthenticationFailed();
                                                    // Handle authentication failures
//                                                    Toast.makeText(Login.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                            BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                                                    .setTitle("Biometric Login")
                                                    .setSubtitle("Authenticate to pay")
                                                    .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG | BiometricManager.Authenticators.DEVICE_CREDENTIAL)
                                                    .build();

                                            // Show biometric prompt
                                            biometricPrompt.authenticate(promptInfo);

                                            break;
                                    }
                                } else {
                                    Log.e(TAG, "Error updating password: " + updateTask.getException().getMessage());
                                    Toast.makeText(Login.this, "Error updating password. Please try again.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        firebaseUser.sendEmailVerification();
                        auth.signOut();
                        showAlertDialog();
                    }
                } else {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        txtemail.setError("User does not exist or is no longer valid. Please register again");
                        txtemail.requestFocus();
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        if (e.getErrorCode().equals("ERROR_WRONG_PASSWORD")) {
                            Toast.makeText(Login.this, "Incorrect password. Please try again.", Toast.LENGTH_SHORT).show();
                        } else {
                            txtemail.setError("Invalid credentials. Kindly check and re-enter");
                            txtemail.requestFocus();
                        }
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
            System.out.println("****************************///////////////////////////////////  " + eem);


            Toast.makeText(this, "You are already logged in", Toast.LENGTH_SHORT).show();
            System.out.println("****************************///////////////////////////////////  2nd login");

            Intent intent = new Intent(Login.this, date_selection.class);
            startActivity(intent);
            finish();
        } else {
            //Toast.makeText(this, "You can login now!", Toast.LENGTH_SHORT).show();
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
    private void showAlertDialog1() {
        new MaterialAlertDialogBuilder(this, R.style.AlertDialogTheme)
                .setTitle("Renter Alert")
                .setMessage("So sorry, This Renter is unavailable,Please find another")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Code to handle OK button click
//                        dialog.dismiss(); // Close the dialog
                        finish();
                    }
                })
                .setCancelable(false)
                .show();
    }
}