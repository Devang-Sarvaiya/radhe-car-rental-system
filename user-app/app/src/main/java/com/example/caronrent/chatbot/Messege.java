// Messege.java
package com.example.caronrent.chatbot;

public class Messege {

    public static String SENT_BY_ME = "me";
    public static String SENT_BY_BOT = "bot";

    private String msg;
    private String sentby;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSentby() {
        return sentby;
    }

    public void setSentby(String sentby) {
        this.sentby = sentby;
    }

    public Messege(String msg, String sentby) {
        this.msg = msg;
        this.sentby = sentby;
    }
}