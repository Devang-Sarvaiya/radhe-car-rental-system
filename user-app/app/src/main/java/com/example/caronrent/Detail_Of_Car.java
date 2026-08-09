package com.example.caronrent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.caronrent.Adapter.ItemAdapter;
import com.example.caronrent.Add_car.C_ItemAdapter;
import com.example.caronrent.Add_car.ImageModel1;
import com.example.caronrent.BookingHistory.bookHistory;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Detail_Of_Car extends AppCompatActivity {


    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "emailShare";

    String emailShare, name, eem;


    private ImageView call, message;

    private RecyclerView recyclerViewPopular, recyclerViewNew;
    private ImageModel1 object;
    TextView titleTxt, txtRating, txtPassengers, txtGear, txtMaxSpeed, txtRname, txtMob, txtRent, txtRent2, txtDescription, txtCarType, txt_verified, txtdoor, txtFuel;
    ImageView img, img_renter, img_back, img_favourite;
    private DatabaseReference databaseReference_High = FirebaseDatabase.getInstance().getReference("Admin").child("Car").child("General");
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Admin").child("Renters");

    private ItemAdapter adapter;
    String rmob, mob, mobiii;
    String ddl1;
    private DatabaseReference usersRef, usersRef1, usersRef2;
    ConstraintLayout Book_history;

    TextView txt_status;
    private ViewPager2 viewPagerImages;


    Button book_now;

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            int currentItem = viewPagerImages.getCurrentItem();
            int totalItems = viewPagerImages.getAdapter() != null ? viewPagerImages.getAdapter().getItemCount() : 0;

            if (currentItem < totalItems - 1) {
                viewPagerImages.setCurrentItem(currentItem + 1);
            } else {
                viewPagerImages.setCurrentItem(0);
            }

            handler.postDelayed(this, AUTO_SCROLL_INTERVAL); // Define AUTO_SCROLL_INTERVAL as per your requirement
        }
    };

    private static final long AUTO_SCROLL_INTERVAL = 3000; // 3 seconds, adjust as needed


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_of_car);

        titleTxt = findViewById(R.id.txtTitle);
//        img = findViewById(R.id.img_car);
        img_renter = findViewById(R.id.img_renter);
        img_favourite = findViewById(R.id.img_favourite);

        txtRating = findViewById(R.id.txtRating);
        txtPassengers = findViewById(R.id.txtPassengers);
        txtGear = findViewById(R.id.txtgear);
        txtMaxSpeed = findViewById(R.id.txtMaxSpeed);
        txtRname = findViewById(R.id.txtrname);
        txtMob = findViewById(R.id.txtMob);
        txtRent = findViewById(R.id.txtRent);
        txtdoor = findViewById(R.id.txtdoor);
        txtFuel = findViewById(R.id.txtFuel);
        txtRent2 = findViewById(R.id.txtRent2);
        txt_verified = findViewById(R.id.txt_verified);
        txtDescription = findViewById(R.id.txtDescription);
        img_back = findViewById(R.id.img_back);
//        txtCarType = findViewById(R.id.txtCarType);
        txt_status = findViewById(R.id.txt_status);
        Book_history = findViewById(R.id.Book_history);
        viewPagerImages = findViewById(R.id.viewPagerImages);


//        call = findViewById(R.id.img_call);
//        message = findViewById(R.id.img_message);


//        book_now = findViewById(R.id.book_now);


        sharedPreferences = getSharedPreferences("email_1", MODE_PRIVATE);


        eem = sharedPreferences.getString("email", "AAA");
//        imgProfile = findViewById(R.id.imagProfile);
//        txtName = findViewById(R.id.txtName);
//        txtName.setText(emailShare);
        String desiredUsername = eem;
        usersRef1 = FirebaseDatabase.getInstance().getReference("Admin").child("Users");

        // Retrieve the user's image URL
        usersRef1.orderByChild("email").equalTo(desiredUsername).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
//                    String imageUrl = userSnapshot.child("imageURLUser").getValue(String.class);
//                    Glide.with(Detail_Of_Car.this).load(imageUrl).into(img_renter);

                    mob = userSnapshot.child("mobile").getValue(String.class);
                }
//                txtRname.setText(name);
//                txtMob.setText(rmob);

                mobiii = mob;
//                Toast.makeText(Detail_Of_Car.this, mobiii, Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle the error, if any.
            }
        });

//        Toast.makeText(Detail_Of_Car.this, mobiii, Toast.LENGTH_SHORT).show();


        object = (ImageModel1) getIntent().getSerializableExtra("object");


//
//        String firebaseImageUrl = object.getImageURL();
//        Glide.with(this)
//                .load(firebaseImageUrl)
//                .into(img);

        titleTxt.setText(object.getModelName());

        txtPassengers.setText(object.getNumberPassengers().toString() + " Passengers");
        txtGear.setText(object.getGearMode().toString());
        txtMaxSpeed.setText(object.getMaximumSpeed());
        txtRent.setText(object.getRentPerDay());
        txtRent2.setText(object.getRentPerDay());
        txtDescription.setText(object.getModelDescription());
        //txtCarType.setText(object.getCarType());
        titleTxt.setText(object.getModelName());
        txtdoor.setText(object.getDoors() + " Doors");
        txtFuel.setText(object.getFuelType());

        if (object.getIsVerified().equals("true")) {
            txt_verified.setText("Verified");
//            txt_status.setTextColor(Integer.parseInt("#FFFFFF"));
        } else {
            txt_verified.setText("UnVerified");
        }


        String com = object.getCarCompany();
        String mob = object.getRenterMobile();


        ImageSliderAdapter imageSliderAdapter = new ImageSliderAdapter(object.getImageUrls());
        viewPagerImages.setAdapter(imageSliderAdapter);


//        usersRef = FirebaseDatabase.getInstance().getReference("Admin").child("Renters");
        usersRef = FirebaseDatabase.getInstance().getReference("Admin").child("Drivers").child("General");

        // Retrieve the user's image URL
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String CarName = userSnapshot.child("carName").getValue(String.class);
                    if (CarName.equals(object.getModelName())) {
                        String imageUrl = userSnapshot.child("imageURLUser").getValue(String.class);
                        Glide.with(Detail_Of_Car.this).load(imageUrl).into(img_renter);
                        name = userSnapshot.child("name").getValue(String.class);
                        rmob = userSnapshot.child("mobile_dr").getValue(String.class);
                    }else{
                        txtRname.setText("Not Connected");
                        txtMob.setText("");
                    }
                }
                txtRname.setText(name);
                txtMob.setText(rmob);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle the error, if any.
            }
        });


        if (object.getIsUnavailable().equals("true")) {
            txt_status.setText("UnAvailable");
            img_favourite.setImageResource(R.drawable.unavailable);
//            txt_status.setTextColor(Integer.parseInt("#FFFFFF"));
        } else {
            txt_status.setText("Available");
            img_favourite.setImageResource(R.drawable.available);
        }

        img_favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (object.getIsUnavailable().equals("true")) {
//                    txt_status.setText("UnAvailable");

                    AlertDialog.Builder builder = new AlertDialog.Builder(Detail_Of_Car.this);
                    builder.setTitle("Car Alert")
                            .setMessage("Are you want to set this Car online ?")
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DatabaseReference db = FirebaseDatabase.getInstance().getReference("Admin").child("Car").child("General").child(object.getCarType()).child("Company").child(object.getCarCompany()).child(object.getModelName()).child("Details").child("isUnavailable");
                                    DatabaseReference db1 = FirebaseDatabase.getInstance().getReference("Admin").child("Car").child(object.getCarType()).child("Company").child(object.getCarCompany()).child(object.getModelName()).child("Details").child("isUnavailable");
                                    DatabaseReference db2 = FirebaseDatabase.getInstance().getReference("Admin").child("Renters").child(object.getRenterMobile()).child("Car").child(object.getCarType()).child("Company").child(object.getCarCompany()).child(object.getModelName()).child("Details").child("isUnavailable");

                                    db.setValue("false");
                                    db1.setValue("false");
                                    db2.setValue("false");
                                    Toast.makeText(Detail_Of_Car.this, "Car Successfully set Online", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                    builder.setCancelable(false);

                    // Create and show the dialog
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();


                } else {
//                    txt_status.setText("Available");

                    AlertDialog.Builder builder = new AlertDialog.Builder(Detail_Of_Car.this);
                    builder.setTitle("Car Alert")
                            .setMessage("Are you want to set this Car offline ?")
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DatabaseReference db = FirebaseDatabase.getInstance().getReference("Admin").child("Car").child("General").child(object.getCarType()).child("Company").child(object.getCarCompany()).child(object.getModelName()).child("Details").child("isUnavailable");
                                    DatabaseReference db1 = FirebaseDatabase.getInstance().getReference("Admin").child("Car").child(object.getCarType()).child("Company").child(object.getCarCompany()).child(object.getModelName()).child("Details").child("isUnavailable");
                                    DatabaseReference db2 = FirebaseDatabase.getInstance().getReference("Admin").child("Renters").child(object.getRenterMobile()).child("Car").child(object.getCarType()).child("Company").child(object.getCarCompany()).child(object.getModelName()).child("Details").child("isUnavailable");

                                    db.setValue("true");
                                    db1.setValue("true");
                                    db2.setValue("true");
                                    Toast.makeText(Detail_Of_Car.this, "Car Successfully set Offline", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                    builder.setCancelable(false);

                    // Create and show the dialog
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                }
            }

        });


//        img_favourite.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                usersRef2 = FirebaseDatabase.getInstance().getReference("Admin").child("Renters");
//                String dd = String.valueOf(databaseReference.child(mobiii).child("Favorite").child("name"));
//                // Retrieve the user's image URL
//                usersRef2.orderByChild("mobile").equalTo(mobiii).addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
//                            ddl1 = userSnapshot.child("Favorite").child("name").getValue(String.class);
//
//                            databaseReference.child(mobiii).child("Favorite").child(object.getModelName()).child("imageURL").setValue(firebaseImageUrl.toString());
//                            databaseReference.child(mobiii).child("Favorite").child(object.getModelName()).child("modelName").setValue(object.getModelName().toString());
//                            databaseReference.child(mobiii).child("Favorite").child(object.getModelName()).child("rentPerDay").setValue(object.getRentPerDay().toString());
//                            databaseReference.child(mobiii).child("Favorite").child(object.getModelName()).child("maximumSpeed").setValue(object.getMaximumSpeed().toString());
//
//                            startActivity(new Intent(Detail_Of_Car.this, Favorite.class));
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        // Handle the error, if any.
//                    }
//                });
//            }
//        });

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Book_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Detail_Of_Car.this, bookHistory.class);
                String mname = object.getModelName();
                intent.putExtra("mName", mname);
                startActivity(intent);
            }
        });

//
//        message.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_SENDTO);
//                intent.putExtra("sms_body", "Hey," + name);
//                intent.setData(Uri.parse("sms:  " + rmob));
//                startActivity(intent);
//            }
//        });


        handler.postDelayed(runnable, AUTO_SCROLL_INTERVAL);

    }

    private void getBundle() {
        object = (ImageModel1) getIntent().getSerializableExtra("object");
        String firebaseImageUrl = object.getImageURL();
        Glide.with(this)
                .load(firebaseImageUrl)
                .into(img);
        titleTxt.setText(object.getModelName());
    }
}



