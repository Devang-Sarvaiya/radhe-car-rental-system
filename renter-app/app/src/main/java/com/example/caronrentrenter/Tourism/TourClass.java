package com.example.caronrentrenter.Tourism;

import java.io.Serializable;

public class TourClass implements Serializable {

    private String placelName;
    private String placeDescription;
    private String placeLink;
    private String placeImageUrl;


    public TourClass() {
    }

    public TourClass(String placelName, String placeDescription, String placeLink, String placeImageUrl) {
        this.placelName = placelName;
        this.placeDescription = placeDescription;
        this.placeLink = placeLink;
        this.placeImageUrl = placeImageUrl;
    }


    public String getPlacelName() {
        return placelName;
    }

    public void setPlacelName(String placelName) {
        this.placelName = placelName;
    }

    public String getPlaceDescription() {
        return placeDescription;
    }

    public void setPlaceDescription(String placeDescription) {
        this.placeDescription = placeDescription;
    }

    public String getPlaceLink() {
        return placeLink;
    }

    public void setPlaceLink(String placeLink) {
        this.placeLink = placeLink;
    }

    public String getPlaceImageUrl() {
        return placeImageUrl;
    }

    public void setPlaceImageUrl(String placeImageUrl) {
        this.placeImageUrl = placeImageUrl;
    }
}
