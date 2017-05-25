package com.elatesoftware.meetings.util.api.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterAnswer extends MessageAnswer {

    @SerializedName("Result")
    @Expose
    protected String result;

    public static RegisterAnswer answersInstance = null;
    public static RegisterAnswer getInstance() {
        return answersInstance;
    }
    public static void setInstance(RegisterAnswer answers) {
        answersInstance = answers;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
