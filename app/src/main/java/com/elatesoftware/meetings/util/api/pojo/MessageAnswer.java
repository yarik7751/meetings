package com.elatesoftware.meetings.util.api.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MessageAnswer {

    @SerializedName("Success")
    @Expose
    protected Boolean success;

    @SerializedName("Message")
    @Expose
    protected String message;

    public String getMessage() {
        return message;
    }

    public static MessageAnswer answersInstance = null;
    public static MessageAnswer getInstance() {
        return answersInstance;
    }
    public static void setInstance(MessageAnswer answers) {
        answersInstance = answers;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "success: " + success + ", message: " + message;
    }
}
