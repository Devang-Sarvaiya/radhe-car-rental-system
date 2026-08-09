package com.example.driver_module.BookHistory;

import java.io.Serializable;

public class bookAdmin implements Serializable {
    private String modelImageUrl;
    private String renterMobile;
    private String userMobile;
    private String carModelName;
    private String startDate;
    private String endDate;
    private String totalAmount;
    private String pickupPoint;
    private String driver;
    private String isRenterPaymentDone;
    private String isDriverPaymentDone;
    private String isDepositPaymentDone;
    private String slot;
    private String isRefund;
    private String isExtended;
    private String isUpdated;
    private String isSelfCanceld;
    private String isRenterCanceld;
    private String isRenterAccepted;
    private String isAppliedReturn;
    private String isCarReceived;
    private String isRefundDone;
    private String isCanceld;
    private String rentPerDay;

    public bookAdmin() {
    }

    public bookAdmin(String modelImageUrl, String renterMobile, String userMobile, String carModelName, String startDate, String endDate, String totalAmount, String pickupPoint, String driver, String isRenterPaymentDone, String isDriverPaymentDone, String isDepositPaymentDone, String slot, String isRefund, String isExtended, String isUpdated, String isSelfCanceld, String isRenterCanceld, String isRenterAccepted, String isAppliedReturn, String isCarReceived, String isRefundDone, String isCanceld, String rentPerDay) {
        this.modelImageUrl = modelImageUrl;
        this.renterMobile = renterMobile;
        this.userMobile = userMobile;
        this.carModelName = carModelName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalAmount = totalAmount;
        this.pickupPoint = pickupPoint;
        this.driver = driver;
        this.isRenterPaymentDone = isRenterPaymentDone;
        this.isDriverPaymentDone = isDriverPaymentDone;
        this.isDepositPaymentDone = isDepositPaymentDone;
        this.slot = slot;
        this.isRefund = isRefund;
        this.isExtended = isExtended;
        this.isUpdated = isUpdated;
        this.isSelfCanceld = isSelfCanceld;
        this.isRenterCanceld = isRenterCanceld;
        this.isRenterAccepted = isRenterAccepted;
        this.isAppliedReturn = isAppliedReturn;
        this.isCarReceived = isCarReceived;
        this.isRefundDone = isRefundDone;
        this.isCanceld = isCanceld;
        this.rentPerDay = rentPerDay;
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

    public String getIsRenterPaymentDone() {
        return isRenterPaymentDone;
    }

    public void setIsRenterPaymentDone(String isRenterPaymentDone) {
        this.isRenterPaymentDone = isRenterPaymentDone;
    }

    public String getIsDriverPaymentDone() {
        return isDriverPaymentDone;
    }

    public void setIsDriverPaymentDone(String isDriverPaymentDone) {
        this.isDriverPaymentDone = isDriverPaymentDone;
    }

    public String getIsDepositPaymentDone() {
        return isDepositPaymentDone;
    }

    public void setIsDepositPaymentDone(String isDepositPaymentDone) {
        this.isDepositPaymentDone = isDepositPaymentDone;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getIsRefund() {
        return isRefund;
    }

    public void setIsRefund(String isRefund) {
        this.isRefund = isRefund;
    }

    public String getIsExtended() {
        return isExtended;
    }

    public void setIsExtended(String isExtended) {
        this.isExtended = isExtended;
    }

    public String getIsUpdated() {
        return isUpdated;
    }

    public void setIsUpdated(String isUpdated) {
        this.isUpdated = isUpdated;
    }

    public String getIsSelfCanceld() {
        return isSelfCanceld;
    }

    public void setIsSelfCanceld(String isSelfCanceld) {
        this.isSelfCanceld = isSelfCanceld;
    }

    public String getIsRenterCanceld() {
        return isRenterCanceld;
    }

    public void setIsRenterCanceld(String isRenterCanceld) {
        this.isRenterCanceld = isRenterCanceld;
    }

    public String getIsRenterAccepted() {
        return isRenterAccepted;
    }

    public void setIsRenterAccepted(String isRenterAccepted) {
        this.isRenterAccepted = isRenterAccepted;
    }

    public String getIsAppliedReturn() {
        return isAppliedReturn;
    }

    public void setIsAppliedReturn(String isAppliedReturn) {
        this.isAppliedReturn = isAppliedReturn;
    }

    public String getIsCarReceived() {
        return isCarReceived;
    }

    public void setIsCarReceived(String isCarReceived) {
        this.isCarReceived = isCarReceived;
    }

    public String getIsRefundDone() {
        return isRefundDone;
    }

    public void setIsRefundDone(String isRefundDone) {
        this.isRefundDone = isRefundDone;
    }

    public String getIsCanceld() {
        return isCanceld;
    }

    public void setIsCanceld(String isCanceld) {
        this.isCanceld = isCanceld;
    }

    public String getRentPerDay() {
        return rentPerDay;
    }

    public void setRentPerDay(String rentPerDay) {
        this.rentPerDay = rentPerDay;
    }
}
