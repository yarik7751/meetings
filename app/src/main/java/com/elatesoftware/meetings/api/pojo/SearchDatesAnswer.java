package com.elatesoftware.meetings.api.pojo;

import android.os.Parcel;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchDatesAnswer extends MessageAnswer {

    @SerializedName("Result")
    @Expose
    private List<Result> result = null;

    /*public static SearchDatesAnswer answersInstance = null;
    public static SearchDatesAnswer getInstance() {
        return answersInstance;
    }
    public static void setInstance(SearchDatesAnswer answers) {
        answersInstance = answers;
    }*/

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeList(this.result);
    }

    public SearchDatesAnswer() {
    }

    protected SearchDatesAnswer(Parcel in) {
        super(in);
        this.result = new ArrayList<Result>();
        in.readList(this.result, Result.class.getClassLoader());
    }

    public static final Creator<SearchDatesAnswer> CREATOR = new Creator<SearchDatesAnswer>() {
        @Override
        public SearchDatesAnswer createFromParcel(Parcel source) {
            return new SearchDatesAnswer(source);
        }

        @Override
        public SearchDatesAnswer[] newArray(int size) {
            return new SearchDatesAnswer[size];
        }
    };
}
