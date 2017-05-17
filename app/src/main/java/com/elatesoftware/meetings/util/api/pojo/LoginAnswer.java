package com.elatesoftware.meetings.util.api.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginAnswer extends MessageAnswer {

    public static LoginAnswer answersInstance = null;
    public static LoginAnswer getInstance() {
        return answersInstance;
    }
    public static void setInstance(LoginAnswer answers) {
        answersInstance = answers;
    }

    @SerializedName("Result")
    @Expose
    protected HumanAnswer result;

    public HumanAnswer getResult() {
        return result;
    }

    public void setResult(HumanAnswer result) {
        this.result = result;
    }
}
