package com.elatesoftware.meetings.api.pojo;

import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginAnswer extends MessageAnswer {

    /*public static LoginAnswer answersInstance = null;
    public static LoginAnswer getInstance() {
        return answersInstance;
    }
    public static void setInstance(LoginAnswer answers) {
        answersInstance = answers;
    }*/

    @SerializedName("Result")
    @Expose
    protected Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public static class Result implements android.os.Parcelable {

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


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.sessionKey);
            dest.writeParcelable(this.account, flags);
        }

        public Result() {
        }

        protected Result(Parcel in) {
            this.sessionKey = in.readString();
            this.account = in.readParcelable(HumanAnswer.class.getClassLoader());
        }

        public static final Creator<Result> CREATOR = new Creator<Result>() {
            @Override
            public Result createFromParcel(Parcel source) {
                return new Result(source);
            }

            @Override
            public Result[] newArray(int size) {
                return new Result[size];
            }
        };
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(this.result, flags);
    }

    public LoginAnswer() {
    }

    protected LoginAnswer(Parcel in) {
        super(in);
        this.result = in.readParcelable(Result.class.getClassLoader());
    }

    public static final Creator<LoginAnswer> CREATOR = new Creator<LoginAnswer>() {
        @Override
        public LoginAnswer createFromParcel(Parcel source) {
            return new LoginAnswer(source);
        }

        @Override
        public LoginAnswer[] newArray(int size) {
            return new LoginAnswer[size];
        }
    };
}
