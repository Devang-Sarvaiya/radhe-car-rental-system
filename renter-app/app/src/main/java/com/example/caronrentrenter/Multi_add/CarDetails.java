package com.example.caronrentrenter.Multi_add;

public class CarDetails {
    private String modelName;
    private String modelDescription;
    private String rentPerDay;
    private String maximumSpeed;
    private String fuel;
    private String carRating;
    private String numberPassengers;
    private String gearMode;
    private String carCompany;
    private String carType;
    private String renterMobile;
    private String imageUrl;

    public CarDetails() {
        // Default constructor required for Firebase
    }

    public CarDetails(String modelName, String modelDescription, String rentPerDay, String maximumSpeed,
                      String fuel, String carRating, String numberPassengers, String gearMode,
                      String carCompany, String carType, String renterMobile, String imageUrl) {
        this.modelName = modelName;
        this.modelDescription = modelDescription;
        this.rentPerDay = rentPerDay;
        this.maximumSpeed = maximumSpeed;
        this.fuel = fuel;
        this.carRating = carRating;
        this.numberPassengers = numberPassengers;
        this.gearMode = gearMode;
        this.carCompany = carCompany;
        this.carType = carType;
        this.renterMobile = renterMobile;
        this.imageUrl = imageUrl;
    }

    // Getter and setter methods for each field (generated automatically or write manually)


    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
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
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
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
        return carCompany;
    }

    public void setCarCompany(String carCompany) {
        this.carCompany = carCompany;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getRenterMobile() {
        return renterMobile;
    }

    public void setRenterMobile(String renterMobile) {
        this.renterMobile = renterMobile;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}