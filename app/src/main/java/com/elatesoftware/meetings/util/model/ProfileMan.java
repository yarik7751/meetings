package com.elatesoftware.meetings.util.model;

import java.util.Calendar;

public class ProfileMan {

    private String name, about;
    private Calendar birthDate;

    public ProfileMan(String name, Calendar birthDate, String about) {
        this.name = name;
        this.birthDate = birthDate;
        this.about = about;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Calendar getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Calendar birthDate) {
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
