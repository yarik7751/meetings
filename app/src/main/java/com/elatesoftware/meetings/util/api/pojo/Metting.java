
package com.elatesoftware.meetings.util.api.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Metting {

    @SerializedName("CreatorId")
    @Expose
    private Integer creatorId;

    @SerializedName("Status")
    @Expose
    private Integer status;

    @SerializedName("StartTime")
    @Expose
    private Long startTime;

    @SerializedName("EndTime")
    @Expose
    private Long endTime;

    @SerializedName("Place")
    @Expose
    private String place;

    @SerializedName("Lat")
    @Expose
    private Double lat;

    @SerializedName("Long")
    @Expose
    private Double _long;

    @SerializedName("Amount")
    @Expose
    private Double amount;

    @SerializedName("PrefAgeStart")
    @Expose
    private Double prefAgeStart;

    @SerializedName("PrefAgeEnd")
    @Expose
    private Double prefAgeEnd;

    @SerializedName("PrefHeightStart")
    @Expose
    private Double prefHeightStart;

    @SerializedName("PrefHeightEnd")
    @Expose
    private Double prefHeightEnd;

    @SerializedName("PrefWeightStart")
    @Expose
    private Double prefWeightStart;

    @SerializedName("PrefWeightEnd")
    @Expose
    private Double prefWeightEnd;

    @SerializedName("HairColor")
    @Expose
    private Integer hairColor;

    @SerializedName("WithPhoto")
    @Expose
    private Boolean withPhoto;

    @SerializedName("Id")
    @Expose
    private Integer id;

    public Double get_long() {
        return _long;
    }

    public void set_long(Double _long) {
        this._long = _long;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Integer getHairColor() {
        return hairColor;
    }

    public void setHairColor(Integer hairColor) {
        this.hairColor = hairColor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
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



    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getWithPhoto() {
        return withPhoto;
    }

    public void setWithPhoto(Boolean withPhoto) {
        this.withPhoto = withPhoto;
    }
}
