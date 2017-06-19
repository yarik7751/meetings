package com.elatesoftware.meetings.util.api.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MessageAnswer implements Parcelable {

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.success);
        dest.writeString(this.message);
    }

    public MessageAnswer() {
    }

    protected MessageAnswer(Parcel in) {
        this.success = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.message = in.readString();
    }

    public static final Parcelable.Creator<MessageAnswer> CREATOR = new Parcelable.Creator<MessageAnswer>() {
        @Override
        public MessageAnswer createFromParcel(Parcel source) {
            return new MessageAnswer(source);
        }

        @Override
        public MessageAnswer[] newArray(int size) {
            return new MessageAnswer[size];
        }
    };
}
