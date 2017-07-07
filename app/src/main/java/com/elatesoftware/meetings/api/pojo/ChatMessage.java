
package com.elatesoftware.meetings.api.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatMessage {

    @SerializedName("DateId")
    @Expose
    private Integer dateId;
    @SerializedName("Text")
    @Expose
    private String text;
    @SerializedName("DateTime")
    @Expose
    private Long dateTime;
    @SerializedName("SenderId")
    @Expose
    private Integer senderId;
    @SerializedName("RecieverId")
    @Expose
    private Integer recieverId;

    public ChatMessage(Integer dateId, String text, Long dateTime, Integer senderId, Integer recieverId) {
        this.dateId = dateId;
        this.text = text;
        this.dateTime = dateTime;
        this.senderId = senderId;
        this.recieverId = recieverId;
    }

    public Integer getDateId() {
        return dateId;
    }

    public void setDateId(Integer dateId) {
        this.dateId = dateId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getDateTime() {
        return dateTime;
    }

    public void setDateTime(Long dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public Integer getRecieverId() {
        return recieverId;
    }

    public void setRecieverId(Integer recieverId) {
        this.recieverId = recieverId;
    }

}
