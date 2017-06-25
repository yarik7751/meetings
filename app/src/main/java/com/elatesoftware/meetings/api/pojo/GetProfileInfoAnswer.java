package com.elatesoftware.meetings.api.pojo;

import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetProfileInfoAnswer extends MessageAnswer {

    @SerializedName("Result")
    @Expose
    protected HumanAnswer result;

    /*public static GetProfileInfoAnswer answersInstance = null;
    public static GetProfileInfoAnswer getInstance() {
        return answersInstance;
    }
    public static void setInstance(GetProfileInfoAnswer answers) {
        answersInstance = answers;
    }*/

    public HumanAnswer getResult() {
        return result;
    }

    public void setResult(HumanAnswer result) {
        this.result = result;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(this.result, flags);
    }

    public GetProfileInfoAnswer() {
    }

    protected GetProfileInfoAnswer(Parcel in) {
        super(in);
        this.result = in.readParcelable(HumanAnswer.class.getClassLoader());
    }

    public static final Creator<GetProfileInfoAnswer> CREATOR = new Creator<GetProfileInfoAnswer>() {
        @Override
        public GetProfileInfoAnswer createFromParcel(Parcel source) {
            return new GetProfileInfoAnswer(source);
        }

        @Override
        public GetProfileInfoAnswer[] newArray(int size) {
            return new GetProfileInfoAnswer[size];
        }
    };
}
