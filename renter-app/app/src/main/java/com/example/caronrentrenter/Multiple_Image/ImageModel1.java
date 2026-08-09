package com.example.caronrentrenter.Multiple_Image;

import java.io.Serializable;
import java.util.List;

public class ImageModel1 implements Serializable {
    private String modelName;
    private String modelDescription;
    private String imageURL;
    private String rcBookImageURL;
    private String insuranceImageURL;
    private String chassisNumberImageURL;
    private String rentPerDay;
    private String maximumSpeed;
    private String fuel;
    private String numberPassengers;
    private String gearMode;
    private String carCompany;
    private String carType;
    private String pickUpPoint;
    private String renterMobile;
    private int productCounter;
    private String isVerified;
    private String isBooked;
    private String isUnavailable;
    private String start_date;
    private String end_date;
    private String fuelType;
    private String doors;

    private List<String> imageUrls; // Add this line


    // Add any additional fields as needed


    public ImageModel1() {

    }

    public ImageModel1(String modelName, String modelDescription, String imageURL, String rcBookImageURL, String insuranceImageURL, String chassisNumberImageURL, String rentPerDay, String maximumSpeed, String fuel, String numberPassengers, String gearMode, String carCompany, String carType, String pickUpPoint, String renterMobile, int productCounter, String isVerified, String isBooked, String isUnavailable, String start_date, String end_date, String fuelType, String doors, List<String> imageUrls) {
        this.modelName = modelName;
        this.modelDescription = modelDescription;
        this.imageURL = imageURL;
        this.rcBookImageURL = rcBookImageURL;
        this.insuranceImageURL = insuranceImageURL;
        this.chassisNumberImageURL = chassisNumberImageURL;
        this.rentPerDay = rentPerDay;
        this.maximumSpeed = maximumSpeed;
        this.fuel = fuel;
        this.numberPassengers = numberPassengers;
        this.gearMode = gearMode;
        this.carCompany = carCompany;
        this.carType = carType;
        this.pickUpPoint = pickUpPoint;
        this.renterMobile = renterMobile;
        this.productCounter = productCounter;
        this.isVerified = isVerified;
        this.isBooked = isBooked;
        this.isUnavailable = isUnavailable;
        this.start_date = start_date;
        this.end_date = end_date;
        this.fuelType = fuelType;
        this.doors = doors;
        this.imageUrls = imageUrls;
    }


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

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getRcBookImageURL() {
        return rcBookImageURL;
    }

    public void setRcBookImageURL(String rcBookImageURL) {
        this.rcBookImageURL = rcBookImageURL;
    }

    public String getInsuranceImageURL() {
        return insuranceImageURL;
    }

    public void setInsuranceImageURL(String insuranceImageURL) {
        this.insuranceImageURL = insuranceImageURL;
    }

    public String getChassisNumberImageURL() {
        return chassisNumberImageURL;
    }

    public void setChassisNumberImageURL(String chassisNumberImageURL) {
        this.chassisNumberImageURL = chassisNumberImageURL;
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

    public String getPickUpPoint() {
        return pickUpPoint;
    }

    public void setPickUpPoint(String pickUpPoint) {
        this.pickUpPoint = pickUpPoint;
    }

    public String getRenterMobile() {
        return renterMobile;
    }

    public void setRenterMobile(String renterMobile) {
        this.renterMobile = renterMobile;
    }

    public int getProductCounter() {
        return productCounter;
    }

    public void setProductCounter(int productCounter) {
        this.productCounter = productCounter;
    }

    public String getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(String isVerified) {
        this.isVerified = isVerified;
    }

    public String getIsBooked() {
        return isBooked;
    }

    public void setIsBooked(String isBooked) {
        this.isBooked = isBooked;
    }

    public String getIsUnavailable() {
        return isUnavailable;
    }

    public void setIsUnavailable(String isUnavailable) {
        this.isUnavailable = isUnavailable;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getDoors() {
        return doors;
    }

    public void setDoors(String doors) {
        this.doors = doors;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
}