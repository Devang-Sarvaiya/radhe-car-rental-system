package com.example.caronrent;

import java.io.Serializable;

public class ReadWriteUserDetails2 implements Serializable {
    private String name, email, pass, mobile, city, dll, gender, imageURLUser;

    public ReadWriteUserDetails2() {
        // Default constructor
    }

    public ReadWriteUserDetails2(String name, String email, String pass, String mobile, String city, String dll, String gender, String imageURLUser) {
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.mobile = mobile;
        this.city = city;
        this.dll = dll;
        this.gender = gender;
        this.imageURLUser = imageURLUser;
    }

    // Getter and Setter methods
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
}
