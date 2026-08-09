package com.example.caronrentrenter;

import java.io.Serializable;

public class DataClass1 implements Serializable {
    private String CarType;

    public DataClass1() {
    }

    public DataClass1(String carType) {
        CarType = carType;
    }

    public String getCarType() {
        return CarType;
    }

    public void setCarType(String carType) {
        CarType = carType;
    }
}
