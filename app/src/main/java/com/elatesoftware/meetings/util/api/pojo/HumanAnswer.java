package com.elatesoftware.meetings.util.api.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.GregorianCalendar;

public class HumanAnswer {

    @SerializedName("Username")
    @Expose
    protected String username;

    @SerializedName("Password")
    @Expose
    protected String password;

    @SerializedName("Name")
    @Expose
    protected String firstName;

    @SerializedName("Gender")
    @Expose
    protected String gender;

    @SerializedName("Height")
    @Expose
    protected Double height;

    @SerializedName("Weight")
    @Expose
    protected Double weight;

    @SerializedName("HairColor")
    @Expose
    protected Integer hairColor;

    @SerializedName("DateOfBirth")
    @Expose
    protected Long dateOfBirth;

    @SerializedName("Id")
    @Expose
    protected Long id;

    @SerializedName("City")
    @Expose
    protected String city;

    @SerializedName("AboutMe")
    @Expose
    protected String aboutMe;

    public HumanAnswer(String firstName, Long dateOfBirth, String aboutMe) {
        this.firstName = firstName;
        this.dateOfBirth = dateOfBirth;
        this.aboutMe = aboutMe;
    }

    public HumanAnswer(String firstName, Long dateOfBirth, String aboutMe, Double height, Double weight) {
        this.firstName = firstName;
        this.dateOfBirth = dateOfBirth;
        this.aboutMe = aboutMe;
        this.height = height;
        this.weight = weight;
    }

    public static HumanAnswer answersInstance = null;
    public static HumanAnswer getInstance() {
        return answersInstance;
    }
    public static void setInstance(HumanAnswer answers) {
        answersInstance = answers;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getDateOfBirth() {
        return dateOfBirth;
    }

    public GregorianCalendar getDateOfBirthByCalendar() {
        if(dateOfBirth <= 0) {
            return null;
        }
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(dateOfBirth * 1000);
        return calendar;
    }

    public void setDateOfBirth(Long dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getHairColor() {
        return hairColor;
    }

    public void setHairColor(Integer hairColor) {
        this.hairColor = hairColor;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }
}
