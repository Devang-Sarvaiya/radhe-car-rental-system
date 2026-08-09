package com.example.caronrentrenter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfile extends AppCompatActivity {

    private static final String TAG = "EditProfile";
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "emailShare";
    final private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Admin").child("Users");
    Button saveButton;
    String name, email, pass, mob, city, driving, gender;
    ImageView imgProfile, back_btn;
    EditText editName;
    SharedPreferences sharedPreferences;
    TextView txtName;
    String emailShare, eem;
    ImageView showProfilePic;

    Uri imageUri;
    String nameUser, emailUser, usernameUser, passwordUser, image;
    private DatabaseReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        sharedPreferences = getSharedPreferences("email_1", MODE_PRIVATE);

        eem = sharedPreferences.getString("email", "AAA");

        String demo = eem;

        saveButton = findViewById(R.id.saveButton);
//        txtName = findViewById(R.id.editName);
        editName = findViewById(R.id.editName);
//        editEmail = findViewById(R.id.editEmail);
//        editPassword = findViewById(R.id.editPassword);
//        editMobile = findViewById(R.id.editMobile);
//        editCity = findViewById(R.id.editCity);
//        editDriving = findViewById(R.id.editDriving);
//        editGender = findViewById(R.id.editGender);
        back_btn = findViewById(R.id.back_btn);
        showProfilePic = findViewById(R.id.showProfilePic);

//        editEmail.setText(demo.toString());
        String desiredUsername = demo;

        usersRef = FirebaseDatabase.getInstance().getReference("Admin").child("Users");

        // Retrieve the user's image URL
        usersRef.orderByChild("email").equalTo(desiredUsername).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String imageUrl = userSnapshot.child("imageURLUser").getValue(String.class);

                    Glide.with(EditProfile.this).load(imageUrl).into(showProfilePic);

                    name = userSnapshot.child("name").getValue(String.class);
                    email = userSnapshot.child("email").getValue(String.class);
                    pass = userSnapshot.child("pass").getValue(String.class);
                    mob = userSnapshot.child("mobile").getValue(String.class);
                    city = userSnapshot.child("city").getValue(String.class);
                    driving = userSnapshot.child("dll").getValue(String.class);
                    gender = userSnapshot.child("gender").getValue(String.class);


//                    role = userSnapshot.child("role").getValue(String.class);

                    // Now you can use the imageUrl in your app, e.g., to load the image using an image loading library like Glide or Picasso.
                }


                editName.setText(name);
//                editPassword.setText(pass);
//                editMobile.setText(mob);
//                editCity.setText(city);
//                editDriving.setText(driving);
//                editGender.setText(gender);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle the error, if any.
            }
        });
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditProfile.this, Profile_Ui.class));
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNameChanged()) {
                    Toast.makeText(EditProfile.this, "Saved", Toast.LENGTH_SHORT).show();

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("email");
                    editor.apply();
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(EditProfile.this, Login.class));


                    finishAffinity();
                } else {
                    Toast.makeText(EditProfile.this, "No Changes Found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isNameChanged() {
        if (!name.equals(editName.getText().toString())) {
            databaseReference.child(mob).child("name").setValue(editName.getText().toString());
            name = editName.getText().toString();
            return true;
        } else {
            return false;
        }
    }

//    private boolean isEmailChanged() {
//        if (!email.equals(editEmail.getText().toString())) {
//            databaseReference.child(mob).child("email").setValue(editEmail.getText().toString());
//            email = editEmail.getText().toString();
//            return true;
//        } else {
//            return false;
//        }
//    }

//    private boolean isPassChanged() {
//        if (!pass.equals(editPassword.getText().toString())) {
//            databaseReference.child(mob).child("pass").setValue(editPassword.getText().toString());
//            pass = editPassword.getText().toString();
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    private boolean isMobChanged() {
//        if (!mob.equals(editMobile.getText().toString())) {
//            databaseReference.child(mob).child("mobile").setValue(editMobile.getText().toString());
//            mob = editMobile.getText().toString();
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    private boolean isCityChanged() {
//        if (!city.equals(editCity.getText().toString())) {
//            databaseReference.child(mob).child("city").setValue(editCity.getText().toString());
//            city = editCity.getText().toString();
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    private boolean isDllChanged() {
//        if (!driving.equals(editDriving.getText().toString())) {
//            databaseReference.child(mob).child("dll").setValue(editDriving.getText().toString());
//            driving = editDriving.getText().toString();
//            return true;
//        } else {
//            return false;
//        }
//    }

//    private boolean isGenderChanged() {
//        if (!gender.equals(editGender.getText().toString())) {
//            databaseReference.child(mob).child("gender").setValue(editGender.getText().toString());
//            gender = editGender.getText().toString();
//            return true;
//        } else {
//            return false;
//        }
//    }


//    private boolean isEmailChanged() {
//        if (!emailUser.equals(editEmail.getText().toString())){
//            databaseReference.child(usernameUser).child("email").setValue(editEmail.getText().toString());
//            emailUser = editEmail.getText().toString();
//            return true;
//        } else {
//            return false;
//        }
//    }
//    private boolean isPasswordChanged() {
//        if (!passwordUser.equals(editPassword.getText().toString())){
//            databaseReference.child(usernameUser).child("password").setValue(editPassword.getText().toString());
//            passwordUser = editPassword.getText().toString();
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    private boolean isMobileChanged() {
//        if (!usernameUser.equals(editUsername.getText().toString())){
//            databaseReference.child(usernameUser).child("username").setValue(editUsername.getText().toString());
//            usernameUser = editUsername.getText().toString();
//            return true;
//        } else {
//            return false;
//        }
//    }
//    public void showData(){
//        Intent intent = getIntent();
//        nameUser = intent.getStringExtra("uname");
//        emailUser = intent.getStringExtra("uEmail");
//        usernameUser = intent.getStringExtra("uUname");
//        passwordUser = intent.getStringExtra("uPwd");
//
//        editName.setText(nameUser);
//        editEmail.setText(emailUser);
////        editUsername.setText(usernameUser);
//        editPassword.setText(passwordUser);
//    }


}