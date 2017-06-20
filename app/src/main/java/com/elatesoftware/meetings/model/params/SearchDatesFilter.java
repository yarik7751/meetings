package com.elatesoftware.meetings.model.params;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchDatesFilter extends SessionKey implements Parcelable {

    @SerializedName("amountStart")
    @Expose
    protected Double amountStart;

    @SerializedName("startTime")
    @Expose
    protected Long startTime;

    @SerializedName("page")
    @Expose
    protected Integer page;

    public SearchDatesFilter(Double amountStart, Long startTime, Integer page) {
        this.amountStart = amountStart;
        this.startTime = startTime;
        this.page = page;
    }

    public Double getAmountStart() {
        return amountStart;
    }

    public void setAmountStart(Double amountStart) {
        this.amountStart = amountStart;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.amountStart);
        dest.writeValue(this.startTime);
        dest.writeValue(this.page);
    }

    public SearchDatesFilter() {
    }

    protected SearchDatesFilter(Parcel in) {
        this.amountStart = (Double) in.readValue(Double.class.getClassLoader());
        this.startTime = (Long) in.readValue(Long.class.getClassLoader());
        this.page = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<SearchDatesFilter> CREATOR = new Parcelable.Creator<SearchDatesFilter>() {
        @Override
        public SearchDatesFilter createFromParcel(Parcel source) {
            return new SearchDatesFilter(source);
        }

        @Override
        public SearchDatesFilter[] newArray(int size) {
            return new SearchDatesFilter[size];
        }
    };
}
