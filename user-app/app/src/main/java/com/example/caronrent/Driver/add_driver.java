package com.example.caronrent.Driver;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.caronrent.R;
import com.example.caronrent.ReadWriteUserDetails;
import com.example.caronrent.Driver.add_driver;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class add_driver extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "emailShare";
    private DatabaseReference usersRef;
    String emailShare;
    String mob,eem;
    static final String TAG = "add_driver";
    Spinner spinner;
    EditText txtname, txtmobile, txtemail, txtpass, txtconpass, txtdl;
    //    RadioGroup radiogender;
//    RadioButton male, female, other;
    AppCompatButton btnregister;
    String[] city = {"Surat", "Vadodara", "Ahmedabad", "Rajkot", "Bhavnagar"};
    private ImageButton img_adhar_front, img_adhar_back, Profilepic, insurance,img_dl;
    private ProgressBar progressBar;
    private FirebaseUser firebaseUser;
    private static final int PIC_IMAGE_REQUEST = 1;
    private FirebaseAuth Fauth;
    private Uri imageUri, imageUri1, imageUri2, imageUri3,imageUri4;
    final private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("Admin").child("Renters");
    RadioGroup radioGroup;
    String selectedOption;
    RadioButton rdb_male, rdb_female, rdb_others;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_driver);

        txtname = findViewById(R.id.dr_name);
        txtmobile = findViewById(R.id.dr_mno);
        txtemail = findViewById(R.id.dr_email);
        txtdl = findViewById(R.id.dr_dl);

        btnregister = findViewById(R.id.btnregister);

        img_adhar_front = findViewById(R.id.img_adhar_front);
        img_adhar_back = findViewById(R.id.img_adhar_back);
        Profilepic = findViewById(R.id.Profilepic);
        insurance = findViewById(R.id.insurance);
        img_dl = findViewById(R.id.img_dl);

        sharedPreferences =getSharedPreferences("email_1", MODE_PRIVATE); ;

        eem = sharedPreferences.getString("email","AAA");
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
        progressBar = findViewById(R.id.progressBar);

        rdb_male = findViewById(R.id.rdb_male);
        rdb_female = findViewById(R.id.rdb_female);
        rdb_others = findViewById(R.id.rdb_others);

        radioGroup = findViewById(R.id.radioGroup);
        Fauth = FirebaseAuth.getInstance();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Check which radio button is selected
                RadioButton radioButton = findViewById(checkedId);

                if (radioButton != null) {
                    selectedOption = radioButton.getText().toString();
                    Toast.makeText(add_driver.this, "Selected option: " + selectedOption, Toast.LENGTH_SHORT).show();
                }
            }
        });


        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data1 = result.getData();
                            imageUri1 = data1.getData();
                            img_adhar_front.setImageURI(imageUri1);
                        } else {
                            Toast.makeText(add_driver.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        img_adhar_front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent();
                photoPicker.setAction(Intent.ACTION_GET_CONTENT);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

        ActivityResultLauncher<Intent> activityResultLauncher1 = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data1 = result.getData();
                            imageUri2 = data1.getData();
                            img_adhar_back.setImageURI(imageUri2);
                        } else {
                            Toast.makeText(add_driver.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        img_adhar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent();
                photoPicker.setAction(Intent.ACTION_GET_CONTENT);
                photoPicker.setType("image/*");
                activityResultLauncher1.launch(photoPicker);
            }
        });

        ActivityResultLauncher<Intent> activityResultLauncher2 = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data1 = result.getData();
                            imageUri = data1.getData();
                            Profilepic.setImageURI(imageUri);
                        } else {
                            Toast.makeText(add_driver.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        Profilepic.setOnClickListener(new View.OnClickListener() {
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
                            insurance.setImageURI(imageUri3);
                        } else {
                            Toast.makeText(add_driver.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        insurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPicker = new Intent();
                photoPicker.setAction(Intent.ACTION_GET_CONTENT);
                photoPicker.setType("image/*");
                activityResultLauncher3.launch(photoPicker);
            }
        });


        ActivityResultLauncher<Intent> activityResultLauncher4 = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data1 = result.getData();
                            imageUri4 = data1.getData();
                            img_dl.setImageURI(imageUri4);
                        } else {
                            Toast.makeText(add_driver.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        img_dl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPicker = new Intent();
                photoPicker.setAction(Intent.ACTION_GET_CONTENT);
                photoPicker.setType("image/*");
                activityResultLauncher4.launch(photoPicker);
            }
        });


        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if (valid()) {
                    uploadUserDetails();
                } else {
                    progressBar.setVisibility(View.GONE);
                    valid();
                }
            }
        });
    }


    private void uploadUserDetails() {
        // Upload profile picture to Firebase
        if (imageUri != null) {
            final StorageReference imageReferenceProfile = storageReference.child("driver/profile/" + System.currentTimeMillis() + "." + getFileExtension(imageUri));

            imageReferenceProfile.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageReferenceProfile.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            // Profile picture URL
                            String profileImageUrl = uri.toString();

                            // Upload Adhar Front image to Firebase
                            if (imageUri1 != null) {
                                final StorageReference imageReferenceAdharFront = storageReference.child("driver/adhar_front/" + System.currentTimeMillis() + "." + getFileExtension(imageUri1));

                                imageReferenceAdharFront.putFile(imageUri1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        imageReferenceAdharFront.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uriAdharFront) {
                                                // Adhar Front image URL
                                                String adharFrontImageUrl = uriAdharFront.toString();

                                                // Upload Adhar Back image to Firebase
                                                if (imageUri2 != null) {
                                                    final StorageReference imageReferenceAdharBack = storageReference.child("driver/adhar_back/" + System.currentTimeMillis() + "." + getFileExtension(imageUri2));

                                                    imageReferenceAdharBack.putFile(imageUri2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                        @Override
                                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                            imageReferenceAdharBack.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                @Override
                                                                public void onSuccess(Uri uriAdharBack) {
                                                                    // Adhar Back image URL
                                                                    String adharBackImageUrl = uriAdharBack.toString();

                                                                    if (imageUri3 != null) {
                                                                        final StorageReference imageReferenceAdharBack = storageReference.child("driver/insurance/" + System.currentTimeMillis() + "." + getFileExtension(imageUri2));

                                                                        imageReferenceAdharBack.putFile(imageUri3).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                                            @Override
                                                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                                                imageReferenceAdharBack.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                    @Override
                                                                                    public void onSuccess(Uri uriAdharBack) {
                                                                                        // Adhar Back image URL
                                                                                        String insurance = uriAdharBack.toString();
                                                                                        if (imageUri4 != null) {
                                                                                            final StorageReference imageReferenceAdharBack = storageReference.child("driver/drivingLicence/" + System.currentTimeMillis() + "." + getFileExtension(imageUri2));

                                                                                            imageReferenceAdharBack.putFile(imageUri4).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                                                                @Override
                                                                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                                                                    imageReferenceAdharBack.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                                                        @Override
                                                                                                        public void onSuccess(Uri uriAdharBack) {
                                                                                                            // Adhar Back image URL
                                                                                                            String dl = uriAdharBack.toString();

                                                                                                            // Retrieve other user details
                                                                                                            String Name = txtname.getText().toString();
                                                                                                            String Email = txtemail.getText().toString();
                                                                                                            String Mobile = txtmobile.getText().toString();
                                                                                                            String Dll = txtdl.getText().toString();
                                                                                                            String Gender = selectedOption;
                                                                                                            Boolean isVerified = false;

                                                                                                            // Create HelperClass and upload to Firebase
//                                                                                                            ImageModel_1_driver helperClass = new ImageModel_1_driver(Name, Email, Mobile,mob, Dll, Gender, profileImageUrl, isVerified.toString(), adharFrontImageUrl, adharBackImageUrl,insurance,dl);
////                                                                                                            reference.child(mob).child("Drivers").child(Mobile).setValue(helperClass);
//                                                                                                            DatabaseReference reference_admin = FirebaseDatabase.getInstance().getReference("Admin").child("Renters").child(mob).child("Drivers").child(Mobile);
//                                                                                                            reference_admin.setValue(helperClass);
//                                                                                                            DatabaseReference reference_admin_general = FirebaseDatabase.getInstance().getReference("Admin").child("Drivers").child("General").child(Mobile);
//                                                                                                            reference_admin_general.setValue(helperClass);
//                                                                                                            progressBar.setVisibility(View.INVISIBLE);
//                                                                                                            Toast.makeText(add_driver.this, "Driver added successfully!", Toast.LENGTH_SHORT).show();
//                                                                                                            finish();

                                                                                                        }
                                                                                                    });
                                                                                                }
                                                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                                                @Override
                                                                                                public void onFailure(@NonNull Exception e) {
                                                                                                    progressBar.setVisibility(View.GONE);
                                                                                                    Toast.makeText(add_driver.this, "Failed to upload Driving Licence", Toast.LENGTH_SHORT).show();
                                                                                                }
                                                                                            });
                                                                                        }


                                                                                    }
                                                                                });
                                                                            }
                                                                        }).addOnFailureListener(new OnFailureListener() {
                                                                            @Override
                                                                            public void onFailure(@NonNull Exception e) {
                                                                                progressBar.setVisibility(View.GONE);
                                                                                Toast.makeText(add_driver.this, "Failed to upload Insurance", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        });
                                                                    }
                                                                }
                                                            });
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            progressBar.setVisibility(View.GONE);
                                                            Toast.makeText(add_driver.this, "Failed to aadhaar card", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                }
                                            }
                                        });
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(add_driver.this, "Failed to upload aadhaar card", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(add_driver.this, "Failed to upload profile picture", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private String getFileExtension(Uri imageUri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(imageUri));
    }


    boolean check;

    boolean valid() {
        int selectedGenderid = radioGroup.getCheckedRadioButtonId();
        rdb_male = findViewById(selectedGenderid);

        String txtName = txtname.getText().toString();
        String txtMobile = txtmobile.getText().toString();
        String txtEmail = txtemail.getText().toString();

        String txtDll = txtdl.getText().toString();


        String txtGender;

        String mobileregex = "[6-9][0-9]{9}";
        Matcher mobilematcher;
        Pattern mobilepattern;
        mobilepattern = Pattern.compile(mobileregex);
        mobilematcher = mobilepattern.matcher(txtMobile);

        if (TextUtils.isEmpty(txtName)) {
            Toast.makeText(add_driver.this, "Please Enter Name", Toast.LENGTH_SHORT).show();
            txtname.setError("Name is required");
            txtname.requestFocus();
            check = false;
        } else if (TextUtils.isEmpty(txtMobile)) {
            Toast.makeText(add_driver.this, "Please Enter Mobile Number", Toast.LENGTH_SHORT).show();
            txtmobile.setError("Mobile Number is required");
            txtmobile.requestFocus();
            check = false;
        } else if (txtMobile.length() != 10) {
            Toast.makeText(add_driver.this, "Please Re-Enter Mobile Number", Toast.LENGTH_SHORT).show();
            txtmobile.setError("Mobile no. should be 10 digits");
            txtmobile.requestFocus();
            check = false;
        } else if (!mobilematcher.find()) {
            Toast.makeText(add_driver.this, "Please Re-Enter Mobile Number", Toast.LENGTH_SHORT).show();
            txtmobile.setError("Mobile no. is not valid");
            txtmobile.requestFocus();
            check = false;
        } else if (TextUtils.isEmpty(txtEmail)) {
            Toast.makeText(add_driver.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
            txtemail.setError("Email is required");
            txtemail.requestFocus();
            check = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(txtEmail).matches()) {
            Toast.makeText(add_driver.this, "Please Re-Enter Email", Toast.LENGTH_SHORT).show();
            txtemail.setError("Valid Email is required");
            txtemail.requestFocus();
        } else if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(add_driver.this, "Please select your gender", Toast.LENGTH_SHORT).show();
            rdb_male.setError("Gender is required");
            rdb_male.requestFocus();
            check = false;
        } else if (TextUtils.isEmpty(txtDll)) {
            Toast.makeText(add_driver.this, "Please Enter Aadhaar card no.", Toast.LENGTH_SHORT).show();
            txtconpass.setError("Aadhaar card  no. is required");
            txtconpass.requestFocus();
            check = false;
        } else {
            txtGender = rdb_male.getText().toString();
//                    registeruser(txtName, txtMobile, txtEmail, txtPass, txtDll,City,txtGender);
            check = true;
        }
        if (!check) {
            progressBar.setVisibility(View.GONE);
        }

        return check;

    }
}
