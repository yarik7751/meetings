package com.elatesoftware.meetings.util.model;

public class Message {

    public String text;
    public long date;
    public int senderId;

    public Message() {
    }

    public Message(String text, long date, int senderId) {
        this.text = text;
        this.date = date;
        this.senderId = senderId;
    }
}
