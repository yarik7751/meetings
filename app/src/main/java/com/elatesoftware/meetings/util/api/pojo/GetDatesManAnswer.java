
package com.elatesoftware.meetings.util.api.pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetDatesManAnswer extends MessageAnswer {

    @SerializedName("Result")
    @Expose
    private List<Result> result = null;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public static GetDatesManAnswer answersInstance = null;
    public static GetDatesManAnswer getInstance() {
        return answersInstance;
    }
    public static void setInstance(GetDatesManAnswer answers) {
        answersInstance = answers;
    }

    /*public static class Result {

        @SerializedName("Date")
        @Expose
        private Meeting date;
        @SerializedName("CreatorName")
        @Expose
        private String creatorName;
        @SerializedName("CreatorId")
        @Expose
        private Integer creatorId;
        @SerializedName("CreatorPhotoId")
        @Expose
        private Integer creatorPhotoId;
        @SerializedName("PartnerName")
        @Expose
        private String partnerName;
        @SerializedName("PartnerId")
        @Expose
        private Integer partnerId;
        @SerializedName("PartnerPhotoId")
        @Expose
        private Integer partnerPhotoId;
        @SerializedName("Id")
        @Expose
        private Integer id;

        public Integer getCreatorId() {
            return creatorId;
        }

        public void setCreatorId(Integer creatorId) {
            this.creatorId = creatorId;
        }

        public String getCreatorName() {
            return creatorName;
        }

        public void setCreatorName(String creatorName) {
            this.creatorName = creatorName;
        }

        public Integer getCreatorPhotoId() {
            return creatorPhotoId;
        }

        public void setCreatorPhotoId(Integer creatorPhotoId) {
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

        public Integer getPartnerId() {
            return partnerId;
        }

        public void setPartnerId(Integer partnerId) {
            this.partnerId = partnerId;
        }

        public String getPartnerName() {
            return partnerName;
        }

        public void setPartnerName(String partnerName) {
            this.partnerName = partnerName;
        }

        public Integer getPartnerPhotoId() {
            return partnerPhotoId;
        }

        public void setPartnerPhotoId(Integer partnerPhotoId) {
            this.partnerPhotoId = partnerPhotoId;
        }
    }*/
}
