package com.elatesoftware.meetings.api.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 13.06.2017.
 */

public class Result implements Parcelable {

    @SerializedName("Date")
    @Expose
    private Meeting date;

    @SerializedName("CreatorName")
    @Expose
    private String creatorName;

    @SerializedName("CreatorId")
    @Expose
    private Long creatorId;

    @SerializedName("CreatorPhotoId")
    @Expose
    private Long creatorPhotoId;

    @SerializedName("PartnerName")
    @Expose
    private String partnerName;

    @SerializedName("PartnerId")
    @Expose
    private Long partnerId;

    @SerializedName("PartnerPhotoId")
    @Expose
    private Long partnerPhotoId;

    @SerializedName("PartnerCount")
    @Expose
    private Integer partnerCount;

    @SerializedName("Id")
    @Expose
    private Integer id;


    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Long getCreatorPhotoId() {
        return creatorPhotoId;
    }

    public void setCreatorPhotoId(Long creatorPhotoId) {
        this.creatorPhotoId = creatorPhotoId;
    }

    public Meeting getDate() {
        return date;
    }

    public void setDate(Meeting date) {
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public Long getPartnerPhotoId() {
        return partnerPhotoId;
    }

    public void setPartnerPhotoId(Long partnerPhotoId) {
        this.partnerPhotoId = partnerPhotoId;
    }

    public Integer getPartnerCount() {
        return partnerCount;
    }

    public void setPartnerCount(Integer partnerCount) {
        this.partnerCount = partnerCount;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.date, flags);
        dest.writeString(this.creatorName);
        dest.writeValue(this.creatorId);
        dest.writeValue(this.creatorPhotoId);
        dest.writeString(this.partnerName);
        dest.writeValue(this.partnerId);
        dest.writeValue(this.partnerPhotoId);
        dest.writeValue(this.partnerCount);
        dest.writeValue(this.id);
    }

    public Result() {
    }

    protected Result(Parcel in) {
        this.date = in.readParcelable(Meeting.class.getClassLoader());
        this.creatorName = in.readString();
        this.creatorId = (Long) in.readValue(Long.class.getClassLoader());
        this.creatorPhotoId = (Long) in.readValue(Long.class.getClassLoader());
        this.partnerName = in.readString();
        this.partnerId = (Long) in.readValue(Long.class.getClassLoader());
        this.partnerPhotoId = (Long) in.readValue(Long.class.getClassLoader());
        this.partnerCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<Result> CREATOR = new Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel source) {
            return new Result(source);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[size];
        }
    };
}
