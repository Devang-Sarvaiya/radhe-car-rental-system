package com.example.car_admin.Tourism;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.car_admin.MainFragment;
import com.example.car_admin.More;
import com.example.car_admin.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Tourism_Data_add extends AppCompatActivity {

    final private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    EditText modelName, Link, modelDescription;
    ImageButton btnUpload;
    ImageView back_btn;
    SharedPreferences sharedPreferences;
    ProgressBar progressBar;
    String mob, eem;
    boolean check;
    private AppCompatButton btnAddItem;
    private DatabaseReference usersRef;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tourism_data_add);

        modelName = findViewById(R.id.modelName);
        modelDescription = findViewById(R.id.modelDescription);
        Link = findViewById(R.id.Link);
        btnUpload = findViewById(R.id.btnUpload);
        btnAddItem = findViewById(R.id.btnAddItem);
        progressBar = findViewById(R.id.progressBar);
        back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Tourism_Data_add.this, MainFragment.class));
            }
        });

        sharedPreferences = getSharedPreferences("email_1", MODE_PRIVATE);


        eem = sharedPreferences.getString("email", "AAA");
        usersRef = FirebaseDatabase.getInstance().getReference("Admin").child("Renters");
        usersRef.orderByChild("email").equalTo(eem).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String imageUrl = userSnapshot.child("imageURLUser").getValue(String.class);
                    //                    Glide.with(Car_item_add.this).load(imageUrl).into(showProfilePic);
                    mob = userSnapshot.child("mobile").getValue(String.class);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle the error, if any.
            }
        });


        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data1 = result.getData();
                            imageUri = data1.getData();
                            btnUpload.setImageURI(imageUri);
                        } else {
                            Toast.makeText(Tourism_Data_add.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent();
                photoPicker.setAction(Intent.ACTION_GET_CONTENT);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });



        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (valid()) {
                    uploadUserDetails();
                } else {
                    valid(); // It seems like you want to display a message here, not actually validate again
                }
            }
        });
    }


//    private void uploadUserDetails() {
//        // Upload profile picture to Firebase
//        if (imageUri != null) {
//            final StorageReference imageReferenceProfile = storageReference.child("Tourism_places/Picture/" + System.currentTimeMillis() + "." + getFileExtension(imageUri));
//
//            imageReferenceProfile.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    imageReferenceProfile.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                        public void onSuccess(Uri uri) {
//                            // Profile picture URL
//                            String placeImageUrl = uri.toString();
//                            // Retrieve other user details
//                            String placeName = modelName.getText().toString();
//                            String placeLink = Link.getText().toString();
//                            String placeDescription = modelDescription.getText().toString();
//
//                            // Create HelperClass and upload to Firebase
//                            TourClass helperClass = new TourClass(placeName, placeDescription, placeLink, placeImageUrl);
////                                                                                                            reference.child(mob).child("Drivers").child(Mobile).setValue(helperClass);
//                            DatabaseReference reference_admin = FirebaseDatabase.getInstance().getReference("Admin").child("Tourism_Place").child(placeName).child("Details");
//                            reference_admin.setValue(helperClass);
////                            DatabaseReference reference_admin_general = FirebaseDatabase.getInstance().getReference("Admin").child("Drivers").child("General").child(Mobile);
////                            reference_admin_general.setValue(helperClass);
//                            Toast.makeText(Tourism_Data_add.this, "Place added successfully!", Toast.LENGTH_SHORT).show();
//                            finish();
//
//
//                        }
//                    });
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(Tourism_Data_add.this, "Failed to upload place picture", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//    }

    private void uploadUserDetails() {
        progressBar.setVisibility(View.VISIBLE);

        if (imageUri != null) {
            final StorageReference imageReferenceProfile = storageReference.child("Tourism_places/Picture/" + System.currentTimeMillis() + "." + getFileExtension(imageUri));

            imageReferenceProfile.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageReferenceProfile.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String placeImageUrl = uri.toString();
                            String placeName = modelName.getText().toString();
                            String placeLink = Link.getText().toString();
                            String placeDescription = modelDescription.getText().toString();

                            TourClass helperClass = new TourClass(placeName, placeDescription, placeLink, placeImageUrl);
                            DatabaseReference reference_admin = FirebaseDatabase.getInstance().getReference("Admin").child("Tourism_Place").child(placeName).child("Details");
                            reference_admin.setValue(helperClass).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(Tourism_Data_add.this, "Place added successfully!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(Tourism_Data_add.this, "Failed to upload place details", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(Tourism_Data_add.this, "Failed to upload place picture", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private String getFileExtension(Uri imageUri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(imageUri));
    }

    boolean valid() {
        String placeName = modelName.getText().toString();
        String placeLink = Link.getText().toString();
        String placeDescription = modelDescription.getText().toString();

        if (TextUtils.isEmpty(placeName)) {
            Toast.makeText(Tourism_Data_add.this, "Please Enter Name", Toast.LENGTH_SHORT).show();
            modelName.setError("Name is required");
            modelName.requestFocus();
            check = false;
        } else if (TextUtils.isEmpty(placeDescription)) {
            Toast.makeText(Tourism_Data_add.this, "Please Enter Description", Toast.LENGTH_SHORT).show();
            modelDescription.setError("Description is required");
            modelDescription.requestFocus();
            check = false;
        } else if (TextUtils.isEmpty(placeLink)) {
            Toast.makeText(Tourism_Data_add.this, "Please Enter Link", Toast.LENGTH_SHORT).show();
            Link.setError("Link is required");
            Link.requestFocus();
            check = false;
        } else {
            check = true;
        }


        return check;

    }
}
