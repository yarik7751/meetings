package com.elatesoftware.meetings.util.api.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetProfileInfoAnswer extends MessageAnswer {

    @SerializedName("Result")
    @Expose
    protected HumanAnswer result;

    public static GetProfileInfoAnswer answersInstance = null;
    public static GetProfileInfoAnswer getInstance() {
        return answersInstance;
    }
    public static void setInstance(GetProfileInfoAnswer answers) {
        answersInstance = answers;
    }

    public HumanAnswer getResult() {
        return result;
    }

    public void setResult(HumanAnswer result) {
        this.result = result;
    }
}
