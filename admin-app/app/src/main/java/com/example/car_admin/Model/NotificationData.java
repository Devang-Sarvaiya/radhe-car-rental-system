package com.example.car_admin.Model;

public class NotificationData {
    private  String title;
    private String Message;

    public NotificationData(String title, String message) {
        this.title = title;
        Message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
