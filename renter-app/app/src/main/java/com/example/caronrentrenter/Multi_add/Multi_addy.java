package com.example.caronrentrenter.Multi_add;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.caronrentrenter.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.List;

public class Multi_addy extends AppCompatActivity {

    private static final int PICK_IMAGES_REQUEST = 1;

    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_addy);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

        // Trigger image selection
        pickImages();
    }

    private void pickImages() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent, PICK_IMAGES_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGES_REQUEST && resultCode == RESULT_OK) {
            if (data != null) {
                List<Uri> imageUris = Utils.getSelectedImages(data);
                uploadImages(imageUris);
            }
        }
    }

    private void uploadImages(List<Uri> imageUris) {
        for (Uri uri : imageUris) {
            String imageName = System.currentTimeMillis() + "." + getFileExtension(uri);
            StorageReference imageRef = storageReference.child("images/" + imageName);

            UploadTask uploadTask = imageRef.putFile(uri);

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    return imageRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        saveCarDetailsToDatabase(downloadUri.toString());
                    } else {
                        Toast.makeText(Multi_addy.this, "Image upload failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void saveCarDetailsToDatabase(String imageUrl) {
        CarDetails carDetails = new CarDetails("modelName", "modelDescription", "rentPerDay", "maximumSpeed",
                "fuel", "carRating", "numberPassengers", "gearMode", "carCompany", "carType", "renterMobile", imageUrl);

        String key = databaseReference.child("Details").push().getKey();
        databaseReference.child("Details").child(key).setValue(carDetails);

        Toast.makeText(this, "Car details uploaded successfully", Toast.LENGTH_SHORT).show();
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }
}