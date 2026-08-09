package com.example.caronrentrenter.Payment;


import java.io.Serializable;

public class payAdmin implements Serializable {


    private String modelImageUrl;
    private String renterMobile;
    private String userMobile;
    private String carModelName;
    private String startDate;
    private String endDate;
    private String totalAmount;
    private String isConfirmed;
    private String slot;
    private String isBooked;
    private String pickupPoint;
    private String driver;
    private String isRequestCanceld;


    public payAdmin() {
    }

    public payAdmin(String modelImageUrl, String renterMobile, String userMobile, String carModelName, String startDate, String endDate, String totalAmount, String isConfirmed, String slot, String isBooked, String pickupPoint, String driver, String isRequestCanceld) {
        this.modelImageUrl = modelImageUrl;
        this.renterMobile = renterMobile;
        this.userMobile = userMobile;
        this.carModelName = carModelName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalAmount = totalAmount;
        this.isConfirmed = isConfirmed;
        this.slot = slot;
        this.isBooked = isBooked;
        this.pickupPoint = pickupPoint;
        this.driver = driver;
        this.isRequestCanceld = isRequestCanceld;
    }

    public String getModelImageUrl() {
        return modelImageUrl;
    }

    public void setModelImageUrl(String modelImageUrl) {
        this.modelImageUrl = modelImageUrl;
    }

    public String getRenterMobile() {
        return renterMobile;
    }

    public void setRenterMobile(String renterMobile) {
        this.renterMobile = renterMobile;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getCarModelName() {
        return carModelName;
    }

    public void setCarModelName(String carModelName) {
        this.carModelName = carModelName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getIsConfirmed() {
        return isConfirmed;
    }

    public void setIsConfirmed(String isConfirmed) {
        this.isConfirmed = isConfirmed;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getIsBooked() {
        return isBooked;
    }

    public void setIsBooked(String isBooked) {
        this.isBooked = isBooked;
    }

    public String getPickupPoint() {
        return pickupPoint;
    }

    public void setPickupPoint(String pickupPoint) {
        this.pickupPoint = pickupPoint;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getIsRequestCanceld() {
        return isRequestCanceld;
    }

    public void setIsRequestCanceld(String isRequestCanceld) {
        this.isRequestCanceld = isRequestCanceld;
    }
}