package com.elatesoftware.meetings.api.pojo;

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
    protected Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public class Result {

        @SerializedName("SessionKey")
        @Expose
        protected String sessionKey;

        @SerializedName("Account")
        @Expose
        protected HumanAnswer account;

        public HumanAnswer getAccount() {
            return account;
        }

        public void setAccount(HumanAnswer account) {
            this.account = account;
        }

        public String getSessionKey() {
            return sessionKey;
        }

        public void setSessionKey(String sessionKey) {
            this.sessionKey = sessionKey;
        }
    }
}
