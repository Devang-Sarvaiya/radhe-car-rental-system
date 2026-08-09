package com.example.caronrent.E_commerce;

public class order_Gadget {
    String gadgetImage;
    String sellerRenterMobile;
    String buyerRenterMobile;
    String gadgetName;
    String gadgetCompany;
    String gadgetCategory;
    String homeNumber;
    String socName;
    String landmark;
    String pincode;
    String deliveryDay;
    String replaceDay;
    String totalAmount;

    public order_Gadget() {
    }


    public order_Gadget(String gadgetImage, String sellerRenterMobile, String buyerRenterMobile, String gadgetName, String gadgetCompany, String gadgetCategory, String homeNumber, String socName, String landmark, String pincode, String deliveryDay, String replaceDay, String totalAmount) {
        this.gadgetImage = gadgetImage;
        this.sellerRenterMobile = sellerRenterMobile;
        this.buyerRenterMobile = buyerRenterMobile;
        this.gadgetName = gadgetName;
        this.gadgetCompany = gadgetCompany;
        this.gadgetCategory = gadgetCategory;
        this.homeNumber = homeNumber;
        this.socName = socName;
        this.landmark = landmark;
        this.pincode = pincode;
        this.deliveryDay = deliveryDay;
        this.replaceDay = replaceDay;
        this.totalAmount = totalAmount;
    }

    public String getGadgetImage() {
        return gadgetImage;
    }

    public void setGadgetImage(String gadgetImage) {
        this.gadgetImage = gadgetImage;
    }

    public String getSellerRenterMobile() {
        return sellerRenterMobile;
    }

    public void setSellerRenterMobile(String sellerRenterMobile) {
        this.sellerRenterMobile = sellerRenterMobile;
    }

    public String getBuyerRenterMobile() {
        return buyerRenterMobile;
    }

    public void setBuyerRenterMobile(String buyerRenterMobile) {
        this.buyerRenterMobile = buyerRenterMobile;
    }

    public String getGadgetName() {
        return gadgetName;
    }

    public void setGadgetName(String gadgetName) {
        this.gadgetName = gadgetName;
    }

    public String getGadgetCompany() {
        return gadgetCompany;
    }

    public void setGadgetCompany(String gadgetCompany) {
        this.gadgetCompany = gadgetCompany;
    }

    public String getGadgetCategory() {
        return gadgetCategory;
    }

    public void setGadgetCategory(String gadgetCategory) {
        this.gadgetCategory = gadgetCategory;
    }

    public String getHomeNumber() {
        return homeNumber;
    }

    public void setHomeNumber(String homeNumber) {
        this.homeNumber = homeNumber;
    }

    public String getSocName() {
        return socName;
    }

    public void setSocName(String socName) {
        this.socName = socName;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getDeliveryDay() {
        return deliveryDay;
    }

    public void setDeliveryDay(String deliveryDay) {
        this.deliveryDay = deliveryDay;
    }

    public String getReplaceDay() {
        return replaceDay;
    }

    public void setReplaceDay(String replaceDay) {
        this.replaceDay = replaceDay;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}
