package com.elatesoftware.meetings.util.api.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HumanAnswer{

    @SerializedName("Username")
    @Expose
    protected String username;

    @SerializedName("Password")
    @Expose
    protected String password;

    @SerializedName("FirstName")
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
    protected String hairColor;

    @SerializedName("DateOfBirth")
    @Expose
    protected String dateOfBirth;

    @SerializedName("Id")
    @Expose
    protected Long id;

    @SerializedName("City")
    @Expose
    protected String city;

    public static HumanAnswer answersInstance = null;
    public static HumanAnswer getInstance() {
        return answersInstance;
    }
    public static void setInstance(HumanAnswer answers) {
        answersInstance = answers;
    }

    public static HumanAnswer getAnswersInstance() {
        return answersInstance;
    }

    public static void setAnswersInstance(HumanAnswer answersInstance) {
        HumanAnswer.answersInstance = answersInstance;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
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

    public String getHairColor() {
        return hairColor;
    }

    public void setHairColor(String hairColor) {
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
}