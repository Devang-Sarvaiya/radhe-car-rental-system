package com.example.caronrentrenter.Domain;

import java.io.Serializable;

public class ItemDomain implements Serializable {

    private String title;
    private String address;
    private String description;
    private String pic;
    private int bed;
    private int bath;
    private int price;



    public ItemDomain(String title, String address, String description, int bed, int bath, int price, String pic) {
        this.title = title;
        this.address = address;
        this.description = description;
        this.pic = pic;
        this.bed = bed;
        this.bath = bath;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pid) {
        this.pic = pid;
    }

    public int getBed() {
        return bed;
    }

    public void setBed(int bed) {
        this.bed = bed;
    }

    public int getBath() {
        return bath;
    }

    public void setBath(int bath) {
        this.bath = bath;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
