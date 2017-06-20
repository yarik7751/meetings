package com.elatesoftware.meetings.model.params;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SelectPartnerParams implements Parcelable {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("AccountId")
    @Expose
    private Integer accountId;
    @SerializedName("DateId")
    @Expose
    private Integer dateId;

    public SelectPartnerParams(Integer id, Integer accountId, Integer dateId) {
        this.id = id;
        this.accountId = accountId;
        this.dateId = dateId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getDateId() {
        return dateId;
    }

    public void setDateId(Integer dateId) {
        this.dateId = dateId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.accountId);
        dest.writeValue(this.dateId);
    }

    protected SelectPartnerParams(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.accountId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.dateId = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<SelectPartnerParams> CREATOR = new Parcelable.Creator<SelectPartnerParams>() {
        @Override
        public SelectPartnerParams createFromParcel(Parcel source) {
            return new SelectPartnerParams(source);
        }

        @Override
        public SelectPartnerParams[] newArray(int size) {
            return new SelectPartnerParams[size];
        }
    };
}
