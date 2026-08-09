package com.example.car_admin.Location_Of_Car;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.car_admin.LocationData;
import com.example.car_admin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

public class Live_Data_Of_Location extends AppCompatActivity {

    TextView locationText;
    private DatabaseReference locationReference;
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Admin").child("Drivers").child("General");
    String mob;
    private IMapController mapController;
    private MapView mapView;
    Button startLocationBtn, stopLocationBtn;
    private Marker currentLocationMarker;

    double latitude;
    double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configure OSMDroid with a custom user agent
        Configuration.getInstance().setUserAgentValue("your_app_name");

        setContentView(R.layout.activity_live_data_of_location);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Intent in = getIntent();
        mob = in.getStringExtra("mob");

        // Use the path corresponding to your database structure
        locationReference = database.getReference("Admin").child("Drivers").child("General").child(mob).child("Live_Location");

        // Initialize the Firebase database reference
//        locationReference = FirebaseDatabase.getInstance().getReference("locations");

        startLocationBtn = findViewById(R.id.startLocationBtn);
        stopLocationBtn = findViewById(R.id.stopLocationBtn);
        locationText = findViewById(R.id.locationText);
        // Initialize the map
        mapView = findViewById(R.id.mapView);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);

        // Initialize the map controller
        mapController = mapView.getController();




        startLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(mob).child("admin_access_state").setValue("true");

            }
        });

        stopLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(mob).child("admin_access_state").setValue("false");

            }
        });

        // Uncomment the following line if you want to write a sample location
        // writeSampleLocation();

        // Add a ValueEventListener to fetch live location data
        locationReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                LocationData locationData = snapshot.getValue(LocationData.class);
                if (locationData != null) {
                    latitude = locationData.getLatitude();
                    longitude = locationData.getLongitude();
                    // Now you can use latitude and longitude in your app
                    // Update UI or map with the fetched location data
                    updateMap(latitude, longitude);
                }

                String lt = String.valueOf(latitude);
                String lg = String.valueOf(longitude);
                locationText.setText("LT" + lt + "*" + "LG" + lg);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error if needed
            }
        });
    }

    // Uncomment the following method if you want to write a sample location
    // private void writeSampleLocation() {
    //     LocationData sampleLocation = new LocationData(37.7749, -122.4194); // San Francisco, CA
    //     locationReference.child("sample").setValue(sampleLocation);
    // }

    // Add this method to update your map with the fetched location
    private void updateMap(double latitude, double longitude) {
        GeoPoint geoPoint = new GeoPoint(latitude, longitude);
        mapController.animateTo(geoPoint);

        // Zoom to the current location
        mapController.setCenter(geoPoint);
        mapController.animateTo(geoPoint);
        mapController.setZoom(18.0); // You can adjust the zoom level as needed

        // Add a marker to the map
        addCurrentLocationMarker(geoPoint);
    }

    private void addCurrentLocationMarker(GeoPoint geoPoint) {
        if (mapView != null) {
            if (currentLocationMarker != null) {
                mapView.getOverlays().remove(currentLocationMarker);
            }
            currentLocationMarker = new Marker(mapView);
            currentLocationMarker.setPosition(geoPoint);
            mapView.getOverlays().add(currentLocationMarker);
            mapView.invalidate();
        }
    }
}