package com.example.caronrentrenter;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentTransaction;

import com.example.caronrentrenter.TNC.MainTNC;
import com.example.caronrentrenter.chatbot.ChatMain;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Settings extends AppCompatActivity {
    SwitchCompat switchCompat, notify;
    boolean nightmode;
    ImageView back;
    SharedPreferences sharedPreferences;

    Button editbutton;

    final private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
    String user, usern, userName, userEmail, userPassword;

    private DatabaseReference usersRef;
    private static final String SHARED_PREF_NAME = "mypref";
    SharedPreferences.Editor editor;
    TextView usen;
    RelativeLayout privacy, sentmsg, aboutus, logout, review;
    ImageView imageView;
    public static final String CHANNEL_ID = "CarRenter";
    public static final int NOTIFICATION_ID = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

//        imageView = findViewById(R.id.uid);
//        usen = findViewById(R.id.uname);
//        editbutton = findViewById(R.id.editprofile);
        switchCompat = findViewById(R.id.nnm);
        // Deva's code
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        // Ramani's code
        logout = findViewById(R.id.log_out);
        review = findViewById(R.id.review);
        //sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE);
        nightmode = sharedPreferences.getBoolean("night", false); //light mode

        back = findViewById(R.id.imgback);
        privacy = findViewById(R.id.privacy);
        sentmsg = findViewById(R.id.sentmessage);
        aboutus = findViewById(R.id.aboutus);
        notify = findViewById(R.id.notification);

        int Flag = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        boolean dark = Flag == Configuration.UI_MODE_NIGHT_YES;

        //dark mode
        if (dark) {
            switchCompat.setChecked(true);

            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        sentmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Settings.this, ChatMain.class));
            }
        });
        switchCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = switchCompat.isChecked();
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("night", true);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("night", false);
                }
                editor.apply();
            }
        });

        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Settings.this, Review.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                FirebaseAuth.getInstance().signOut();

                startActivity(new Intent(Settings.this, Login.class));

                finishAffinity();

            }
        });

        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Settings.this, MainTNC.class));
                // Create a new fragment transaction
//                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//
//                // Replace the current fragment with MainTNC
//                fragmentTransaction.replace(R.id.english,new MainTNC());
//
//                // Add the transaction to the back stack (optional)
//                fragmentTransaction.addToBackStack(null);
//
//                // Commit the transaction
//                fragmentTransaction.commit();
            }
        });

        notify.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (notify.isChecked()) {
                    Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.log_1, null);
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                    Bitmap largeicon = bitmapDrawable.getBitmap();

                    Notification notification;
                    NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        Intent resultIntent = new Intent(Settings.this, MainFragment.class);
                        PendingIntent resultingIntent = PendingIntent.getActivity(Settings.this, 1, resultIntent, PendingIntent.FLAG_IMMUTABLE);
                        notification = new Notification.Builder(Settings.this)
                                .setLargeIcon(largeicon)
                                .setSmallIcon(R.drawable.log_1)
                                .setContentText("You got new order for rent on car 😍")
                                .setSubText("Hurray!!!")
                                .setChannelId(CHANNEL_ID)
                                .setAutoCancel(true)
                                .setContentIntent(resultingIntent)
                                .build();
                        nm.createNotificationChannel(new NotificationChannel(CHANNEL_ID, "New Channel", NotificationManager.IMPORTANCE_HIGH));
                    } else {
                        notification = new Notification.Builder(Settings.this)
                                .setLargeIcon(largeicon)
                                .setSmallIcon(R.drawable.log_1)
                                .setContentText("New Message")
                                .setSubText("New Message For Car")
                                .build();
                    }
                    nm.notify(NOTIFICATION_ID, notification);
                    Toast.makeText(Settings.this, "Turn On Notification", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Settings.this, "Turn Off Notification", Toast.LENGTH_SHORT).show();
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Settings.this, MainFragment.class));
            }
        });


    }

    @SuppressLint("MissingSuperCall")
    public void onBackPressed() {
        startActivity(new Intent(Settings.this, MainFragment.class));
    }
}