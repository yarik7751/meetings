package com.elatesoftware.meetings.api.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meeting implements Parcelable {

    @SerializedName("Id")
    @Expose
    protected Long id;

    @SerializedName("StartTime")
    @Expose
    protected Long startTime;

    @SerializedName("Status")
    @Expose
    private Integer status;

    @SerializedName("EndTime")
    @Expose
    protected Long endTime;

    @SerializedName("Place")
    @Expose
    protected String place;

    @SerializedName("About")
    @Expose
    protected String about;

    @SerializedName("Lat")
    @Expose
    protected Double latitude;

    @SerializedName("Long")
    @Expose
    protected Double longitude;

    @SerializedName("Amount")
    @Expose
    protected Integer amount;

    @SerializedName("PrefAgeStart")
    @Expose
    protected Integer prefAgeStart;

    @SerializedName("PrefAgeEnd")
    @Expose
    protected Integer prefAgeEnd;

    @SerializedName("PrefHeightStart")
    @Expose
    protected Integer prefHeightStart;

    @SerializedName("PrefHeightEnd")
    @Expose
    protected Integer prefHeightEnd;

    @SerializedName("PrefWeightStart")
    @Expose
    protected Integer prefWeightStart;

    @SerializedName("PrefWeightEnd")
    @Expose
    protected Integer prefWeightEnd;

    @SerializedName("HairColor")
    @Expose
    protected Integer hairColor;

    @SerializedName("WithPhoto")
    @Expose
    protected Boolean withPhoto;

    public static Meeting answersInstance = null;
    public static Meeting getInstance() {
        return answersInstance;
    }
    public static void setInstance(Meeting answers) {
        answersInstance = answers;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Integer getHairColor() {
        return hairColor;
    }

    public void setHairColor(Integer hairColor) {
        this.hairColor = hairColor;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Integer getPrefAgeEnd() {
        return prefAgeEnd;
    }

    public void setPrefAgeEnd(Integer prefAgeEnd) {
        this.prefAgeEnd = prefAgeEnd;
    }

    public Integer getPrefAgeStart() {
        return prefAgeStart;
    }

    public void setPrefAgeStart(Integer prefAgeStart) {
        this.prefAgeStart = prefAgeStart;
    }

    public Integer getPrefHeightEnd() {
        return prefHeightEnd;
    }

    public void setPrefHeightEnd(Integer prefHeightEnd) {
        this.prefHeightEnd = prefHeightEnd;
    }

    public Integer getPrefHeightStart() {
        return prefHeightStart;
    }

    public void setPrefHeightStart(Integer prefHeightStart) {
        this.prefHeightStart = prefHeightStart;
    }

    public Integer getPrefWeightEnd() {
        return prefWeightEnd;
    }

    public void setPrefWeightEnd(Integer prefWeightEnd) {
        this.prefWeightEnd = prefWeightEnd;
    }

    public Integer getPrefWeightStart() {
        return prefWeightStart;
    }

    public void setPrefWeightStart(Integer prefWeightStart) {
        this.prefWeightStart = prefWeightStart;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Boolean getWithPhoto() {
        return withPhoto;
    }

    public void setWithPhoto(Boolean withPhoto) {
        this.withPhoto = withPhoto;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.startTime);
        dest.writeValue(this.status);
        dest.writeValue(this.endTime);
        dest.writeString(this.place);
        dest.writeString(this.about);
        dest.writeValue(this.latitude);
        dest.writeValue(this.longitude);
        dest.writeValue(this.amount);
        dest.writeValue(this.prefAgeStart);
        dest.writeValue(this.prefAgeEnd);
        dest.writeValue(this.prefHeightStart);
        dest.writeValue(this.prefHeightEnd);
        dest.writeValue(this.prefWeightStart);
        dest.writeValue(this.prefWeightEnd);
        dest.writeValue(this.hairColor);
        dest.writeValue(this.withPhoto);
    }

    public Meeting() {
    }

    protected Meeting(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.startTime = (Long) in.readValue(Long.class.getClassLoader());
        this.status = (Integer) in.readValue(Integer.class.getClassLoader());
        this.endTime = (Long) in.readValue(Long.class.getClassLoader());
        this.place = in.readString();
        this.about = in.readString();
        this.latitude = (Double) in.readValue(Double.class.getClassLoader());
        this.longitude = (Double) in.readValue(Double.class.getClassLoader());
        this.amount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.prefAgeStart = (Integer) in.readValue(Integer.class.getClassLoader());
        this.prefAgeEnd = (Integer) in.readValue(Integer.class.getClassLoader());
        this.prefHeightStart = (Integer) in.readValue(Integer.class.getClassLoader());
        this.prefHeightEnd = (Integer) in.readValue(Integer.class.getClassLoader());
        this.prefWeightStart = (Integer) in.readValue(Integer.class.getClassLoader());
        this.prefWeightEnd = (Integer) in.readValue(Integer.class.getClassLoader());
        this.hairColor = (Integer) in.readValue(Integer.class.getClassLoader());
        this.withPhoto = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public static final Parcelable.Creator<Meeting> CREATOR = new Parcelable.Creator<Meeting>() {
        @Override
        public Meeting createFromParcel(Parcel source) {
            return new Meeting(source);
        }

        @Override
        public Meeting[] newArray(int size) {
            return new Meeting[size];
        }
    };
}
