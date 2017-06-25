package com.elatesoftware.meetings.api.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Photo implements Parcelable {

    @SerializedName("AccountId")
    @Expose
    protected Integer accountId;

    @SerializedName("Name")
    @Expose
    protected String name;

    @SerializedName("Position")
    @Expose
    protected Integer position;

    @SerializedName("Content")
    @Expose
    protected String content;

    @SerializedName("IsMain")
    @Expose
    protected Boolean isMain;

    @SerializedName("Id")
    @Expose
    protected Integer id;

    public static Photo answersInstance = null;
    public static Photo getInstance() {
        return answersInstance;
    }
    public static void setInstance(Photo answers) {
        answersInstance = answers;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getMain() {
        return isMain;
    }

    public void setMain(Boolean main) {
        isMain = main;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.accountId);
        dest.writeString(this.name);
        dest.writeValue(this.position);
        dest.writeString(this.content);
        dest.writeValue(this.isMain);
        dest.writeValue(this.id);
    }

    public Photo() {
    }

    protected Photo(Parcel in) {
        this.accountId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.position = (Integer) in.readValue(Integer.class.getClassLoader());
        this.content = in.readString();
        this.isMain = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<Photo> CREATOR = new Parcelable.Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel source) {
            return new Photo(source);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };
}
