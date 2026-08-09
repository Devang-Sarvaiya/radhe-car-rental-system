package com.example.caronrentrenter.Safety_feature;

import android.os.Bundle;
import android.text.util.Linkify;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.caronrentrenter.R;
import com.example.caronrentrenter.Tourism.TourClass;

public class Safety_Feature_Details extends AppCompatActivity {
    TextView txtPlaceName,txtLink,txtDescription;
    ImageButton btnUpload;
    TourClass object;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_saftey_feature_details);
        txtPlaceName = findViewById(R.id.txtPlaceName);
        txtLink = findViewById(R.id.txtLink);
        txtDescription = findViewById(R.id.txtDescription);
        btnUpload = findViewById(R.id.btnUpload);

        object = (TourClass) getIntent().getSerializableExtra("object");

        txtPlaceName.setText(object.getPlacelName());
        txtLink.setText(object.getPlaceLink());
        txtDescription.setText(object.getPlaceDescription());
        // Make the link clickable
        Linkify.addLinks(txtLink, Linkify.WEB_URLS);
        String imageUrl = object.getPlaceImageUrl();
        Glide.with(Safety_Feature_Details.this).load(imageUrl).into(btnUpload);
//        txtLink.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String location = txtLink.getText().toString();
//                // Create intent with the location URI
//                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(location));
//                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//                mapIntent.setPackage("com.google.android.apps.maps"); // Specify Google Maps package to ensure it opens in Maps app
//                if (mapIntent.resolveActivity(getPackageManager()) != null) {
//                    startActivity(mapIntent);
//                }
//            }
//        });




    }
}