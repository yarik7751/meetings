package com.elatesoftware.meetings.api.pojo;

import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetPhotosAnswer extends MessageAnswer {

    @SerializedName("Result")
    @Expose
    protected List<Photo> result;

    /*public static GetPhotosAnswer answersInstance = null;
    public static GetPhotosAnswer getInstance() {
        return answersInstance;
    }
    public static void setInstance(GetPhotosAnswer answers) {
        answersInstance = answers;
    }*/

    public List<Photo> getResult() {
        return result;
    }

    public void setResult(List<Photo> result) {
        this.result = result;
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

    public GetPhotosAnswer() {
    }

    protected GetPhotosAnswer(Parcel in) {
        super(in);
        this.result = in.createTypedArrayList(Photo.CREATOR);
    }

    public static final Creator<GetPhotosAnswer> CREATOR = new Creator<GetPhotosAnswer>() {
        @Override
        public GetPhotosAnswer createFromParcel(Parcel source) {
            return new GetPhotosAnswer(source);
        }

        @Override
        public GetPhotosAnswer[] newArray(int size) {
            return new GetPhotosAnswer[size];
        }
    };
}
