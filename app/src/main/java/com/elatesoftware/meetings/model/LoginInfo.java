package com.elatesoftware.meetings.model;

public class LoginInfo {

    private String login;
    private String pass;
    private String repPass;

    private static LoginInfo ourInstance = null;
    public static LoginInfo getInstance() {
        if(ourInstance == null) {
            ourInstance = new LoginInfo();
        }
        return ourInstance;
    }

    public LoginInfo() {
        this.login = "";
        this.pass = "";
        this.repPass = "";
    }

    public LoginInfo(String login, String pass, String repPass) {
        this.login = login;
        this.pass = pass;
        this.repPass = repPass;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getRepPass() {
        return repPass;
    }

    public void setRepPass(String repPass) {
        this.repPass = repPass;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return login + " : " + pass;
    }
}
