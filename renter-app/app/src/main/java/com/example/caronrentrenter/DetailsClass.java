package com.example.caronrentrenter;

public class DetailsClass {

    private String modelName;
    private String modelDescription;
    private String rentPerDay;
    private String maximumSpeed;
    private String fuel;
    private String carRating;
    private String numberPassengers;
    private String selectedOption;
    private String carCompany;
    private String carType;
    private String uploaderMobile; // Assuming the uploader's mobile number is part of the details

    public DetailsClass() {
        // Default constructor required for Firebase
    }

    public DetailsClass(String modelName, String modelDescription, String rentPerDay, String maximumSpeed, String fuel, String carRating, String numberPassengers, String selectedOption, String carCompany, String carType, String uploaderMobile) {
        this.modelName = modelName;
        this.modelDescription = modelDescription;
        this.rentPerDay = rentPerDay;
        this.maximumSpeed = maximumSpeed;
        this.fuel = fuel;
        this.carRating = carRating;
        this.numberPassengers = numberPassengers;
        this.selectedOption = selectedOption;
        this.carCompany = carCompany;
        this.carType = carType;
        this.uploaderMobile = uploaderMobile;
    }

    public String getModelName() {
        return modelName;
    }

    public String getModelDescription() {
        return modelDescription;
    }

    public String getRentPerDay() {
        return rentPerDay;
    }

    public String getMaximumSpeed() {
        return maximumSpeed;
    }

    public String getFuel() {
        return fuel;
    }

    public String getCarRating() {
        return carRating;
    }

    public String getNumberPassengers() {
        return numberPassengers;
    }

    public String getSelectedOption() {
        return selectedOption;
    }

    public String getCarCompany() {
        return carCompany;
    }

    public String getCarType() {
        return carType;
    }

    public String getUploaderMobile() {
        return uploaderMobile;
    }
}
