
package com.elatesoftware.meetings.api.pojo;

import android.os.Parcel;

import java.util.ArrayList;
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

    /*public static GetDatesManAnswer answersInstance = null;
    public static GetDatesManAnswer getInstance() {
        return answersInstance;
    }
    public static void setInstance(GetDatesManAnswer answers) {
        answersInstance = answers;
    }*/


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeList(this.result);
    }

    public GetDatesManAnswer() {
    }

    protected GetDatesManAnswer(Parcel in) {
        super(in);
        this.result = new ArrayList<Result>();
        in.readList(this.result, Result.class.getClassLoader());
    }

    public static final Creator<GetDatesManAnswer> CREATOR = new Creator<GetDatesManAnswer>() {
        @Override
        public GetDatesManAnswer createFromParcel(Parcel source) {
            return new GetDatesManAnswer(source);
        }

        @Override
        public GetDatesManAnswer[] newArray(int size) {
            return new GetDatesManAnswer[size];
        }
    };
}
