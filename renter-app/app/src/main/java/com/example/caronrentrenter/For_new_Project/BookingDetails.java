package com.example.caronrentrenter.For_new_Project;

public class BookingDetails {
    private String carModelName;
    private String startDate;
    private String endDate;

    public BookingDetails(String carModelName, String startDate, String endDate) {
        this.carModelName = carModelName;
        this.startDate = startDate;
        this.endDate = endDate;
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

    //    public BookingDetails(String modelName, String startDate, String endDate) {
//        this.modelName = modelName;
//        this.startDate = startDate;
//        this.endDate = endDate;
//    }
//
//    public String getModelName() {
//        return modelName;
//    }
//
//    public String getStartDate() {
//        return startDate;
//    }
//
//    public String getEndDate() {
//        return endDate;
//    }
}
