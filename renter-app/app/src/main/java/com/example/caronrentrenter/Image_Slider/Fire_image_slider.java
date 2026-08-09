package com.example.caronrentrenter.Image_Slider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.caronrentrenter.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Fire_image_slider extends AppCompatActivity {

    TextView txt_ramu;
    String model, model1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire_image_slider);

        txt_ramu = findViewById(R.id.txt_ramu);

        // Initialize Firebase Database
        DatabaseReference productsReference = FirebaseDatabase.getInstance().getReference("Car").child("General").child("Wedding");

        // Initialize imageList
        ArrayList<SlideModel> imageList = new ArrayList<>();

        // Fetch image URLs for each product from Firebase Realtime Database
        productsReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot productSnapshot1 : productSnapshot.getChildren()) {
                        for (DataSnapshot productSnapshot2 : productSnapshot1.getChildren()) {
                            DatabaseReference productPicReference = productSnapshot2.child("Pic").getRef();
                            // Fetch image URLs for the specific product
                            productPicReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot picSnapshot) {
                                    for (DataSnapshot itemSnapshot : picSnapshot.getChildren()) {
                                        String imageUrl = itemSnapshot.getValue(String.class);
                                        if (imageUrl != null) {
                                            // Use ScaleTypes.CENTER_CROP for desired scaling
                                                imageList.add(new SlideModel(imageUrl, ScaleTypes.CENTER_CROP));
                                        }
                                    }

                                    // Initialize ImageSlider and set the imageList
                                    ImageSlider imageSlider = findViewById(R.id.image_slider);
                                    imageSlider.setImageList(imageList);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    // Handle error
                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });


    }
}
