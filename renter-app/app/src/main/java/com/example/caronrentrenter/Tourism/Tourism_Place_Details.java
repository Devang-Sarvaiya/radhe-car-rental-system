package com.example.caronrentrenter.Tourism;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.caronrentrenter.R;

public class Tourism_Place_Details extends AppCompatActivity {

    TextView txtPlaceName,txtLink,txtDescription;
    ImageButton btnUpload;
    TourClass object;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tourism_place_details);

        txtPlaceName = findViewById(R.id.txtPlaceName);
        txtLink = findViewById(R.id.txtLink);
        txtDescription = findViewById(R.id.txtDescription);
        btnUpload = findViewById(R.id.btnUpload);

        object = (TourClass) getIntent().getSerializableExtra("object");

        txtPlaceName.setText(object.getPlacelName());
        txtLink.setText(object.getPlaceLink());

        txtLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the search query from the text of txtLink
                String searchQuery = txtLink.getText().toString();

                // Create an Intent to perform a web search
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, searchQuery);

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    // If no app can handle the web search intent, show a message
                    Toast.makeText(Tourism_Place_Details.this, "No app can handle this action", Toast.LENGTH_SHORT).show();
                }
            }
        });


        txtDescription.setText(object.getPlaceDescription());

        String imageUrl = object.getPlaceImageUrl();
        Glide.with(Tourism_Place_Details.this).load(imageUrl).into(btnUpload);





    }
}