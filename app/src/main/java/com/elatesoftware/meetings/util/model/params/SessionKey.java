package com.elatesoftware.meetings.util.model.params;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SessionKey {

    @SerializedName("sessionKey")
    @Expose
    protected String sessionKey;

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }
}
