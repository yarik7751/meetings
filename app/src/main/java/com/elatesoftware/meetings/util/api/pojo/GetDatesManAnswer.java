
package com.elatesoftware.meetings.util.api.pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetDatesManAnswer extends MessageAnswer {

    @SerializedName("Result")
    @Expose
    private List<Metting> result = null;

    public static GetDatesManAnswer answersInstance = null;
    public static GetDatesManAnswer getInstance() {
        return answersInstance;
    }
    public static void setInstance(GetDatesManAnswer answers) {
        answersInstance = answers;
    }

    public List<Metting> getResult() {
        return result;
    }

    public void setResult(List<Metting> result) {
        this.result = result;
    }

}
