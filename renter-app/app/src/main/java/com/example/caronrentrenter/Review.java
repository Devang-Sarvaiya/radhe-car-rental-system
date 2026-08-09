package com.example.caronrentrenter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Review extends AppCompatActivity {

    private EditText editTextReview;
    private Button buttonSubmit;
    TextView textViewStayAnonymous;
    SharedPreferences sharedPreferences1;
    private DatabaseReference usersRef;
    private String text = "Your Reviews will improve our system.  ", eem, mob, name;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        textViewStayAnonymous = findViewById(R.id.textView5);
        usersRef = FirebaseDatabase.getInstance().getReference("Admin").child("Users");

        sharedPreferences1 = getSharedPreferences("email_1", MODE_PRIVATE);
        eem = sharedPreferences1.getString("email", "");

//        Toast.makeText(this, eem, Toast.LENGTH_SHORT).show();
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            int startIndex = 0;

            @Override
            public void run() {
                // Remove the first letter from the text
                String newText = text.substring(startIndex) + text.substring(0, startIndex);

                // Update the TextView with the new text
                textViewStayAnonymous.setText(newText);

                // Increase the startIndex for the next iteration
                startIndex = (startIndex + 1) % text.length();

                // Schedule the next iteration after a delay
                handler.postDelayed(this, 200); // Change the delay as needed (in milliseconds)
            }
        }, 0);
        editTextReview = findViewById(R.id.editTextReview);
        buttonSubmit = findViewById(R.id.buttonSubmit);


        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitReview();
            }
        });


        usersRef.orderByChild("email").equalTo(eem).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {

                    name = userSnapshot.child("name").getValue(String.class);
                    mob = userSnapshot.child("mobile").getValue(String.class);

//                    Toast.makeText(Review.this, name, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(Review.this, mob, Toast.LENGTH_SHORT).show();

                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Admin").child("Reviews").child(mob).child(name);


                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle the error, if any.
            }
        });




    }


    private void submitReview() {
        String reviewText = editTextReview.getText().toString().trim();

        if (reviewText.isEmpty()) {
            Toast.makeText(this, "Please enter your review", Toast.LENGTH_SHORT).show();
            return;
        }

        String key = databaseReference.push().getKey();
        databaseReference.child(key).setValue(reviewText)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Review.this, "Review submitted successfully", Toast.LENGTH_SHORT).show();
                        editTextReview.setText("");
                        startActivity(new Intent(Review.this, Settings.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Review.this, "Failed to submit review", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
