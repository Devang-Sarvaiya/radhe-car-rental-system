//package com.example.caronrentrenter.Multiple_Image;
//
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.net.Uri;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.ProgressBar;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.Spinner;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.GridLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.caronrentrenter.R;
//import com.google.android.gms.tasks.Task;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.UploadTask;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Multiple_Image extends AppCompatActivity {
//
//    public static final int PICK_IMAGES_REQUEST = 1;
//
//    SharedPreferences sharedPreferences;
//    private static final String SHARED_PREF_NAME = "mypref";
//    private static final String KEY_NAME = "emailShare";
//    private DatabaseReference usersRef;
//    String emailShare;
//    String mob;
//
//    private ImageButton btnUpload;
//    private FloatingActionButton btnAddItem;
//    private EditText edtPrice, edtQuantity;
//    private RecyclerView recyclerView;
//    private List<ImageModel> imageList;
//    private ImageAdapter imageAdapter;
//    private StorageReference storageReference;
//    private DatabaseReference databaseReference;
//    private DatabaseReference databaseReference1;
//    private DatabaseReference databaseReferenceGeneralCar;
//    private DatabaseReference databaseReferenceGeneralRenters;
//
//    EditText modelName, modelDescription, rentPerDay, maximumSpeed, Fuel, carRating, numberPassengers;
//    String selectedOption;
//    ProgressBar progressBar;
//    RadioGroup radioGroup;
//    RadioButton rdb_mannual, rdb_auto;
//    Spinner spinner, spinner1;
//
//    List<String> items;
//    String item;
//    List<String> items1;
//    String item1;
//
//    private int productCounter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_multiple_image);
//
//        spinner = findViewById(R.id.spinner);
//        spinner1 = findViewById(R.id.spinner1);
//
//        items = new ArrayList<>();
//        items.add(0, "Car Category");
//        items.add("Sports");
//        items.add("Wedding");
//        items.add("Tour");
//        items.add("Off roading");
//        items.add("Transport");
//        items.add("Luxuries");
//        items.add("General");
//        spinner.setAdapter(new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, items));
//
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (!(spinner.getSelectedItem().toString().equals("Car Category"))) {
//                    item = spinner.getSelectedItem().toString();
//                    Toast.makeText(Multiple_Image.this, item, Toast.LENGTH_SHORT).show();
//                } else {
//                    // Handle the case when "Car Category" is selected
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//
//        items1 = new ArrayList<>();
//        items1.add(0, "Car Company Category");
//        items1.add("Tata");
//        items1.add("Mahindra");
//        items1.add("Maruti");
//        items1.add("Lamborghini");
//        items1.add("Audi");
//        items1.add("Bmw");
//        items1.add("Skoda");
//        items1.add("Others");
//
//        spinner1.setAdapter(new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, items1));
//
//        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (!(spinner1.getSelectedItem().toString().equals("Car Company Category"))) {
//                    item1 = spinner1.getSelectedItem().toString();
//                    Toast.makeText(Multiple_Image.this, item1, Toast.LENGTH_SHORT).show();
//                } else {
//                    // Handle the case when "Car Company Category" is selected
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//
//        modelName = findViewById(R.id.modelName);
//        modelDescription = findViewById(R.id.modelDescription);
//        rentPerDay = findViewById(R.id.rentPerDay);
//        maximumSpeed = findViewById(R.id.maximumSpeed);
//        Fuel = findViewById(R.id.Fuel);
//        carRating = findViewById(R.id.carRating);
//        radioGroup = findViewById(R.id.radioGroup1);
//        rdb_mannual = findViewById(R.id.rdb_mannual);
//        rdb_auto = findViewById(R.id.rdb_auto);
//        numberPassengers = findViewById(R.id.numberPassengers);
//        progressBar = findViewById(R.id.progressBar);
//        progressBar.setVisibility(View.INVISIBLE);
//
//        btnUpload = findViewById(R.id.btnUpload);
//        btnAddItem = findViewById(R.id.btnAddItem);
//        recyclerView = findViewById(R.id.recyclerView_1);
//
//        imageList = new ArrayList<>();
//        imageAdapter = new ImageAdapter(imageList);
//
//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                // Check which radio button is selected
//                RadioButton radioButton = findViewById(checkedId);
//
//                if (radioButton != null) {
//                    selectedOption = radioButton.getText().toString();
//                    Toast.makeText(Multiple_Image.this, "Selected Gear Mode: " + selectedOption, Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//
//        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
//        emailShare = sharedPreferences.getString(KEY_NAME, null);
//
//        usersRef = FirebaseDatabase.getInstance().getReference("Renters");
//
//
//        usersRef.orderByChild("email").equalTo(emailShare).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
//                    String imageUrl = userSnapshot.child("imageURLUser").getValue(String.class);
////                    Glide.with(Car_item_add.this).load(imageUrl).into(showProfilePic);
//
//                    mob = userSnapshot.child("mobile").getValue(String.class);
//
////                    usern = userSnapshot.child("name").getValue(String.class);
////                    userName = userSnapshot.child("username").getValue(String.class);
////                    userEmail = userSnapshot.child("email").getValue(String.class);
////                    userPassword = userSnapshot.child("password").getValue(String.class);
////
////                    role = userSnapshot.child("role").getValue(String.class);
//
//                    // Now you can use the imageUrl in your app, e.g., to load the image using an image loading library like Glide or Picasso.
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Handle the error, if any.
//            }
//        });
//
//
//
//        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(imageAdapter);
//
//        btnUpload.setOnClickListener(v -> pickImages());
//
//        storageReference = FirebaseStorage.getInstance().getReference();
//
//        retrieveLastProductID();
//
//        btnAddItem.setOnClickListener(view -> {
//
//            if (imageList.isEmpty()) {
//                Toast.makeText(Multiple_Image.this, "Please select images", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            if (validateInputs()) {
//
//                String Model = modelName.getText().toString();
//                String Description = modelDescription.getText().toString();
//                String Rent = rentPerDay.getText().toString();
//                String MaxSpeed = maximumSpeed.getText().toString();
//                String FuelStatus = Fuel.getText().toString();
//                String Rate = carRating.getText().toString();
//                String Passengers = numberPassengers.getText().toString();
//                String gearMode = selectedOption;
//                String CarCompany = spinner1.getSelectedItem().toString();
//                String CarType = spinner.getSelectedItem().toString();
//
//
//
//
//                // Continue with the rest of your code
//
//                databaseReferenceGeneralCar = FirebaseDatabase.getInstance().getReference("Car").child("General").child(CarType).child("Company").child(CarCompany);
//                databaseReferenceGeneralRenters = FirebaseDatabase.getInstance().getReference("Renters").child("78787878787878").child("Car").child("General").child("Company").child(CarCompany);
//
//                // Modify references to point to "Car" node
//                databaseReference1 = FirebaseDatabase.getInstance().getReference("Car").child(CarType).child("Company").child(CarCompany);
//                String newProductId = "Product" + productCounter;
//                DatabaseReference productRefCar = databaseReference1.child(newProductId);
//
//                // Modify references to point to "Renters" node
//                DatabaseReference productRefRenters = FirebaseDatabase.getInstance().getReference("Renters").child("78787878787878").child("Car").child(CarType).child("Company").child(CarCompany).child(newProductId);
//                DatabaseReference productRefGeneral = FirebaseDatabase.getInstance().getReference("Car").child("General").child(CarType).child("Company").child(CarCompany).child(newProductId);
//
//                ImageModel1 dataClass = new ImageModel1(Model, Description, Rent, MaxSpeed, FuelStatus, Rate, Passengers, selectedOption, CarCompany, CarType, productCounter);
//
//                // Modify references to point to "Car" node
//                databaseReference = FirebaseDatabase.getInstance().getReference("Car").child(CarType).child("Company").child(CarCompany);
//                String key = databaseReference.push().getKey();
//                databaseReference.child(Model).setValue(dataClass);
//                progressBar.setVisibility(View.INVISIBLE);
//                Toast.makeText(Multiple_Image.this, "Uploaded", Toast.LENGTH_SHORT).show();
//
//                for (int i = 0; i < imageList.size(); i++) {
//                    String itemId = "item" + i;
//                    String imageUrl = imageList.get(i).getImageUrl();
//                    // Save images under both "Car" and "Renters" nodes
//                    productRefCar.child("Pic").child(itemId).setValue(imageUrl);
//                    productRefRenters.child("Pic").child(itemId).setValue(imageUrl);
//                    productRefGeneral.child("Pic").child(itemId).setValue(imageUrl);
//                }
//
//                productRefCar.child("PID").setValue(newProductId);
//                productRefRenters.child("PID").setValue(newProductId);
//
//                // Save details under "Renters" node
//                DatabaseReference rentersRef = FirebaseDatabase.getInstance().getReference("Renters").child("78787878787878").child("Car").child(CarType).child("Company").child(CarCompany).child("Details").child(newProductId);
//                rentersRef.setValue(dataClass);
//
//                DatabaseReference generalRefCar = FirebaseDatabase.getInstance().getReference("Car").child("General").child(CarType).child("Company").child(CarCompany).child("Details").child(newProductId);
//                generalRefCar.setValue(dataClass);
//
//                productCounter++;
//
//                clearUI();
//                imageList.clear();
//                imageAdapter.notifyDataSetChanged();
//
//                Toast.makeText(Multiple_Image.this, "Item added successfully", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    // Validation method
//    private boolean validateInputs() {
//        String Model = modelName.getText().toString().trim();
//        String Description = modelDescription.getText().toString().trim();
//        String Rent = rentPerDay.getText().toString().trim();
//        String MaxSpeed = maximumSpeed.getText().toString().trim();
//        String FuelStatus = Fuel.getText().toString().trim();
//        String Rate = carRating.getText().toString().trim();
//        String Passengers = numberPassengers.getText().toString().trim();
//        String CarCompany = spinner1.getSelectedItem().toString().trim();
//        String CarType = spinner.getSelectedItem().toString().trim();
//
//        if (Model.isEmpty()) {
//            modelName.setError("Please enter a model name");
//            return false;
//        }
//
//        if (Description.isEmpty()) {
//            modelDescription.setError("Please enter a description");
//            return false;
//        }
//
//        if (Rent.isEmpty()) {
//            rentPerDay.setError("Please enter the rent per day");
//            return false;
//        }
//
//        if (MaxSpeed.isEmpty()) {
//            maximumSpeed.setError("Please enter the maximum speed");
//            return false;
//        }
//
//        if (FuelStatus.isEmpty()) {
//            Fuel.setError("Please enter the fuel status");
//            return false;
//        }
//
//        if (Rate.isEmpty()) {
//            carRating.setError("Please enter the car rating");
//            return false;
//        }
//
//        if (Passengers.isEmpty()) {
//            numberPassengers.setError("Please enter the number of passengers");
//            return false;
//        }
//
//        if (selectedOption == null || selectedOption.isEmpty()) {
//            Toast.makeText(Multiple_Image.this, "Please select a gear mode", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        if (CarType.equals("Car Category")) {
//            Toast.makeText(Multiple_Image.this, "Please select a car category", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        if (CarCompany.equals("Car Company Category")) {
//            Toast.makeText(Multiple_Image.this, "Please select a car company category", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        return true;
//    }
//
//
//
//
//
//    private void retrieveLastProductID() {
//        databaseReference1 = FirebaseDatabase.getInstance().getReference("Car").child("DefaultCategory").child("DefaultCompany");
//        databaseReference1.orderByKey().limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    String lastProductKey = snapshot.getKey();
//                    if (lastProductKey != null) {
//                        String numericPart = lastProductKey.replaceAll("[^0-9]", "");
//                        productCounter = Integer.parseInt(numericPart) + 1;
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                // Handle error
//            }
//        });
//    }
//
//    private void pickImages() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Select Images"), PICK_IMAGES_REQUEST);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == PICK_IMAGES_REQUEST && resultCode == RESULT_OK && data != null) {
//            if (data.getClipData() != null) {
//                int count = data.getClipData().getItemCount();
//                for (int i = 0; i < count; i++) {
//                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
//                    uploadImage(imageUri);
//                }
//            } else if (data.getData() != null) {
//                Uri imageUri = data.getData();
//                uploadImage(imageUri);
//            }
//        }
//    }
//
//    private void uploadImage(Uri imageUri) {
//        String fileName = System.currentTimeMillis() + ".jpg";
//        StorageReference fileReference = storageReference.child("images/" + fileName);
//
//        UploadTask uploadTask = fileReference.putFile(imageUri);
//
//        Task<Uri> urlTask = uploadTask.continueWithTask(task -> {
//            if (!task.isSuccessful()) {
//                throw task.getException();
//            }
//            return fileReference.getDownloadUrl();
//        }).addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                Uri downloadUri = task.getResult();
//                ImageModel imageModel = new ImageModel(downloadUri.toString(), productCounter);
//                imageList.add(imageModel);
//                imageAdapter.notifyDataSetChanged();
//            } else {
//                Toast.makeText(Multiple_Image.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void clearUI() {
//
//        modelName.setText("");
//        modelDescription.setText("");
//        rentPerDay.setText("");
//        maximumSpeed.setText("");
//        Fuel.setText("");
//        carRating.setText("");
//        numberPassengers.setText("");
//
//    }
//
//
//    // ... (rest of the code)
//}
