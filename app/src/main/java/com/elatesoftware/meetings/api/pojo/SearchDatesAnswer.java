package com.elatesoftware.meetings.api.pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchDatesAnswer extends MessageAnswer {

    @SerializedName("Result")
    @Expose
    private List<Result> result = null;

    public static SearchDatesAnswer answersInstance = null;
    public static SearchDatesAnswer getInstance() {
        return answersInstance;
    }
    public static void setInstance(SearchDatesAnswer answers) {
        answersInstance = answers;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }
}
