package com.example.car_admin;

import org.osmdroid.util.GeoPoint;

public class SerializableGeoPoint {
    private double latitude;
    private double longitude;

    // Required default constructor for Firebase deserialization
    public SerializableGeoPoint() {
    }

    public SerializableGeoPoint(GeoPoint geoPoint) {
        this.latitude = geoPoint.getLatitude();
        this.longitude = geoPoint.getLongitude();
    }

    public GeoPoint toGeoPoint() {
        return new GeoPoint(latitude, longitude);
    }

    // Add getters and setters if needed
}
