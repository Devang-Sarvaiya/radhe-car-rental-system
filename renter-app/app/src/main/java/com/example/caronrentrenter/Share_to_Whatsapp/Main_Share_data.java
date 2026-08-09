package com.example.caronrentrenter.Share_to_Whatsapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.caronrentrenter.R;

public class Main_Share_data extends AppCompatActivity {

    private static final String message = "Rent, search and enjoy with Radhe Car Renters";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_share_data);

        final AppCompatButton sendText = findViewById(R.id.sendTxtBtn);
        final AppCompatButton sendImageText = findViewById(R.id.sendImageTextBtn);

        sendText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareTextViaWhatsApp();
            }
        });

        sendImageText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFromGallery();
            }
        });
    }

    private void shareTextViaWhatsApp() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        intent.setType("text/plain");
//        intent.setPackage("com.whatsapp");

        // Ensure that WhatsApp is installed before launching the intent
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            // WhatsApp is not installed
            // You can handle this case, e.g., by showing a message to the user
        }
    }

    private void chooseFromGallery() {
        activityResultLauncher.launch("image/*");
    }

    private final ActivityResultLauncher<String> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    shareImageViaWhatsApp(result);
                }
            });

    private void shareImageViaWhatsApp(Uri imageUri) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        intent.putExtra(Intent.EXTRA_STREAM, imageUri);
        intent.setType("image/jpeg");
//        intent.setPackage("com.whatsapp");

        // Ensure that WhatsApp is installed before launching the intent
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            // WhatsApp is not installed
            // You can handle this case, e.g., by showing a message to the user
        }
    }
}
