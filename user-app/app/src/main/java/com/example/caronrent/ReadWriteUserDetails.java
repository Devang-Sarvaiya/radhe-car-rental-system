package com.example.caronrent;

public class ReadWriteUserDetails {
    public String name, email, pass, mobile, city, aadhaar_card_number, aadhaar_front, aadhaar_back, gender, imageURLUser, isVerified,isUnAvailable;

//    Name, Email, Pass,Mobile,City, Dll,Gender,uri.toString()
//    public ReadWriteUserDetails(String txtName,String txtEmail,String txtPass, String txtMobile, String City,  String txtDll,String txtGender,String imageURLUser) {
//
//    }


    public ReadWriteUserDetails() {
    }

    public ReadWriteUserDetails(String name, String email, String pass, String mobile, String city, String aadhaar_card_number, String aadhaar_front, String aadhaar_back, String gender, String imageURLUser, String isVerified, String isUnAvailable) {
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.mobile = mobile;
        this.city = city;
        this.aadhaar_card_number = aadhaar_card_number;
        this.aadhaar_front = aadhaar_front;
        this.aadhaar_back = aadhaar_back;
        this.gender = gender;
        this.imageURLUser = imageURLUser;
        this.isVerified = isVerified;
        this.isUnAvailable = isUnAvailable;
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

    public String getAadhaar_card_number() {
        return aadhaar_card_number;
    }

    public void setAadhaar_card_number(String aadhaar_card_number) {
        this.aadhaar_card_number = aadhaar_card_number;
    }

    public String getAadhaar_front() {
        return aadhaar_front;
    }

    public void setAadhaar_front(String aadhaar_front) {
        this.aadhaar_front = aadhaar_front;
    }

    public String getAadhaar_back() {
        return aadhaar_back;
    }

    public void setAadhaar_back(String aadhaar_back) {
        this.aadhaar_back = aadhaar_back;
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

    public String getIsUnAvailable() {
        return isUnAvailable;
    }

    public void setIsUnAvailable(String isUnAvailable) {
        this.isUnAvailable = isUnAvailable;
    }
}

