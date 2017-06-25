package com.elatesoftware.meetings.api.pojo;

import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetInfoAccAnswer extends MessageAnswer {

    @SerializedName("Result")
    @Expose
    protected HumanAnswer humanAnswer;

    /*public static GetInfoAccAnswer answersInstance = null;
    public static GetInfoAccAnswer getInstance() {
        return answersInstance;
    }
    public static void setInstance(GetInfoAccAnswer answers) {
        answersInstance = answers;
    }*/

    public HumanAnswer getHumanAnswer() {
        return humanAnswer;
    }

    public void setHumanAnswer(HumanAnswer humanAnswer) {
        this.humanAnswer = humanAnswer;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(this.humanAnswer, flags);
    }

    public GetInfoAccAnswer() {
    }

    protected GetInfoAccAnswer(Parcel in) {
        super(in);
        this.humanAnswer = in.readParcelable(HumanAnswer.class.getClassLoader());
    }

    public static final Creator<GetInfoAccAnswer> CREATOR = new Creator<GetInfoAccAnswer>() {
        @Override
        public GetInfoAccAnswer createFromParcel(Parcel source) {
            return new GetInfoAccAnswer(source);
        }

        @Override
        public GetInfoAccAnswer[] newArray(int size) {
            return new GetInfoAccAnswer[size];
        }
    };
}
