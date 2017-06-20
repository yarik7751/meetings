package com.elatesoftware.meetings.api.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 13.06.2017.
 */

public class Result {

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
}
