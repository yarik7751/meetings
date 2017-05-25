package com.elatesoftware.meetings.util.api.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetPhotosAnswer extends MessageAnswer {

    @SerializedName("Result")
    @Expose
    protected List<Photo> result;

    public static GetPhotosAnswer answersInstance = null;
    public static GetPhotosAnswer getInstance() {
        return answersInstance;
    }
    public static void setInstance(GetPhotosAnswer answers) {
        answersInstance = answers;
    }

    public List<Photo> getResult() {
        return result;
    }

    public void setResult(List<Photo> result) {
        this.result = result;
    }
}
