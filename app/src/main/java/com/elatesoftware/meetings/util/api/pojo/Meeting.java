package com.elatesoftware.meetings.util.api.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meeting {

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
}
