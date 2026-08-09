package com.example.caronrent;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CompleteProfile extends AppCompatActivity {

    static final String TAG = "CompleteProfile";
    private static final int PIC_IMAGE_REQUEST = 1;
    final private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Admin").child("Users");
    //final  private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Images");
    final private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    SharedPreferences sharedPreferences;
    Spinner spinner;
    TextInputEditText txtname, txtmobile, txtemail, txtpass, txtconpass, txtaadharnum;
    //    RadioGroup radiogender;
//    RadioButton male, female, other;
    Button btnregister;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("Admin").child("Renters");
    String emailShare, name, mob, pass, email, imageUrl, selectedOption, eem, img,isAvail;
    RadioGroup radioGroup;
    RadioButton rdb_male, rdb_female, rdb_others;
    ScrollView onMainlayout;
    boolean val = false;
    boolean check;
    private ImageButton  img_aadhaar_front, img_aadhaar_back;
    private ProgressBar progressBar;
    //    private FirebaseAuth authProfile;
//    private StorageReference storageReference;
    private FirebaseUser firebaseUser;
    private FirebaseAuth Fauth;
    private Uri imageUri, imageUri2, imageUri3;
    private DatabaseReference usersRef;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profile);


        img_aadhaar_front = findViewById(R.id.adhar_front);
        img_aadhaar_back = findViewById(R.id.adhar_back);
        progressBar = findViewById(R.id.progressBar); // Add this line to initialize progressBar
        txtaadharnum = findViewById(R.id.txtDl);
        onMainlayout = findViewById(R.id.main);


//        uploadUserDl = findViewById(R.id.uploadUserDl);
        btnregister = findViewById(R.id.btnregister);

        sharedPreferences = getSharedPreferences("email_1", MODE_PRIVATE);


        eem = sharedPreferences.getString("email", "AAA");

        System.out.println("/////////////////*********************////////////////********** eem" + eem);


        usersRef = FirebaseDatabase.getInstance().getReference("Admin").child("Renters");

//        Intent inn = getIntent();
//        String rMO = inn.getStringExtra("rMo");
//        String mType = inn.getStringExtra("mType");
//        String mCom = inn.getStringExtra("mCom");
//        String pPoint = inn.getStringExtra("pPoint");
//        String dri = inn.getStringExtra("dri");
//        String mprice = inn.getStringExtra("mprice");
//        String mpic = inn.getStringExtra("mpic");
//        String mname = inn.getStringExtra("moname");


        usersRef.orderByChild("email").equalTo(eem).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    imageUrl = userSnapshot.child("imageURLUser").getValue(String.class);
                    isAvail = userSnapshot.child("isUnAvailable").getValue(String.class);

                    if (imageUrl != null) {
                        img = imageUrl;
                    }

                    name = userSnapshot.child("name").getValue(String.class);
                    mob = userSnapshot.child("mobile").getValue(String.class);
                    email = userSnapshot.child("email").getValue(String.class);
                    pass = userSnapshot.child("pass").getValue(String.class);
                    selectedOption = userSnapshot.child("gender").getValue(String.class);

                    System.out.println("/////////////////*********************////////////////**********" + mob);
                    System.out.println("/////////////////*********************////////////////**********" + name);
                    System.out.println("/////////////////*********************////////////////**********" + email);
                    System.out.println("/////////////////*********************////////////////**********" + pass);
                    System.out.println("/////////////////*********************////////////////**********" + selectedOption);
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle the error, if any.
            }
        });


//        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                new ActivityResultCallback<ActivityResult>() {
//                    @Override
//                    public void onActivityResult(ActivityResult result) {
//                        if (result.getResultCode() == Activity.RESULT_OK) {
//                            Intent data = result.getData();
//                            imageUri = data.getData();
//                            uploadUserDl.setImageURI(imageUri);
//
//                        } else {
//                            Toast.makeText(CompleteProfile.this, "No Image Selected", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }
//        );
//
//        uploadUserDl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent photoPicker = new Intent();
//                photoPicker.setAction(Intent.ACTION_GET_CONTENT);
//                photoPicker.setType("image/*");
//                activityResultLauncher.launch(photoPicker);
//            }
//        });

        ActivityResultLauncher<Intent> activityResultLauncher2 = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data1 = result.getData();
                            imageUri2 = data1.getData();
                            img_aadhaar_front.setImageURI(imageUri2);
                        } else {
                            Toast.makeText(CompleteProfile.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        img_aadhaar_front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPicker = new Intent();
                photoPicker.setAction(Intent.ACTION_GET_CONTENT);
                photoPicker.setType("image/*");
                activityResultLauncher2.launch(photoPicker);
            }
        });

        ActivityResultLauncher<Intent> activityResultLauncher3 = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data1 = result.getData();
                            imageUri3 = data1.getData();
                            img_aadhaar_back.setImageURI(imageUri3);
                        } else {
                            Toast.makeText(CompleteProfile.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        img_aadhaar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPicker = new Intent();
                photoPicker.setAction(Intent.ACTION_GET_CONTENT);
                photoPicker.setType("image/*");
                activityResultLauncher3.launch(photoPicker);
            }
        });

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (valid()) {
                    progressBar.setVisibility(View.VISIBLE);
                    getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                            android.view.WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    Toast.makeText(CompleteProfile.this, "Profile Complete Successfully", Toast.LENGTH_SHORT).show();
//                                sendEmailVerification();
                    txtaadharnum.setEnabled(false);
                    uploadUserDetails();

                    if (val) {
                        progressBar.setVisibility(View.GONE);

//                        Toast.makeText(CompleteProfile.this, mname, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(CompleteProfile.this,MainFragment.class));
                        finish();
                    }


                } else {
                    valid(); // It seems like you want to display a message here, not actually validate again
                }


            }
        });
    }

    private void uploadUserDetails() {
        // Upload profile picture to Firebase
        // Profile picture URL
        sharedPreferences = getSharedPreferences("email_1", MODE_PRIVATE);


        eem = sharedPreferences.getString("email", "AAA");

        System.out.println("/////////////////*********************////////////////********** eem" + eem);


        usersRef.orderByChild("email").equalTo(eem).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    imageUrl = userSnapshot.child("imageURLUser").getValue(String.class);

                    if (imageUrl != null) {
                        img = imageUrl;
                    }


                    mob = userSnapshot.child("mobile").getValue(String.class);

                    System.out.println("/////////////////*********************////////////////**********" + mob);
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle the error, if any.
            }
        });


        String profileImageUrl = img;

        // Upload driving license to Firebase
//        if (imageUri != null) {
//            final StorageReference imageReferenceDl = storageReference.child("Admin").child("Users/dl/" + System.currentTimeMillis() + "." + getFileExtension(imageUri));
//
//            imageReferenceDl.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
//            {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    imageReferenceDl.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                        public void onSuccess(Uri uriDl) {
//                            // Driving license URL
//                            String dlImageUrl = uriDl.toString();

        // Retrieve other user details
        if (imageUri2 != null) {
            final StorageReference imageReferenceDl = storageReference.child("Admin").child("Renters/aadhaar_front/" + System.currentTimeMillis() + "." + getFileExtension(imageUri2));

            imageReferenceDl.putFile(imageUri2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageReferenceDl.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uriDl) {
                            // Driving license URL
                            String aadhaar_front = uriDl.toString();

                            if (imageUri3 != null) {
                                final StorageReference imageReferenceDl = storageReference.child("Admin").child("Renters/aadhaar_back/" + System.currentTimeMillis() + "." + getFileExtension(imageUri3));

                                imageReferenceDl.putFile(imageUri3).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        imageReferenceDl.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uriDl) {
                                                // Driving license URL
                                                String aadhaar_back = uriDl.toString();

                                                // Retrieve other user details
                                                String Name = name;
                                                String Email = email;
                                                String Pass = pass;
                                                String Mobile = mob;
                                                String Gender = selectedOption;
                                                Boolean isVerified = false;
                                                String aadharNum = txtaadharnum.getText().toString();


                                                System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM" + Mobile);


                                                // Create HelperClass and upload to Firebase
                                                // Create HelperClass and upload to Firebase
                                                ReadWriteUserDetails helperClass = new ReadWriteUserDetails(Name, Email, Pass, Mobile, "Surat", aadharNum, aadhaar_front, aadhaar_back, Gender, profileImageUrl, isVerified.toString(),isAvail);
                                                reference.child(mob).setValue(helperClass); // Line 296


//                                                                    progressBar.setVisibility(View.INVISIBLE);
//                                                DatabaseReference db_admin = FirebaseDatabase.getInstance().getReference("Admin").child("Users").child(Mobile);
                                                DatabaseReference db_admin = FirebaseDatabase.getInstance().getReference("Admin").child("Renters").child(Mobile);
                                                db_admin.setValue(helperClass);
                                                progressBar.setVisibility(View.VISIBLE);
                                                Toast.makeText(CompleteProfile.this, "You have completed your profile", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(CompleteProfile.this, MainFragment.class);
                                                startActivity(intent);
                                                val = true;

                                            }
                                        });
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(CompleteProfile.this, "Failed to upload Aadhar back", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            // Retrieve other user details

                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(CompleteProfile.this, "Failed to upload Aadhar Front", Toast.LENGTH_SHORT).show();
                }
            });
        }
//                        }
//                    });
    }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    progressBar.setVisibility(View.INVISIBLE);
//                    Toast.makeText(CompleteProfile.this, "Failed to upload driving license", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }

    //}


    private String getFileExtension(Uri imageUri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(imageUri));
    }

    boolean valid() {


        String City = "Surat";
        String aadharNum = txtaadharnum.getText().toString();

        String txtGender;

//        String mobileregex = "[6-9][0-9][9]";
//        String mobileregex = "^[6-9]\\d{9}$";
        Matcher mobilematcher;
        Pattern mobilepattern;

        if (TextUtils.isEmpty(aadharNum)) {
            Toast.makeText(CompleteProfile.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
            txtemail.setError("Driving Licence  is required");
            txtemail.requestFocus();
            return false;
        } else {
            check = true;
        }

        return check;


    }
}