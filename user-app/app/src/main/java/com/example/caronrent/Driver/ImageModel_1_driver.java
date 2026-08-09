package com.example.caronrent.Driver;

import java.io.Serializable;
import java.util.List;

public class ImageModel_1_driver implements Serializable {
    public String name, email, mobile_dr, renter_mobile,aadhaar_card_number, gender, imageURLUser, isVerified, adharFrontImageUrl, adharBackImageUrl, insurance,dl,isCoonectedWithCar,isUnAvailable, driver_id,driver_pass,admin_access_state,carRenterMobile,carName;

//    Name, Email, Pass,mobile_dr,City, Dll,Gender,uri.toString()
//    public ReadWriteUserDetails(String txtName,String txtEmail,String txtPass, String txtmobile_dr, String City,  String txtDll,String txtGender,String imageURLUser) {
//
//    }


    public ImageModel_1_driver() {
    }

    public ImageModel_1_driver(String name, String email, String mobile_dr, String renter_mobile, String aadhaar_card_number, String gender, String imageURLUser, String isVerified, String adharFrontImageUrl, String adharBackImageUrl, String insurance, String dl, String isCoonectedWithCar, String isUnAvailable, String driver_id, String driver_pass, String admin_access_state, String carRenterMobile, String carName) {
        this.name = name;
        this.email = email;
        this.mobile_dr = mobile_dr;
        this.renter_mobile = renter_mobile;
        this.aadhaar_card_number = aadhaar_card_number;
        this.gender = gender;
        this.imageURLUser = imageURLUser;
        this.isVerified = isVerified;
        this.adharFrontImageUrl = adharFrontImageUrl;
        this.adharBackImageUrl = adharBackImageUrl;
        this.insurance = insurance;
        this.dl = dl;
        this.isCoonectedWithCar = isCoonectedWithCar;
        this.isUnAvailable = isUnAvailable;
        this.driver_id = driver_id;
        this.driver_pass = driver_pass;
        this.admin_access_state = admin_access_state;
        this.carRenterMobile = carRenterMobile;
        this.carName = carName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile_dr() {
        return mobile_dr;
    }

    public void setMobile_dr(String mobile_dr) {
        this.mobile_dr = mobile_dr;
    }

    public String getRenter_mobile() {
        return renter_mobile;
    }

    public void setRenter_mobile(String renter_mobile) {
        this.renter_mobile = renter_mobile;
    }

    public String getAadhaar_card_number() {
        return aadhaar_card_number;
    }

    public void setAadhaar_card_number(String aadhaar_card_number) {
        this.aadhaar_card_number = aadhaar_card_number;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImageURLUser() {
        return imageURLUser;
    }

    public void setImageURLUser(String imageURLUser) {
        this.imageURLUser = imageURLUser;
    }

    public String getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(String isVerified) {
        this.isVerified = isVerified;
    }

    public String getAdharFrontImageUrl() {
        return adharFrontImageUrl;
    }

    public void setAdharFrontImageUrl(String adharFrontImageUrl) {
        this.adharFrontImageUrl = adharFrontImageUrl;
    }

    public String getAdharBackImageUrl() {
        return adharBackImageUrl;
    }

    public void setAdharBackImageUrl(String adharBackImageUrl) {
        this.adharBackImageUrl = adharBackImageUrl;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public String getDl() {
        return dl;
    }

    public void setDl(String dl) {
        this.dl = dl;
    }

    public String getIsCoonectedWithCar() {
        return isCoonectedWithCar;
    }

    public void setIsCoonectedWithCar(String isCoonectedWithCar) {
        this.isCoonectedWithCar = isCoonectedWithCar;
    }

    public String getIsUnAvailable() {
        return isUnAvailable;
    }

    public void setIsUnAvailable(String isUnAvailable) {
        this.isUnAvailable = isUnAvailable;
    }

    public String getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(String driver_id) {
        this.driver_id = driver_id;
    }

    public String getDriver_pass() {
        return driver_pass;
    }

    public void setDriver_pass(String driver_pass) {
        this.driver_pass = driver_pass;
    }

    public String getAdmin_access_state() {
        return admin_access_state;
    }

    public void setAdmin_access_state(String admin_access_state) {
        this.admin_access_state = admin_access_state;
    }

    public String getCarRenterMobile() {
        return carRenterMobile;
    }

    public void setCarRenterMobile(String carRenterMobile) {
        this.carRenterMobile = carRenterMobile;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }
}
