package com.example.car_admin.Cars;

public class ImageModel {
    private String imageUrl;
    private int productKey;
    private String price;
    private int quantity;

    public ImageModel(String imageUrl, int productKey) {
        this.imageUrl = imageUrl;
        this.productKey = productKey;
        this.price = price;
        this.quantity = quantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getProductKey() {
        return productKey;
    }

    public String getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }


}