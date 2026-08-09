package com.example.caronrent.E_commerce;

import java.io.Serializable;
import java.util.List;

public class ImageModel_1_e_com implements Serializable {
    private String modelName;
    private String modelDescription;
    private String imageURL;
    private String gadgetCompany;
    private String gadgetPrice;
    private String deliveryDay;
    private String replacementDay;
    private String gadgetCategory;

    private String renterMobile;
    private int productCounter;
    private List<String> imageUrls; // Add this line


    // Add any additional fields as needed


    public ImageModel_1_e_com() {
    }

    public ImageModel_1_e_com(String modelName, String modelDescription, String imageURL, String gadgetCompany, String gadgetPrice, String deliveryDay, String replacementDay, String gadgetCategory, String renterMobile, int productCounter, List<String> imageUrls) {
        this.modelName = modelName;
        this.modelDescription = modelDescription;
        this.imageURL = imageURL;
        this.gadgetCompany = gadgetCompany;
        this.gadgetPrice = gadgetPrice;
        this.deliveryDay = deliveryDay;
        this.replacementDay = replacementDay;
        this.gadgetCategory = gadgetCategory;
        this.renterMobile = renterMobile;
        this.productCounter = productCounter;
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

    public String getGadgetCompany() {
        return gadgetCompany;
    }

    public void setGadgetCompany(String gadgetCompany) {
        this.gadgetCompany = gadgetCompany;
    }

    public String getGadgetPrice() {
        return gadgetPrice;
    }

    public void setGadgetPrice(String gadgetPrice) {
        this.gadgetPrice = gadgetPrice;
    }

    public String getDeliveryDay() {
        return deliveryDay;
    }

    public void setDeliveryDay(String deliveryDay) {
        this.deliveryDay = deliveryDay;
    }

    public String getReplacementDay() {
        return replacementDay;
    }

    public void setReplacementDay(String replacementDay) {
        this.replacementDay = replacementDay;
    }

    public String getGadgetCategory() {
        return gadgetCategory;
    }

    public void setGadgetCategory(String gadgetCategory) {
        this.gadgetCategory = gadgetCategory;
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

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
}
