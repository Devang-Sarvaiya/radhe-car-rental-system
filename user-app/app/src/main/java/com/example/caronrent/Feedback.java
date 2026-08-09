package com.example.caronrent;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Feedback extends AppCompatActivity {

    EditText editTextUser, editTextFeedback;
    Button buttonSubmit;

    DatabaseReference feedbackRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        editTextUser = findViewById(R.id.user);
        editTextFeedback = findViewById(R.id.feedback);
        buttonSubmit = findViewById(R.id.button3);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        feedbackRef = database.getReference("feedback");

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = editTextUser.getText().toString().trim();
                String feedbackText = editTextFeedback.getText().toString().trim();

                if (!user.isEmpty() && !feedbackText.isEmpty()) {
                    saveFeedbackToFirebase(user, feedbackText);
                } else {
                    Toast.makeText(Feedback.this, "Please enter user name and feedback", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveFeedbackToFirebase(String user, String feedbackText) {
        String key = feedbackRef.push().getKey();
        feedbackRef.child(key).child("user").setValue(user);
        feedbackRef.child(key).child("feedback").setValue(feedbackText)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Feedback saved successfully
                        Toast.makeText(Feedback.this, "Feedback submitted!", Toast.LENGTH_SHORT).show();
                        // Clear input fields after submission
                        editTextUser.setText("");
                        editTextFeedback.setText("");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Error occurred while saving feedback
                        Toast.makeText(Feedback.this, "Failed to submit feedback.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}