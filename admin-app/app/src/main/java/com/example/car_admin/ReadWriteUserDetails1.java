package com.example.car_admin;

import java.io.Serializable;

public class ReadWriteUserDetails1 implements Serializable {
    private String name, email, pass, mobile, city, dll, gender, imageURLUser, isVerified, dlPic, aadhar_front, aadhaar_back, admin_access_state;


    public ReadWriteUserDetails1() {
    }

    public ReadWriteUserDetails1(String name, String email, String pass, String mobile, String city, String dll, String gender, String imageURLUser, String isVerified, String dlPic, String aadhar_front, String aadhaar_back, String admin_access_state) {
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.mobile = mobile;
        this.city = city;
        this.dll = dll;
        this.gender = gender;
        this.imageURLUser = imageURLUser;
        this.isVerified = isVerified;
        this.dlPic = dlPic;
        this.aadhar_front = aadhar_front;
        this.aadhaar_back = aadhaar_back;
        this.admin_access_state = admin_access_state;
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

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDll() {
        return dll;
    }

    public void setDll(String dll) {
        this.dll = dll;
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

    public String getDlPic() {
        return dlPic;
    }

    public void setDlPic(String dlPic) {
        this.dlPic = dlPic;
    }

    public String getAadhar_front() {
        return aadhar_front;
    }

    public void setAadhar_front(String aadhar_front) {
        this.aadhar_front = aadhar_front;
    }

    public String getAadhaar_back() {
        return aadhaar_back;
    }

    public void setAadhaar_back(String aadhaar_back) {
        this.aadhaar_back = aadhaar_back;
    }

    public String getAdmin_access_state() {
        return admin_access_state;
    }

    public void setAdmin_access_state(String admin_access_state) {
        this.admin_access_state = admin_access_state;
    }
}