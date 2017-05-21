package com.elatesoftware.meetings.util.api.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meeting {

    @SerializedName("StartTime")
    @Expose
    protected Long startTime;

    @SerializedName("EndTime")
    @Expose
    protected Long endTime;

    @SerializedName("Place")
    @Expose
    protected String place;

    @SerializedName("Lat")
    @Expose
    protected Double latitude;

    @SerializedName("Long")
    @Expose
    protected Double longitude;

    @SerializedName("Amount")
    @Expose
    protected Double amount;

    @SerializedName("PrefAgeStart")
    @Expose
    protected Double prefAgeStart;

    @SerializedName("PrefAgeEnd")
    @Expose
    protected Double prefAgeEnd;

    @SerializedName("PrefHeightStart")
    @Expose
    protected Double prefHeightStart;

    @SerializedName("PrefHeightEnd")
    @Expose
    protected Double prefHeightEnd;

    @SerializedName("PrefWeightStart")
    @Expose
    protected Double prefWeightStart;

    @SerializedName("PrefWeightEnd")
    @Expose
    protected Double prefWeightEnd;

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

    public Double getPrefAgeEnd() {
        return prefAgeEnd;
    }

    public void setPrefAgeEnd(Double prefAgeEnd) {
        this.prefAgeEnd = prefAgeEnd;
    }

    public Double getPrefAgeStart() {
        return prefAgeStart;
    }

    public void setPrefAgeStart(Double prefAgeStart) {
        this.prefAgeStart = prefAgeStart;
    }

    public Double getPrefHeightEnd() {
        return prefHeightEnd;
    }

    public void setPrefHeightEnd(Double prefHeightEnd) {
        this.prefHeightEnd = prefHeightEnd;
    }

    public Double getPrefHeightStart() {
        return prefHeightStart;
    }

    public void setPrefHeightStart(Double prefHeightStart) {
        this.prefHeightStart = prefHeightStart;
    }

    public Double getPrefWeightEnd() {
        return prefWeightEnd;
    }

    public void setPrefWeightEnd(Double prefWeightEnd) {
        this.prefWeightEnd = prefWeightEnd;
    }

    public Double getPrefWeightStart() {
        return prefWeightStart;
    }

    public void setPrefWeightStart(Double prefWeightStart) {
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
