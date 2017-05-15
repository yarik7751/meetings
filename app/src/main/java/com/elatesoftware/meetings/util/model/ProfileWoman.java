package com.elatesoftware.meetings.util.model;

import java.util.Calendar;
import java.util.Date;

public class ProfileWoman {

    private String name, hairColor, city;
    private int height;
    private Calendar birthDate;

    public ProfileWoman(String name, int height, String hairColor, String city, Calendar birthDate) {
        this.name = name;
        this.height = height;
        this.hairColor = hairColor;
        this.city = city;
        this.birthDate = birthDate;
    }

    public Calendar getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Calendar birthDate) {
        this.birthDate = birthDate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHairColor() {
        return hairColor;
    }

    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
