package com.elatesoftware.meetings.api.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPendingWomenAnswer extends MessageAnswer implements Parcelable {

    @SerializedName("Result")
    @Expose
    private List<PendingWomanInfo> result = null;

    public List<PendingWomanInfo> getResult() {
        return result;
    }

    public void setResult(List<PendingWomanInfo> result) {
        this.result = result;
    }

    public static class PendingWomanInfo implements Parcelable {

        @SerializedName("AccountId")
        @Expose
        private Integer accountId;
        @SerializedName("DateId")
        @Expose
        private Integer dateId;
        @SerializedName("IsSelected")
        @Expose
        private Boolean isSelected;
        @SerializedName("PhotoId")
        @Expose
        private Integer photoId;
        @SerializedName("Account")
        @Expose
        private HumanAnswer account;
        @SerializedName("Id")
        @Expose
        private Integer id;

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

        public Boolean getSelected() {
            return isSelected;
        }

        public void setSelected(Boolean selected) {
            isSelected = selected;
        }

        public Integer getPhotoId() {
            return photoId;
        }

        public void setPhotoId(Integer photoId) {
            this.photoId = photoId;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public HumanAnswer getAccount() {
            return account;
        }

        public void setAccount(HumanAnswer account) {
            this.account = account;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(this.accountId);
            dest.writeValue(this.dateId);
            dest.writeValue(this.isSelected);
            dest.writeValue(this.photoId);
            dest.writeParcelable(this.account, flags);
            dest.writeValue(this.id);
        }

        public PendingWomanInfo() {
        }

        protected PendingWomanInfo(Parcel in) {
            this.accountId = (Integer) in.readValue(Integer.class.getClassLoader());
            this.dateId = (Integer) in.readValue(Integer.class.getClassLoader());
            this.isSelected = (Boolean) in.readValue(Boolean.class.getClassLoader());
            this.photoId = (Integer) in.readValue(Integer.class.getClassLoader());
            this.account = in.readParcelable(HumanAnswer.class.getClassLoader());
            this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        }

        public static final Creator<PendingWomanInfo> CREATOR = new Creator<PendingWomanInfo>() {
            @Override
            public PendingWomanInfo createFromParcel(Parcel source) {
                return new PendingWomanInfo(source);
            }

            @Override
            public PendingWomanInfo[] newArray(int size) {
                return new PendingWomanInfo[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(this.result);
    }

    public GetPendingWomenAnswer() {
    }

    protected GetPendingWomenAnswer(Parcel in) {
        super(in);
        this.result = in.createTypedArrayList(PendingWomanInfo.CREATOR);
    }

    public static final Creator<GetPendingWomenAnswer> CREATOR = new Creator<GetPendingWomenAnswer>() {
        @Override
        public GetPendingWomenAnswer createFromParcel(Parcel source) {
            return new GetPendingWomenAnswer(source);
        }

        @Override
        public GetPendingWomenAnswer[] newArray(int size) {
            return new GetPendingWomenAnswer[size];
        }
    };
}