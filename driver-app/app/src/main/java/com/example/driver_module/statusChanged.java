package com.example.driver_module;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class statusChanged extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "emailShare";
    private DatabaseReference usersRef;
    String eem, name, mob, isVer, isUnAv, cType, cName, cCom, rMob,dMob;
    TextView txtStatus;
    Button btnChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_status_changed);
        sharedPreferences = getSharedPreferences("email_1", MODE_PRIVATE);


        eem = sharedPreferences.getString("email", "AAA");
        usersRef = FirebaseDatabase.getInstance().getReference("Admin").child("Drivers").child("General");



        txtStatus = findViewById(R.id.txtStatus);
        btnChange = findViewById(R.id.btnChange);

        /*   intent.putExtra("rMob",rMob);
                        intent.putExtra("mName",cName);
                        intent.putExtra("mType",cType);
                        intent.putExtra("mCom",cCom);*/
        Intent in = getIntent();
        cName = in.getStringExtra("mName");
        cCom = in.getStringExtra("mCom");
        cType = in.getStringExtra("mType");
        isUnAv = in.getStringExtra("unav");
        rMob = in.getStringExtra("rMob");
        dMob = in.getStringExtra("dMob");

        if(isUnAv.equals("false")){
            txtStatus.setText("Available");
        }else {

            txtStatus.setText("Unavailable");

        }

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isUnAv.equals("false")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(statusChanged.this);
                    builder.setTitle("Status Alert")
                            .setMessage("Are you want to set your status as Unavailable ?")
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
//                                            DatabaseReference db = FirebaseDatabase.getInstance().getReference("Admin").child("Car").child("General").child(object.getCarType()).child("Company").child(object.getCarCompany()).child(object.getModelName()).child("Details").child("isUnavailable");
//                                            DatabaseReference db1 = FirebaseDatabase.getInstance().getReference("Admin").child("Car").child(object.getCarType()).child("Company").child(object.getCarCompany()).child(object.getModelName()).child("Details").child("isUnavailable");
                                    DatabaseReference db2 = FirebaseDatabase.getInstance().getReference("Admin").child("Car").child("General").child(cType).child("Company").child(cCom).child(cName).child("Details");
                                    db2.child("isDriverConnected").setValue("true");
                                    DatabaseReference db3 = FirebaseDatabase.getInstance().getReference("Admin").child("Drivers").child("General").child(dMob);
                                    db3.child("isUnAvailable").setValue("true");


                                    txtStatus.setText("Unavailable");
                                    startActivity(new Intent(statusChanged.this, MainActivity.class));

                                    Toast.makeText(statusChanged.this, "Your status Successfully updated", Toast.LENGTH_SHORT).show();
                                }
                            });
                    builder.setCancelable(false);

                    // Create and show the dialog
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();


                } else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(statusChanged.this);
                    builder.setTitle("Status Alert")
                            .setMessage("Are you want to set your status as Available ?")
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
//                                            DatabaseReference db = FirebaseDatabase.getInstance().getReference("Admin").child("Car").child("General").child(object.getCarType()).child("Company").child(object.getCarCompany()).child(object.getModelName()).child("Details").child("isUnavailable");
//                                            DatabaseReference db1 = FirebaseDatabase.getInstance().getReference("Admin").child("Car").child(object.getCarType()).child("Company").child(object.getCarCompany()).child(object.getModelName()).child("Details").child("isUnavailable");
                                    DatabaseReference db2 = FirebaseDatabase.getInstance().getReference("Admin").child("Car").child("General").child(cType).child("Company").child(cCom).child(cName).child("Details");
                                    db2.child("isDriverConnected").setValue("false");
                                    DatabaseReference db3 = FirebaseDatabase.getInstance().getReference("Admin").child("Drivers").child("General").child(dMob);
                                    db3.child("isUnAvailable").setValue("false");

                                    txtStatus.setText("Available");
                                    startActivity(new Intent(statusChanged.this, MainActivity.class));

                                    Toast.makeText(statusChanged.this, "Your status Successfully updated", Toast.LENGTH_SHORT).show();
                                }
                            });
                    builder.setCancelable(false);

                    // Create and show the dialog
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                }
            }


        });



//        usersRef.orderByChild("email").equalTo(eem).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
//
//                    String imageUrl = userSnapshot.child("imageURLUser").getValue(String.class);
////                    Glide.with(statusChanged.this).load(imageUrl).into(img_Profile);
//                    name = userSnapshot.child("name").getValue(String.class);
//                    mob = userSnapshot.child("mobile").getValue(String.class);
//                    isVer = userSnapshot.child("isVerified").getValue(String.class);
//                    isUnAv = userSnapshot.child("isUnAvailable").getValue(String.class);
//
//                }
//
//                if (isUnAv.equals("true")) {
//                    txtStatus.setText("Unavailable");
//                } else {
//                    txtStatus.setText("Available");
//                }
//
//                btnChange.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (isUnAv.equals("false")) {
//
//
//                            AlertDialog.Builder builder = new AlertDialog.Builder(statusChanged.this);
//                            builder.setTitle("Status Alert")
//                                    .setMessage("Are you want to set your status as Unavailable ?")
//                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            dialog.dismiss();
//                                        }
//                                    })
//                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
////                                            DatabaseReference db = FirebaseDatabase.getInstance().getReference("Admin").child("Car").child("General").child(object.getCarType()).child("Company").child(object.getCarCompany()).child(object.getModelName()).child("Details").child("isUnavailable");
////                                            DatabaseReference db1 = FirebaseDatabase.getInstance().getReference("Admin").child("Car").child(object.getCarType()).child("Company").child(object.getCarCompany()).child(object.getModelName()).child("Details").child("isUnavailable");
//                                            DatabaseReference db2 = FirebaseDatabase.getInstance().getReference("Admin").child("Renters").child(mob).child("isUnAvailable");
//                                            db2.setValue("true");
//                                            txtStatus.setText("Unavailable");
//                                            startActivity(new Intent(statusChanged.this, MainActivity.class));
//
//                                            Toast.makeText(statusChanged.this, "Your status Successfully updated", Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//                            builder.setCancelable(false);
//
//                            // Create and show the dialog
//                            AlertDialog alertDialog = builder.create();
//                            alertDialog.show();
//
//
//                        } else {
//
//                            AlertDialog.Builder builder = new AlertDialog.Builder(statusChanged.this);
//                            builder.setTitle("Status Alert")
//                                    .setMessage("Are you want to set your status as Available ?")
//                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            dialog.dismiss();
//                                        }
//                                    })
//                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
////                                            DatabaseReference db = FirebaseDatabase.getInstance().getReference("Admin").child("Car").child("General").child(object.getCarType()).child("Company").child(object.getCarCompany()).child(object.getModelName()).child("Details").child("isUnavailable");
////                                            DatabaseReference db1 = FirebaseDatabase.getInstance().getReference("Admin").child("Car").child(object.getCarType()).child("Company").child(object.getCarCompany()).child(object.getModelName()).child("Details").child("isUnavailable");
//                                            DatabaseReference db2 = FirebaseDatabase.getInstance().getReference("Admin").child("Renters").child(mob).child("isUnAvailable");
//                                            db2.setValue("false");
//                                            txtStatus.setText("Available");
//                                            startActivity(new Intent(statusChanged.this, MainActivity.class));
//
//                                            Toast.makeText(statusChanged.this, "Your status Successfully updated", Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//                            builder.setCancelable(false);
//
//                            // Create and show the dialog
//                            AlertDialog alertDialog = builder.create();
//                            alertDialog.show();
//
//                        }
//                    }
//
//
//                });        }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Handle the error, if any.
//            }
//        });


    }
}