
package com.elatesoftware.meetings.model.params;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthorizationParams implements Parcelable {

    @SerializedName("Username")
    @Expose
    private String username;
    @SerializedName("Password")
    @Expose
    private String password;
    @SerializedName("Token")
    @Expose
    private String token;
    @SerializedName("Language")
    @Expose
    private String language;
    @SerializedName("Gender")
    @Expose
    private Integer gender;

    public AuthorizationParams(String username, String password, String token, String language, Integer gender) {
        this.username = username;
        this.password = password;
        this.token = token;
        this.language = language;
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.username);
        dest.writeString(this.password);
        dest.writeString(this.token);
        dest.writeString(this.language);
        dest.writeValue(this.gender);
    }

    public AuthorizationParams() {
    }

    protected AuthorizationParams(Parcel in) {
        this.username = in.readString();
        this.password = in.readString();
        this.token = in.readString();
        this.language = in.readString();
        this.gender = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<AuthorizationParams> CREATOR = new Parcelable.Creator<AuthorizationParams>() {
        @Override
        public AuthorizationParams createFromParcel(Parcel source) {
            return new AuthorizationParams(source);
        }

        @Override
        public AuthorizationParams[] newArray(int size) {
            return new AuthorizationParams[size];
        }
    };
}
