package com.example.caronrent.Add_car;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caronrent.R;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class add_car extends AppCompatActivity {

    public static final int PICK_IMAGES_REQUEST = 1;
    private static final int PICK_RC_BOOK_REQUEST = 2;
    private static final int PICK_CHASSIS_NUMBER_REQUEST = 3;
    private static final int PICK_INSURANCE_REQUEST = 4;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "emailShare";
    SharedPreferences sharedPreferences;
    String emailShare;
    String mob, eem, isVer, aadharNum;
    EditText modelName, modelDescription, rentPerDay, maximumSpeed, Fuel, carRating, numberPassengers,txtdoor;
    String selectedOption;
    ProgressBar progressBar;
    RadioGroup radioGroup;
    RadioButton rdb_mannual, rdb_auto;
    Spinner spinner, spinner1, spinner2, spinner3;
    ImageView back_btn;
    List<String> items;
    String item;
    List<String> items1;
    String item1;
    List<String> items2;
    String item2;
    List<String> items3;
    String item3;
    private DatabaseReference usersRef;
    private ImageButton btnUpload;
    private ImageButton btnUploadRcBook;
    private ImageButton btnUploadChassisNumber;
    private ImageButton btnUploadInsurance;
    private AppCompatButton btnAddItem;
    private EditText edtPrice, edtQuantity;
    private RecyclerView recyclerView;
    private List<ImageModel> imageList;
    private ImageAdapter imageAdapter;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReference_doc;
    private DatabaseReference databaseReference1;
    private DatabaseReference databaseReferenceGeneralCar;
    private DatabaseReference databaseReferenceGeneralCar2;
    private DatabaseReference databaseReferenceGeneralRenters;
    private DatabaseReference databaseReferenceGeneralAdmin;
    private int productCounter;
    private Uri documentImageUriRcBook, documentImageUriChassisNumber, documentImageUriInsurance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);
        back_btn = findViewById(R.id.imageView2);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(add_car.this,car_mode.class));
            }
        });

        progressBar = findViewById(R.id.progressBar);

        spinner = findViewById(R.id.spinner);
        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        spinner3 = findViewById(R.id.spinner3);
        items = new ArrayList<>();
        items.add(0, "Car Category");
        items.add("Sports");
        items.add("Wedding");
        items.add("Tour");
        items.add("Off roading");
        items.add("Transport");
        items.add("Luxuries");
        items.add("General");
        spinner.setAdapter(new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, items));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!(spinner.getSelectedItem().toString().equals("Car Category"))) {
                    item = spinner.getSelectedItem().toString();
                } else {
                    // Handle the case when "Car Category" is selected
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        items1 = new ArrayList<>();
        items1.add(0, "Car Company Category");
        items1.add("Tata");
        items1.add("Mahindra");
        items1.add("Maruti");
        items1.add("Lamborghini");
        items1.add("Audi");
        items1.add("Bmw");
        items1.add("Skoda");
        items1.add("Others");

        spinner1.setAdapter(new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, items1));

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!(spinner1.getSelectedItem().toString().equals("Car Company Category"))) {
                    item1 = spinner1.getSelectedItem().toString();

                } else {
                    // Handle the case when "Car Company Category" is selected
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        items2 = new ArrayList<>();
        items2.add(0, "PickUp Points");
        items2.add("A-011,Ambani Complex,Varachha,Surat");
        items2.add("B-022,Chitra Enterprise,Nana varachha,Surat");
        items2.add("C-033,Kalyan Complex,Katargam,Surat");
        items2.add("D-044,Rajhans Complex,Kamrej,Surat");
        items2.add("E-055,Silver Trade Center,Mota varachha,Surat");

        spinner2.setAdapter(new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, items2));

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!(spinner2.getSelectedItem().toString().equals("PickUp Points"))) {
                    item2 = spinner2.getSelectedItem().toString();
                } else {
                    // Handle the case when "Car Company Category" is selected
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        items3 = new ArrayList<>();
        items3.add(0, "Fuel Type");
        items3.add("Petrol");
        items3.add("Diesel");
        items3.add("Gas");
        items3.add("Electric");

        spinner3.setAdapter(new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, items3));

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!(spinner3.getSelectedItem().toString().equals("Fuel Type"))) {
                    item3 = spinner3.getSelectedItem().toString();
                } else {
                    // Handle the case when "Car Company Category" is selected
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        modelName = findViewById(R.id.modelName);
        modelDescription = findViewById(R.id.modelDescription);
        rentPerDay = findViewById(R.id.rentPerDay);
        maximumSpeed = findViewById(R.id.maximumSpeed);
        Fuel = findViewById(R.id.Fuel);
        txtdoor = findViewById(R.id.txtdoor);
//        carRating = findViewById(R.id.carRating);
        radioGroup = findViewById(R.id.radioGroup1);
        rdb_mannual = findViewById(R.id.rdb_mannual);
        rdb_auto = findViewById(R.id.rdb_auto);
        numberPassengers = findViewById(R.id.numberPassengers);
        progressBar = findViewById(R.id.progressBar);

        btnUpload = findViewById(R.id.btnUpload);
        btnUploadRcBook = findViewById(R.id.btnUploadRcBook);
        btnUploadChassisNumber = findViewById(R.id.btnUploadChassisNumber);
        btnUploadInsurance = findViewById(R.id.btnUploadInsurance);

        btnAddItem = findViewById(R.id.btnAddItem);
        recyclerView = findViewById(R.id.recyclerView_1);

        imageList = new ArrayList<>();
        imageAdapter = new ImageAdapter(imageList);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Check which radio button is selected
                RadioButton radioButton = findViewById(checkedId);

                if (radioButton != null) {
                    selectedOption = radioButton.getText().toString();
                }
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
                    isVer = userSnapshot.child("isVerified").getValue(String.class);
//                    aada = userSnapshot.child("isVerified").getValue(String.class);

                }

                if (isVer.equals("true")) {

                } else {
                    showAlertDialog();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle the error, if any.
            }
        });

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(imageAdapter);

        btnUpload.setOnClickListener(v -> pickImages());

        btnUploadRcBook.setOnClickListener(v -> pickDocumentImage("RC Book", PICK_RC_BOOK_REQUEST));
        btnUploadChassisNumber.setOnClickListener(v -> pickDocumentImage("Chassis Number", PICK_CHASSIS_NUMBER_REQUEST));
        btnUploadInsurance.setOnClickListener(v -> pickDocumentImage("Insurance", PICK_INSURANCE_REQUEST));

        storageReference = FirebaseStorage.getInstance().getReference();

        retrieveLastProductID();

        btnAddItem.setOnClickListener(view -> {
            progressBar.setVisibility(View.VISIBLE);

            if (imageList.isEmpty()) {
                Toast.makeText(add_car.this, "Please select images", Toast.LENGTH_SHORT).show();
                return;
            }

            if (validateInputs()) {

                String Model = modelName.getText().toString();
                String Description = modelDescription.getText().toString();
                String Rent = rentPerDay.getText().toString();
                String MaxSpeed = maximumSpeed.getText().toString();
                String FuelStatus = Fuel.getText().toString();

                String Passengers = numberPassengers.getText().toString();
                String gearMode = selectedOption;
                String CarCompany = spinner1.getSelectedItem().toString();
                String CarType = spinner.getSelectedItem().toString();
                String PickUpPoints = spinner2.getSelectedItem().toString().trim();
                String FuelType = spinner3.getSelectedItem().toString().trim();
                String door = txtdoor.getText().toString();

                DatabaseReference db3 = FirebaseDatabase.getInstance().getReference("Admin").child("Car").child("General").child(CarType).child("Company").child(CarCompany).child(Model);
                DatabaseReference db = FirebaseDatabase.getInstance().getReference("Admin").child("Car").child(CarType).child("Company").child(CarCompany).child(Model);
//                DatabaseReference db1 = FirebaseDatabase.getInstance().getReference("Renters").child(mob).child("Car").child(CarType).child("Company").child(CarCompany).child(Model);
                DatabaseReference db4 = FirebaseDatabase.getInstance().getReference("Admin").child("Renters").child(mob).child("Car").child(CarType).child("Company").child(CarCompany).child(Model);


                if (documentImageUriRcBook != null) {
                    uploadDocumentImage("RC Book", documentImageUriRcBook, db);
//                    uploadDocumentImage("RC Book", documentImageUriRcBook, db1);
                    uploadDocumentImage("RC Book", documentImageUriRcBook, db3);
                    uploadDocumentImage("RC Book", documentImageUriRcBook, db4);
                }

                if (documentImageUriChassisNumber != null) {
                    uploadDocumentImage("Chassis Number", documentImageUriChassisNumber, db);
//                    uploadDocumentImage("Chassis Number", documentImageUriChassisNumber, db1);
                    uploadDocumentImage("Chassis Number", documentImageUriChassisNumber, db3);
                    uploadDocumentImage("Chassis Number", documentImageUriChassisNumber, db4);
                }

                if (documentImageUriInsurance != null) {
                    uploadDocumentImage("Insurance", documentImageUriInsurance, db);
//                    uploadDocumentImage("Insurance", documentImageUriInsurance, db1);
                    uploadDocumentImage("Insurance", documentImageUriInsurance, db3);
                    uploadDocumentImage("Insurance", documentImageUriInsurance, db4);
                }


//                databaseReferenceGeneralCar2 = FirebaseDatabase.getInstance().getReference("date").child(Model);

                databaseReferenceGeneralCar = FirebaseDatabase.getInstance().getReference("Admin").child("Car").child("General").child(CarType).child("Company").child(CarCompany);
//                databaseReferenceGeneralRenters = FirebaseDatabase.getInstance().getReference("Renters").child(mob).child("Car").child("General").child("Company").child(CarCompany);
                databaseReferenceGeneralAdmin = FirebaseDatabase.getInstance().getReference("Admin").child("Renters").child(mob).child("Car").child("General").child("Company").child(CarCompany);

                // Modify references to point to "Car" node
                databaseReference1 = FirebaseDatabase.getInstance().getReference("Admin").child("Car").child(CarType).child("Company").child(CarCompany).child(Model);
                String newProductId = "Product" + productCounter;
                DatabaseReference productRefCar = databaseReference1;

                // Modify references to point to "Renters" node
//                DatabaseReference productRefRenters = FirebaseDatabase.getInstance().getReference("Renters").child(mob).child("Car").child(CarType).child("Company").child(CarCompany).child(Model);
                DatabaseReference productRefGeneral = FirebaseDatabase.getInstance().getReference("Admin").child("Car").child("General").child(CarType).child("Company").child(CarCompany).child(Model);
//                DatabaseReference productRefGeneral_d = FirebaseDatabase.getInstance().getReference("date").child(Model);
                DatabaseReference productRefGeneral_a = FirebaseDatabase.getInstance().getReference("Admin").child("Renters").child(mob).child("Car").child(CarType).child("Company").child(CarCompany).child(Model);

                // Get the list of image URLs from your imageList
                List<String> imageUrls = new ArrayList<>();
                for (ImageModel imageModel : imageList) {
                    imageUrls.add(imageModel.getImageUrl());
                }

                String imageURL = "";
                ImageModel1 dataClass = new ImageModel1(Model, Description, imageURL, "", "", "", Rent, MaxSpeed, FuelStatus, Passengers, selectedOption, CarCompany, CarType, PickUpPoints, mob, productCounter, "false", "false", "false", "", "",FuelType,door,"false","false","false", "",imageUrls);


//                    ImageModel1 dataClass = new ImageModel1(Model, Description, Rent, MaxSpeed, FuelStatus, Rate, Passengers, selectedOption, CarCompany, CarType, mob,productCounter);

                // Modify references to point to "Car" node
                databaseReference = FirebaseDatabase.getInstance().getReference("Admin").child("Car").child(CarType).child("Company").child(CarCompany);
                String key = databaseReference.push().getKey();
                databaseReference.child(Model).child("Details").setValue(dataClass);
//                databaseReferenceGeneralCar2.setValue(dataClass);

                Toast.makeText(add_car.this, "Uploaded", Toast.LENGTH_SHORT).show();


                // Save details under "Renters" node
//                DatabaseReference rentersRef = FirebaseDatabase.getInstance().getReference("Renters").child(mob).child("Car").child(CarType).child("Company").child(CarCompany).child(Model).child("Details");
//                rentersRef.setValue(dataClass);

                DatabaseReference generalRefCar = FirebaseDatabase.getInstance().getReference("Admin").child("Car").child("General").child(CarType).child("Company").child(CarCompany).child(Model).child("Details");
                generalRefCar.setValue(dataClass);

                DatabaseReference car_admin = FirebaseDatabase.getInstance().getReference("Admin").child("Renters").child(mob).child("Car").child(CarType).child("Company").child(CarCompany).child(Model).child("Details");
                car_admin.setValue(dataClass);


                // Continue with the rest of your code
                for (int i = 0; i < imageList.size(); i++) {

                    String itemId = "item" + i;
                    String imageUrl = imageList.get(i).getImageUrl();
                    if (i == 0) {
                        productRefCar.child("Details").child("imageURL").setValue(imageUrl);
//                        productRefRenters.child("Details").child("imageURL").setValue(imageUrl);
                        productRefGeneral.child("Details").child("imageURL").setValue(imageUrl);
//                        productRefGeneral_d.child("imageURL").setValue(imageUrl);
                        productRefGeneral_a.child("Details").child("imageURL").setValue(imageUrl);
                    } else {
                    }
                }

                productCounter++;

                clearUI();
                imageList.clear();
                imageAdapter.notifyDataSetChanged();
                Toast.makeText(add_car.this, Model+ " added sucessfully", Toast.LENGTH_SHORT).show();
                finish();
            }

        });
    }

    // Validation method
    private boolean validateInputs() {
        String Model = modelName.getText().toString().trim();
        String Description = modelDescription.getText().toString().trim();
        String Rent = rentPerDay.getText().toString().trim();
        String MaxSpeed = maximumSpeed.getText().toString().trim();
        String FuelStatus = Fuel.getText().toString().trim();
        String Passengers = numberPassengers.getText().toString().trim();
        String CarCompany = spinner1.getSelectedItem().toString().trim();
        String CarType = spinner.getSelectedItem().toString().trim();
        String PickUpPoints = spinner2.getSelectedItem().toString().trim();

        if (Model.isEmpty()) {
            modelName.setError("Please enter a model name");
            return false;
        }

        if (Description.isEmpty()) {
            modelDescription.setError("Please enter a description");
            return false;
        }

        if (Rent.isEmpty()) {
            rentPerDay.setError("Please enter the rent per day");
            return false;
        }

        if (MaxSpeed.isEmpty()) {
            maximumSpeed.setError("Please enter the maximum speed");
            return false;
        }

        if (FuelStatus.isEmpty()) {
            Fuel.setError("Please enter the fuel status");
            return false;
        }


        if (Passengers.isEmpty()) {
            numberPassengers.setError("Please enter the number of passengers");
            return false;
        }

        if (selectedOption == null || selectedOption.isEmpty()) {
            Toast.makeText(add_car.this, "Please select a gear mode", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (CarType.equals("Car Category")) {
            Toast.makeText(add_car.this, "Please select a car category", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (CarCompany.equals("Car Company Category")) {
            Toast.makeText(add_car.this, "Please select a car company category", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (CarCompany.equals("PickUp Points")) {
            Toast.makeText(add_car.this, "Please select a pickup points", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void retrieveLastProductID() {
        databaseReference1 = FirebaseDatabase.getInstance().getReference("Admin").child("Car").child("DefaultCategory").child("DefaultCompany");
        databaseReference1.orderByKey().limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String lastProductKey = snapshot.getKey();
                    if (lastProductKey != null) {
                        String numericPart = lastProductKey.replaceAll("[^0-9]", "");
                        productCounter = Integer.parseInt(numericPart) + 1;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    private void pickImages() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Images"), PICK_IMAGES_REQUEST);
//        progressBar.setVisibility(View.VISIBLE); // Show ProgressBar when picking images

    }

    private void pickDocumentImage(String documentType, int requestCode) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select " + documentType + " Image"), requestCode);
//        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGES_REQUEST && resultCode == RESULT_OK && data != null) {
            if (data.getClipData() != null) {
                int count = data.getClipData().getItemCount();
                for (int i = 0; i < count; i++) {
                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                    uploadImage(imageUri);

                }
            } else if (data.getData() != null) {
                Uri imageUri = data.getData();
                uploadImage(imageUri);
            }
        }
        if (requestCode == PICK_RC_BOOK_REQUEST && resultCode == RESULT_OK && data != null) {
            documentImageUriRcBook = data.getData();
            Uri imageUri = data.getData();
            btnUploadRcBook.setImageURI(imageUri);
        } else if (requestCode == PICK_CHASSIS_NUMBER_REQUEST && resultCode == RESULT_OK && data != null) {
            documentImageUriChassisNumber = data.getData();
            Uri imageUri = data.getData();
            btnUploadChassisNumber.setImageURI(imageUri);
        } else if (requestCode == PICK_INSURANCE_REQUEST && resultCode == RESULT_OK && data != null) {
            documentImageUriInsurance = data.getData();
            Uri imageUri = data.getData();
            btnUploadInsurance.setImageURI(imageUri);
        }
    }

    private void uploadImage(Uri imageUri) {
//        progressBar.setVisibility(View.VISIBLE);
        String fileName = System.currentTimeMillis() + ".jpg";
        StorageReference fileReference = storageReference.child("images/" + fileName);

        UploadTask uploadTask = fileReference.putFile(imageUri);

        Task<Uri> urlTask = uploadTask.continueWithTask(task -> {
            if (!task.isSuccessful()) {
                throw task.getException();
            }
            return fileReference.getDownloadUrl();
        }).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Uri downloadUri = task.getResult();
                ImageModel imageModel = new ImageModel(downloadUri.toString(), productCounter);
                imageList.add(imageModel);
                imageAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(add_car.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void uploadDocumentImage(String documentType, Uri documentImageUri, DatabaseReference reference) {
        // Implement the code to upload document image to Firebase Storage and store its URL in the database
//        progressBar.setVisibility(View.VISIBLE);
        String fileName = System.currentTimeMillis() + "_" + documentType.toLowerCase() + ".jpg";
        StorageReference documentImageReference = storageReference.child("documents/" + fileName);

        UploadTask uploadTask = documentImageReference.putFile(documentImageUri);

        Task<Uri> urlTask = uploadTask.continueWithTask(task -> {
            if (!task.isSuccessful()) {
                throw task.getException();
            }
            return documentImageReference.getDownloadUrl();
        }).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Uri downloadUri = task.getResult();
                // Save the document image URL to the database

                saveDocumentImageUrlToDatabase(documentType, downloadUri.toString(), reference);

            } else {
                Toast.makeText(add_car.this, "Failed to upload " + documentType + " image", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Modify the references based on your database structure
    private void saveDocumentImageUrlToDatabase(String documentType, String imageUrl, DatabaseReference reference) {
        DatabaseReference carDetailsRef = reference.child("Details");

        switch (documentType) {
            case "RC Book":
                carDetailsRef.child("rcBookImageURL").setValue(imageUrl);
                break;
            case "Chassis Number":
                carDetailsRef.child("chassisNumberImageURL").setValue(imageUrl);
                break;
            case "Insurance":
                carDetailsRef.child("insuranceImageURL").setValue(imageUrl);
                break;
            // Add more cases if you have other document types
        }
    }

    private void clearUI() {

        modelName.setText("");
        modelDescription.setText("");
        rentPerDay.setText("");
        maximumSpeed.setText("");
        Fuel.setText("");

        numberPassengers.setText("");

    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Verification Alert")
                .setMessage("You not verified by Admin,Please wait for verification" +
                        "Check Your Verfication status in Profile > Verification section")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Code to handle OK button click
//                        dialog.dismiss(); // Close the dialog
                        finish();
                    }
                });
        builder.setCancelable(false);

        // Create and show the dialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


}


