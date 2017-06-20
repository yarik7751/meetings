package com.elatesoftware.meetings.api.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Photo {

    @SerializedName("AccountId")
    @Expose
    protected Integer accountId;

    @SerializedName("Name")
    @Expose
    protected String name;

    @SerializedName("Position")
    @Expose
    protected Integer position;

    @SerializedName("Content")
    @Expose
    protected String content;

    @SerializedName("IsMain")
    @Expose
    protected Boolean isMain;

    @SerializedName("Id")
    @Expose
    protected Integer id;

    public static Photo answersInstance = null;
    public static Photo getInstance() {
        return answersInstance;
    }
    public static void setInstance(Photo answers) {
        answersInstance = answers;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getMain() {
        return isMain;
    }

    public void setMain(Boolean main) {
        isMain = main;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
