package com.example.caronrentrenter.Multiple_Image;




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


//public class ImageModel {
//    private String imageUrl;
//    private int productKey; // New field to store the product key
//
//    public ImageModel() {
//        // Default constructor required for Firebase
//    }
//
//    public ImageModel(String imageUrl, int productKey) {
//        this.imageUrl = imageUrl;
//        this.productKey = productKey;
//    }
//
//    public String getImageUrl() {
//        return imageUrl;
//    }
//
//    public int getProductKey() {
//        return productKey;
//    }
//}



















//package com.example.caronrentrenter.Multiple_Image;
//
//public class ImageModel {
//    private String imageUrl;
//
//    public ImageModel() {
//        // Default constructor required for Firebase
//    }
//
//    public ImageModel(String imageUrl) {
//        this.imageUrl = imageUrl;
//    }
//
//    public String getImageUrl() {
//        return imageUrl;
//    }
//}