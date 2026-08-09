package com.example.caronrentrenter;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Detail extends AppCompatActivity {

    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "emailShare";
    private static final long AUTO_SCROLL_INTERVAL = 3000; // 3 seconds, adjust as needed
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Admin").child("Users");
    private final DatabaseReference databaseReference_High = FirebaseDatabase.getInstance().getReference("Admin").child("Car").child("General").child("Company");
    private final Handler handler = new Handler(Looper.getMainLooper());
    String mob, eem, driv;
    RadioGroup radioGroup;
    CheckBox chbDriver;
    String selectedOption, txtPickUpPoint, dll, isVer1;
    RadioButton driverRD, rdb_female, rdb_others;
    String ddl1;
    private SharedPreferences sharedPreferences;
    private String emailShare, name, isVer, isUnAv;
    private DataClass object;
    private DatabaseReference usersRef, usersRef1, usersRef2;
    private String rmob, mobiii, mob1, imageUrl;
    private ReadWriteUserDetails object1;
    private Button submit;
    private TextView titleTxt, txtRating, txtPassengers, txtGear, txtMaxSpeed, txtfuel,txtdr,txtPickUpPoint1;
    private TextView txtRname, txtMob;
    private ImageView call, message;
    private String firebaseImageUrl;
    private ImageView img, img_renter, img_favourite, isVerified;
    private RecyclerView recyclerViewPopular, recyclerViewNew;
    private ViewPager2 viewPagerImages;
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
    private boolean userVerifiedDialogShown = false;
    private boolean renterVerifiedDialogShown = false;

    private boolean isFavorite = false; // Define a boolean flag to track favorite status

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        titleTxt = findViewById(R.id.txtcname);
        txtRating = findViewById(R.id.txtrating);
        txtPassengers = findViewById(R.id.txtpassanger);
        txtGear = findViewById(R.id.txtgear);
        txtMaxSpeed = findViewById(R.id.txtMaxSpeed);
        txtfuel = findViewById(R.id.txtfuel);
        txtdr = findViewById(R.id.txtdr);
        img_renter = findViewById(R.id.img_renter);
        img_favourite = findViewById(R.id.img_favourite);
        isVerified = findViewById(R.id.isVerified);
        submit = findViewById(R.id.button);
        call = findViewById(R.id.img_call);
        message = findViewById(R.id.img_message);
        txtRname = findViewById(R.id.txtrname);
        txtMob = findViewById(R.id.txtMob);
        viewPagerImages = findViewById(R.id.viewPagerImages);
        //txtPickUpPoint1 = findViewById(R.id.txtPickUpPoint);


        chbDriver = findViewById(R.id.chbDriver);
        driv = "no";


        chbDriver.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    // Checkbox is checked
                    driv = "yes";
//                    Toast.makeText(Detail.this, "true", Toast.LENGTH_SHORT).show();
                } else {
                    // Checkbox is unchecked
                    driv = "no";
//                    Toast.makeText(Detail.this, "false", Toast.LENGTH_SHORT).show();
                }
            }
        });

        usersRef2 = FirebaseDatabase.getInstance().getReference("Admin").child("Users");

        sharedPreferences = getSharedPreferences("email_1", MODE_PRIVATE);

        eem = sharedPreferences.getString("email", "AAA");

        System.out.println("/////////////////////*////////////////// Email " + eem);
        usersRef2.orderByChild("email").equalTo(eem).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    dll = userSnapshot.child("dll").getValue(String.class);
                    isVer1 = userSnapshot.child("isVerified").getValue(String.class);

                    if (isVer1.equals("false")) {
                        isVerified.setVisibility(View.VISIBLE);
                        showAlertDialog3();
                    } else {
                        isVerified.setVisibility(View.GONE);
                    }
                    if (TextUtils.isEmpty(dll)) {
                        showAlertDialog5();
                    } else {
                        usersRef.orderByChild("mobile").equalTo(object.getRenterMobile()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                    String imageUrl = userSnapshot.child("imageURLUser").getValue(String.class);
                                    Glide.with(Detail.this).load(imageUrl).into(img_renter);
                                    name = userSnapshot.child("name").getValue(String.class);
                                    rmob = userSnapshot.child("mobile").getValue(String.class);
                                    isVer = userSnapshot.child("isVerified").getValue(String.class);
                                    isUnAv = userSnapshot.child("isUnAvailable").getValue(String.class);
                                }
                                txtRname.setText(name);
                                txtMob.setText(rmob);

                                mobiii = rmob;

                                if (isVer.equals("true")) {
                                    isVerified.setVisibility(View.VISIBLE);
                                } else {
                                    isVerified.setVisibility(View.GONE);
                                    showAlertDialog2();
                                    return;
                                }


                                if (isUnAv.equals("true") || object.getIsUnavailable().equals("true")) {

                                    if (isUnAv.equals("true")) {
                                        showAlertDialog();
                                    } else {
                                        showAlertDialog1();

                                    }
                                    submit.setEnabled(false);
                                    Toast.makeText(Detail.this, "Not available", Toast.LENGTH_SHORT).show();
                                }


                                if(object.getIsDriverConnected().equals("true")){
                                    chbDriver.setVisibility(View.GONE);
                                }else{
                                    chbDriver.setVisibility(View.VISIBLE);
                                }

                                img_favourite.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {


                                        usersRef1 = FirebaseDatabase.getInstance().getReference("Admin").child("Users");

                                        sharedPreferences = getSharedPreferences("email_1", MODE_PRIVATE);

                                        eem = sharedPreferences.getString("email", "AAA");

                                        usersRef1.orderByChild("email").equalTo(eem).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                                    ddl1 = userSnapshot.child("Favorite").child("name").getValue(String.class);

                                                    String uMob = userSnapshot.child("mobile").getValue(String.class);
//                            String dd = String.valueOf(databaseReference.child(mob).child("Favorite").child("name"));
                                                    DataClass dataClass = new DataClass(object.getModelName(), object.getModelDescription(), object.getImageURL(), object.getRcBookImageURL(), object.getInsuranceImageURL(), object.getChassisNumberImageURL(),
                                                            object.getRentPerDay(), object.getMaximumSpeed(), object.getFuel(), object.getNumberPassengers(), object.getGearMode(), object.getCarCompany(), object.getCarType(), object.getPickUpPoint(),
                                                            object.getRenterMobile(), object.getProductCounter(), object.getIsVerified(), object.getIsBooked(), object.getIsUnavailable(), object.getStart_date(), object.getEnd_date(), object.getFuelType(), object.getDoors(),"","","","", object.getImageUrls());

                                                    databaseReference.child(uMob).child("Favorite").child(object.getModelName()).setValue(dataClass);
//                                                    DatabaseReference db1 = FirebaseDatabase.getInstance().getReference("Admin").child("Users").child(uMob).child("Favorite").child(object.getModelName()).child("start_date");
//                                                    db1.setValue("true");

//                                                        databaseReference.child(uMob).child("Favorite").child(object.getModelName()).child("imageURL").setValue(firebaseImageUrl.toString());
//                                                        databaseReference.child(uMob).child("Favorite").child(object.getModelName()).child("modelName").setValue(object.getModelName().toString());
//                                                        databaseReference.child(uMob).child("Favorite").child(object.getModelName()).child("rentPerDay").setValue(object.getRentPerDay().toString());
//                                                        databaseReference.child(uMob).child("Favorite").child(object.getModelName()).child("renterMobile").setValue(object.getRenterMobile().toString());
//                                                        databaseReference.child(uMob).child("Favorite").child(object.getModelName()).child("maximumSpeed").setValue(object.getMaximumSpeed().toString());
//                                                        databaseReference.child(uMob).child("Favorite").child(object.getModelName()).child("numberPassengers").setValue(object.getNumberPassengers().toString());

                                                    Snackbar snackbar = Snackbar.make(findViewById(R.id.cardview), "Favourite Item", Snackbar.LENGTH_LONG);
                                                    snackbar.setAction("Dismiss", new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            snackbar.dismiss();
                                                        }
                                                    });
                                                    snackbar.show();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {
                                            }
                                        });





                                    }
                                });


                                call.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", rmob, null));
                                        startActivity(intent);
                                    }
                                });

                                message.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(Intent.ACTION_SENDTO);
                                        intent.putExtra("sms_body", "Hey," + name);
                                        intent.setData(Uri.parse("sms:  " + rmob));
                                        startActivity(intent);
                                    }
                                });
                                if (object.getIsVerified().equals("false")) {
                                    showAlertDialog4();
                                } else {
                                    isVerified.setVisibility(View.VISIBLE);
                                }

                                submit.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        String mname = object.getModelName();
                                        String mpic = object.getImageURL();
                                        String mprice = object.getRentPerDay();
                                        String rMo = object.getRenterMobile();
                                        String mType = object.getCarType();
                                        String mCom = object.getCarCompany();
                                        String pPoint = object.getPickUpPoint();

                                        Intent intent;
                                        if (!Objects.equals(dll, "")) {
                                            intent = new Intent(Detail.this, Date_Book.class);
                                            intent.putExtra("moname", mname);
                                            intent.putExtra("mpic", mpic);
                                            intent.putExtra("mprice", mprice);
                                            intent.putExtra("rMo", rMo);
                                            intent.putExtra("mType", mType);
                                            intent.putExtra("mCom", mCom);
                                            intent.putExtra("pPoint", pPoint);
                                            intent.putExtra("dri", driv);

                                            Toast.makeText(Detail.this, mname, Toast.LENGTH_SHORT).show();

                                            startActivity(intent);
                                        } else if (Objects.equals(dll, "")) {
                                            showAlertDialog5();
                                        } else {
                                            Toast.makeText(Detail.this, "Error....", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                                // Start automatic sliding
                                handler.postDelayed(runnable, AUTO_SCROLL_INTERVAL);

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });

                    }
                    System.out.println("/////////////////////*////////////////// DLL " + dll);

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                // Check which radio button is selected
//                RadioButton radioButton = findViewById(checkedId);
//
//                if (radioButton != null) {
//                    selectedOption = radioButton.getText().toString();
//                    Toast.makeText(Detail.this, "Selected option: " + selectedOption, Toast.LENGTH_SHORT).show();
//                }
//            }
//        });


        getBundle();
        object = (DataClass) getIntent().getSerializableExtra("object1");
        firebaseImageUrl = object.getImageURL();
        titleTxt.setText(object.getModelName());

        txtPassengers.setText(object.getNumberPassengers().toString());
        txtGear.setText(object.getGearMode().toString());
        txtMaxSpeed.setText(object.getMaximumSpeed());
        txtfuel.setText(object.getFuelType());
        txtdr.setText(object.getDoors());
        txtRname.setText(object.getRenterMobile());
        mob = object.getRenterMobile();
        txtPickUpPoint = object.getPickUpPoint();

        //showAlertDialog5();
        ImageSliderAdapter imageSliderAdapter = new ImageSliderAdapter(object.getImageUrls());
        viewPagerImages.setAdapter(imageSliderAdapter);

        usersRef = FirebaseDatabase.getInstance().getReference("Admin").child("Renters");


    }

    private void getBundle() {
        // Handle bundle if needed
    }

    private void showAlertDialog() {
        new MaterialAlertDialogBuilder(this, R.style.AlertDialogTheme)
                .setTitle("Renter Alert")
                .setMessage("So sorry, This Renter is unavailable,Please find another")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Code to handle OK button click
//                        dialog.dismiss(); // Close the dialog
                        finish();
                    }
                })
                .setCancelable(false)
                .show();
    }

    private void showAlertDialog1() {
        new MaterialAlertDialogBuilder(this, R.style.AlertDialogTheme)
                .setTitle("Car Alert")
                .setMessage("So sorry, This Car is unavailable,Please find another")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Code to handle OK button click
//                        dialog.dismiss(); // Close the dialog
                        finish();
                    }
                })
                .setCancelable(false)
                .show();
    }

    private void showAlertDialog2() {
        new MaterialAlertDialogBuilder(this, R.style.AlertDialogTheme)
                .setTitle("Verification Alert")
                .setMessage("This renter is not verified by admin,Please find another")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Code to handle OK button click
//                        dialog.dismiss(); // Close the dialog
                        finish();
                    }
                })
                .setCancelable(false)
                .show();
        renterVerifiedDialogShown = true;
    }

    private void showAlertDialog3() {
        new MaterialAlertDialogBuilder(this, R.style.AlertDialogTheme)
                .setTitle("Verification Alert")
                .setMessage("You are not verified, check your verification status in Profile > Verification section")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Code to handle OK button click
//                        dialog.dismiss(); // Close the dialog
                        finish();
                    }
                })
                .setCancelable(false)
                .show();
        userVerifiedDialogShown = true;


    }

    private void showAlertDialog4() {
        new MaterialAlertDialogBuilder(this, R.style.AlertDialogTheme)
                .setTitle("Verification Alert")
                .setMessage("Car is not verified by Admin")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Code to handle OK button click
//                        dialog.dismiss(); // Close the dialog
                        finish();
                    }
                })
                .setCancelable(false)
                .show();
    }

    private void showAlertDialog5() {
        new MaterialAlertDialogBuilder(this, R.style.AlertDialogTheme)
                .setTitle("Verification Alert")
                .setMessage("Please complete your profile")
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String mname = object.getModelName();
                        String mpic = object.getImageURL();
                        String mprice = object.getRentPerDay();
                        String rMo = object.getRenterMobile();
                        String mType = object.getCarType();
                        String mCom = object.getCarCompany();
                        String pPoint = object.getPickUpPoint();


                        Intent intent2;
                        intent2 = new Intent(Detail.this, CompleteProfile.class);
                        intent2.putExtra("moname", mname);
                        intent2.putExtra("mpic", mpic);
                        intent2.putExtra("mprice", mprice);
                        intent2.putExtra("rMo", rMo);
                        intent2.putExtra("mType", mType);
                        intent2.putExtra("mCom", mCom);
                        intent2.putExtra("pPoint", pPoint);
                        intent2.putExtra("dri", driv);
                        startActivity(intent2);
                        finish();
                    }
                })
                .setCancelable(false)
                .show();
    }
}