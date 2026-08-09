package com.example.driver_module.Live_location;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color; // Import Color
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.driver_module.R;
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
import org.osmdroid.views.overlay.Polyline; // Import Polyline

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Location_info extends AppCompatActivity implements LocationListener {

    private SharedPreferences sharedPreferences;
    String eem, mob, state;
    private DatabaseReference usersRef;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    private TextView locationText;
    private MapView mapView;
    private LocationManager locationManager;
    private IMapController mapController;
    private boolean isLocationUpdatesStarted = false;
    private Marker currentLocationMarker;

    // Add a list to store GeoPoints representing the path
    private List<GeoPoint> pathGeoPoints = new ArrayList<>();
    private Polyline pathPolyline;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configure OSMDroid with a custom user agent
        Configuration.getInstance().setUserAgentValue("your_app_name");

        setContentView(R.layout.activity_location_info);

        locationText = findViewById(R.id.locationText);
        mapView = findViewById(R.id.mapView);

        Configuration.getInstance().load(this, getPreferences(MODE_PRIVATE));
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);

        // Set the initial map center and zoom level to focus on India
        GeoPoint indiaCenter = new GeoPoint(20.5937, 78.9629); // Coordinates for India's center
        mapController = mapView.getController();
        mapController.setZoom(5.0); // Zoom level to show India
        mapController.setCenter(indiaCenter); // Center the map on India

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        checkLocationPermissions();

        Intent in = getIntent();
        mob = in.getStringExtra("Mob");
//        mob = "9000000007";

        sharedPreferences = getSharedPreferences("email_1", MODE_PRIVATE);
        eem = sharedPreferences.getString("email", "AAA");
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Admin").child("Drivers").child("General");

        db.orderByChild("mobile_dr").equalTo(mob).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    state = userSnapshot.child("admin_access_state").getValue(String.class);
//                    mob = userSnapshot.child("mobile_dr").getValue(String.class);
                }
                System.out.println("---------------------------" + state);
                if (state.equals("true")) {
                    checkLocationPermissions();
                    // Check if location updates have already been started
                    if (!isLocationUpdatesStarted) {
                        startLocationUpdates();
                        isLocationUpdatesStarted = true;
                    }
                } else {
                    stopLocationUpdates();
                    isLocationUpdatesStarted = false;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle the error, if any.
            }
        });
    }

    private void checkLocationPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
//            startLocationUpdates();
            Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }
    }

    private void startLocationUpdates() {
        if (locationManager != null) {
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            } else {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                    isLocationUpdatesStarted = true;
                    Toast.makeText(this, "Location updates started", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Location permission not granted", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void stopLocationUpdates() {
        if (locationManager != null && isLocationUpdatesStarted) {
            locationManager.removeUpdates(this);
            isLocationUpdatesStarted = false;
            Toast.makeText(this, "Location updates stopped", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            updateLocationText(location);
            updateMap(location);
        }
    }

    private void updateLocationText(Location location) {
        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            // Using Geocoder to get the address from latitude and longitude
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                if (addresses != null && addresses.size() > 0) {
                    Address address = addresses.get(0);
                    String locationString = "Latitude: " + latitude +
                            ", Longitude: " + longitude +
                            "\nPlace: " + address.getLocality(); // You can customize the address format
                    locationText.setText("Location: " + locationString);
                } else {
                    locationText.setText("Location not available");
                }
            } catch (IOException e) {
                e.printStackTrace();
                locationText.setText("Error fetching location");
            }
        } else {
            locationText.setText("Location not available");
        }
    }

    private void updateMap(Location location) {
        if (mapView != null && location != null) {
            GeoPoint geoPoint = new GeoPoint(location.getLatitude(), location.getLongitude());
            addCurrentLocationMarker(geoPoint);

            // Add the current location to the path
            pathGeoPoints.add(geoPoint);

            // Draw the path on the map
            drawPath();

            // Store the current location in Firebase Realtime Database
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Admin");
            databaseReference.child("Drivers").child("General").child(mob).child("Live_Location").setValue(geoPoint);

            // Zoom to the current location
            mapController.setCenter(geoPoint);
            mapController.animateTo(geoPoint);
            mapController.setZoom(18.0);
        }
    }

    private void drawPath() {
        if (mapView != null) {
            if (pathPolyline != null) {
                mapView.getOverlayManager().remove(pathPolyline);
            }

            // Create a Polyline using the list of GeoPoints
            pathPolyline = new Polyline();
            pathPolyline.setPoints(pathGeoPoints);
            pathPolyline.setColor(Color.BLUE);
            pathPolyline.setWidth(5.0f);

            // Add the Polyline to the map
            mapView.getOverlayManager().add(pathPolyline);
            mapView.invalidate();
        }
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

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // Handle status changes if needed
    }

    @Override
    public void onProviderEnabled(String provider) {
        // Handle provider enabled if needed
    }

    @Override
    public void onProviderDisabled(String provider) {
        // Handle provider disabled if needed
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopLocationUpdates();
    }

    public void onStartLocationButtonClick(View view) {
//        startLocationUpdates();
    }

    public void onStopLocationButtonClick(View view) {
//        stopLocationUpdates();
    }
}
