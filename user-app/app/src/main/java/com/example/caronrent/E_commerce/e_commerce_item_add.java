package com.example.caronrent.E_commerce;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.caronrent.E_commerce.ImgeAdapter_e_com;
import com.example.caronrent.E_commerce.ImageModel_e_com;
//import com.example.caronrent.E_commerce.ImageModel_e_com;
//import com.example.caronrent.E_commerce;
import com.example.caronrent.R;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

public class e_commerce_item_add extends AppCompatActivity {

    public static final int PICK_IMAGES_REQUEST = 1;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "emailShare";
    private DatabaseReference usersRef;
    String emailShare;
    String mob;
    private ImageButton btnUpload;
    private FloatingActionButton btnAddItem;
    private EditText edtPrice, edtQuantity;
    private RecyclerView recyclerView;
    private List<ImageModel_e_com> imageList;
    private ImgeAdapter_e_com ImgeAdapter_e_com;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReference1;
    private DatabaseReference databaseReferenceGeneralCar;
    private DatabaseReference databaseReferenceGeneralRenters;
    private DatabaseReference databaseReferenceGeneralAdmin;

    EditText modelName, modelDescription, gCompany,gPrice, deliveryDay, replace;
    String selectedOption;
    ProgressBar progressBar;
    RadioGroup radioGroup;

    Spinner spinner;

    List<String> items;
    String item,eem;
    List<String> items1;
    String item1;

    private int productCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecommerce_item_add);


        spinner = findViewById(R.id.spinner);


        items = new ArrayList<>();
        items.add(0, "Gadget Category");
        items.add("Decoration");
        items.add("Modification");
        items.add("Safety");
        items.add("Maintain");
        items.add("Others");

        spinner.setAdapter(new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, items));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!(spinner.getSelectedItem().toString().equals("Gadget Category"))) {
                    item = spinner.getSelectedItem().toString();

                } else {
                    // Handle the case when "Car Category" is selected
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        modelName = findViewById(R.id.modelName);
        modelDescription = findViewById(R.id.modelDescription);
        gCompany = findViewById(R.id.gCompany);
        gPrice = findViewById(R.id.gPrice);
        deliveryDay = findViewById(R.id.deliveryDay);
        replace = findViewById(R.id.replace);



        progressBar = findViewById(R.id.progressBar);

        btnUpload = findViewById(R.id.btnUpload);
        btnAddItem = findViewById(R.id.btnAddItem);
        recyclerView = findViewById(R.id.recyclerView_1);

        imageList = new ArrayList<>();
        ImgeAdapter_e_com = new ImgeAdapter_e_com(imageList);



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

                    //                    usern = userSnapshot.child("name").getValue(String.class);
                    //                    userName = userSnapshot.child("username").getValue(String.class);
                    //                    userEmail = userSnapshot.child("email").getValue(String.class);
                    //                    userPassword = userSnapshot.child("password").getValue(String.class);
                    //
                    //                    role = userSnapshot.child("role").getValue(String.class);

                    // Now you can use the imageUrl in your app, e.g., to load the image using an image loading library like Glide or Picasso.
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle the error, if any.
            }
        });



        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(ImgeAdapter_e_com);

        btnUpload.setOnClickListener(v -> pickImages());

        storageReference = FirebaseStorage.getInstance().getReference();

        retrieveLastProductID();

        btnAddItem.setOnClickListener(view -> {
            if (imageList.isEmpty()) {
                Toast.makeText(e_commerce_item_add.this, "Please select images", Toast.LENGTH_SHORT).show();
                return;
            }

            if (validateInputs()) {
                progressBar.setVisibility(View.VISIBLE);
                String Model = modelName.getText().toString();
                String Description = modelDescription.getText().toString();
                String gCom = gCompany.getText().toString();
                String gPri = gPrice.getText().toString();
                String delD = deliveryDay.getText().toString();
                String repL = replace.getText().toString();

                String gCat = spinner.getSelectedItem().toString();

                // Continue with the rest of your code

                databaseReferenceGeneralCar = FirebaseDatabase.getInstance().getReference("Admin").child("Gadgets").child("General").child(gCat).child("Company").child(gCom);
//                databaseReferenceGeneralRenters = FirebaseDatabase.getInstance().getReference("Admin").child("Renters").child(mob).child("Car").child("General").child("Company").child(gCom);
                databaseReferenceGeneralAdmin = FirebaseDatabase.getInstance().getReference("Admin").child("Renters").child(mob).child("Car").child("General").child("Company").child(gCom);

                // Modify references to point to "Car" node
                databaseReference1 = FirebaseDatabase.getInstance().getReference("Admin").child("Gadgets").child(gCat).child("Company").child(gCom).child(Model);
                String newProductId = "Product" + productCounter;
                DatabaseReference productRefCar = databaseReference1;

                // Modify references to point to "Renters" node
//                DatabaseReference productRefRenters = FirebaseDatabase.getInstance().getReference("Admin").child("Renters").child(mob).child("Gadgets").child(gCat).child("Company").child(gCom).child(Model);
                DatabaseReference productRefAdmin = FirebaseDatabase.getInstance().getReference("Admin").child("Renters").child(mob).child("Gadgets").child(gCat).child("Company").child(gCom).child(Model);
                DatabaseReference productRefGeneral = FirebaseDatabase.getInstance().getReference("Admin").child("Gadgets").child("General").child(gCat).child("Company").child(gCom).child(Model);

                // Get the list of image URLs from your imageList
                List<String> imageUrls = new ArrayList<>();
                for (ImageModel_e_com ImageModel_e_com : imageList) {
                    imageUrls.add(ImageModel_e_com.getImageUrl());
                }

                String imageURL="";

                ImageModel_1_e_com dataClass = new ImageModel_1_e_com(Model, Description,imageURL,gCom, gPri,delD,repL,gCat, mob, productCounter, imageUrls);

//                    ImageModel_1_e_com dataClass = new ImageModel_1_e_com(Model, Description, Rent, MaxSpeed, FuelStatus, Rate, Passengers, selectedOption, CarCompany, CarType, mob,productCounter);
                // Modify references to point to "Car" node
                databaseReference = FirebaseDatabase.getInstance().getReference("Admin").child("Gadgets").child(gCat).child("Company").child(gCom);
                String key = databaseReference.push().getKey();
                databaseReference.child(Model).child("Details").setValue(dataClass);
                Toast.makeText(e_commerce_item_add.this, "Uploaded", Toast.LENGTH_SHORT).show();


                // Save details under "Renters" node
//                DatabaseReference rentersRef = FirebaseDatabase.getInstance().getReference("Admin").child("Renters").child(mob).child("Gadgets").child(gCat).child("Company").child(gCom).child(Model).child("Details");
//                rentersRef.setValue(dataClass);

                DatabaseReference adminRef = FirebaseDatabase.getInstance().getReference("Admin").child("Renters").child(mob).child("Gadgets").child(gCat).child("Company").child(gCom).child(Model).child("Details");
                adminRef.setValue(dataClass);

                DatabaseReference generalRefCar = FirebaseDatabase.getInstance().getReference("Admin").child("Gadgets").child("General").child(gCat).child("Company").child(gCom).child(Model).child("Details");
                generalRefCar.setValue(dataClass);

                for (int i = 0; i < imageList.size(); i++) {

                    String itemId = "item" + i;
                    String imageUrl = imageList.get(i).getImageUrl();

                    if (i == 0) {
                        productRefCar.child("Details").child("imageURL").setValue(imageUrl);
//                        productRefRenters.child("Details").child("imageURL").setValue(imageUrl);
                        productRefGeneral.child("Details").child("imageURL").setValue(imageUrl);
                        productRefAdmin.child("Details").child("imageURL").setValue(imageUrl);

                    }else {
                    }
                }
                productCounter++;
                clearUI();
                imageList.clear();
                ImgeAdapter_e_com.notifyDataSetChanged();
                Toast.makeText(e_commerce_item_add.this, Model  +" added successfully", Toast.LENGTH_SHORT).show();
                finish();
//                startActivity(new Intent(e_commerce_item_add.this, NotificationFragment.class));
            }

        });
    }

    // Validation method
    private boolean validateInputs() {
        String Model = modelName.getText().toString().trim();
        String Description = modelDescription.getText().toString().trim();
        String GCom = gCompany.getText().toString().trim();
        String GPri = gPrice.getText().toString().trim();
        String GelD = deliveryDay.getText().toString().trim();
        String RepL = replace.getText().toString().trim();

        String gCat = spinner.getSelectedItem().toString().trim();

        if (Model.isEmpty()) {
            modelName.setError("Please enter a model name");
            return false;
        }

        if (Description.isEmpty()) {
            modelDescription.setError("Please enter a description");
            return false;
        }

        if (GCom.isEmpty()) {
            gCompany.setError("Please enter gadget's company");
            return false;
        }

        if (GPri.isEmpty()) {
            gPrice.setError("Please enter gadget's price");
            return false;
        }

        if (GelD.isEmpty()) {
            deliveryDay.setError("Please enter delivery date");
            return false;
        }

        if (RepL.isEmpty()) {
            replace.setError("Please enter replacement Days");
            return false;
        }

        if (gCat.equals("Gadget Category")) {
            Toast.makeText(e_commerce_item_add.this, "Please select a Gadget category", Toast.LENGTH_SHORT).show();
            return false;
        }



        return true;
    }





    private void retrieveLastProductID() {
        databaseReference1 = FirebaseDatabase.getInstance().getReference("Admin").child("Gadgets").child("DefaultCategory").child("DefaultCompany");
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
    }

    private void uploadImage(Uri imageUri) {
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
                ImageModel_e_com ImageModel_e_com = new ImageModel_e_com(downloadUri.toString(), productCounter);
                imageList.add(ImageModel_e_com);
                ImgeAdapter_e_com.notifyDataSetChanged();
            } else {
                Toast.makeText(e_commerce_item_add.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearUI() {

        modelName.setText("");
        modelDescription.setText("");
        gCompany.setText("");
        gPrice.setText("");
        deliveryDay.setText("");
        replace.setText("");

    }


    // ... (rest of the code)
}









