package com.example.caronrentrenter;

import java.io.Serializable;

public class DetailClass implements Serializable {
    private String modelName;

    private String imageURL;
    private String modelDescription;
    // private String price;


    private String rentPerDay;
    private String maximumSpeed;
    private String Fuel;
    private String carRating;
    private String numberPassengers;
    private String gearMode;
    private String CarCompany;

    public DetailClass() {
    }

    public DetailClass(String modelName, String imageURL, String modelDescription, String rentPerDay, String maximumSpeed, String fuel, String carRating, String numberPassengers, String gearMode, String carCompany) {
        this.modelName = modelName;
        this.imageURL = imageURL;
        this.modelDescription = modelDescription;
        this.rentPerDay = rentPerDay;
        this.maximumSpeed = maximumSpeed;
        Fuel = fuel;
        this.carRating = carRating;
        this.numberPassengers = numberPassengers;
        this.gearMode = gearMode;
        CarCompany = carCompany;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getModelDescription() {
        return modelDescription;
    }

    public void setModelDescription(String modelDescription) {
        this.modelDescription = modelDescription;
    }

    public String getRentPerDay() {
        return rentPerDay;
    }

    public void setRentPerDay(String rentPerDay) {
        this.rentPerDay = rentPerDay;
    }

    public String getMaximumSpeed() {
        return maximumSpeed;
    }

    public void setMaximumSpeed(String maximumSpeed) {
        this.maximumSpeed = maximumSpeed;
    }

    public String getFuel() {
        return Fuel;
    }

    public void setFuel(String fuel) {
        Fuel = fuel;
    }

    public String getCarRating() {
        return carRating;
    }

    public void setCarRating(String carRating) {
        this.carRating = carRating;
    }

    public String getNumberPassengers() {
        return numberPassengers;
    }

    public void setNumberPassengers(String numberPassengers) {
        this.numberPassengers = numberPassengers;
    }

    public String getGearMode() {
        return gearMode;
    }

    public void setGearMode(String gearMode) {
        this.gearMode = gearMode;
    }

    public String getCarCompany() {
        return CarCompany;
    }

    public void setCarCompany(String carCompany) {
        CarCompany = carCompany;
    }
}
