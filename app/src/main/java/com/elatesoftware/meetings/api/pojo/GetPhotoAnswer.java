package com.elatesoftware.meetings.api.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPhotoAnswer extends MessageAnswer {

    @SerializedName("Result")
    @Expose
    protected Photo result;

    public static GetPhotoAnswer answersInstance = null;
    public static GetPhotoAnswer getInstance() {
        return answersInstance;
    }
    public static void setInstance(GetPhotoAnswer answers) {
        answersInstance = answers;
    }

    public Photo getResult() {
        return result;
    }

    public void setResult(Photo result) {
        this.result = result;
    }
}
