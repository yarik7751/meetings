package com.elatesoftware.meetings.api.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetInfoAccAnswer extends MessageAnswer {

    @SerializedName("Result")
    @Expose
    protected HumanAnswer humanAnswer;

    public static GetInfoAccAnswer answersInstance = null;
    public static GetInfoAccAnswer getInstance() {
        return answersInstance;
    }
    public static void setInstance(GetInfoAccAnswer answers) {
        answersInstance = answers;
    }

    public HumanAnswer getHumanAnswer() {
        return humanAnswer;
    }

    public void setHumanAnswer(HumanAnswer humanAnswer) {
        this.humanAnswer = humanAnswer;
    }
}
