package com.elatesoftware.meetings.api.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class HumanAnswer implements Parcelable {

    @SerializedName("Username")
    @Expose
    protected String username;

    @SerializedName("Password")
    @Expose
    protected String password;

    @SerializedName("Phone")
    @Expose
    protected String phone;

    @SerializedName("Name")
    @Expose
    protected String firstName;

    @SerializedName("Gender")
    @Expose
    protected Integer gender;

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

    @SerializedName("PhotosId")
    @Expose
    private List<Integer> photosId = null;

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

    /*public static HumanAnswer answersInstance = null;
    public static HumanAnswer getInstance() {
        return answersInstance;
    }
    public static void setInstance(HumanAnswer answers) {
        answersInstance = answers;
    }*/

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
        if(dateOfBirth == null || dateOfBirth <= 0) {
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

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
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

    public List<Integer> getPhotosId() {
        return photosId;
    }

    public void setPhotosId(List<Integer> photosId) {
        this.photosId = photosId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.username);
        dest.writeString(this.password);
        dest.writeString(this.phone);
        dest.writeString(this.firstName);
        dest.writeValue(this.gender);
        dest.writeValue(this.height);
        dest.writeValue(this.weight);
        dest.writeValue(this.hairColor);
        dest.writeValue(this.dateOfBirth);
        dest.writeValue(this.id);
        dest.writeString(this.city);
        dest.writeString(this.aboutMe);
        dest.writeList(this.photosId);
    }

    protected HumanAnswer(Parcel in) {
        this.username = in.readString();
        this.password = in.readString();
        this.phone = in.readString();
        this.firstName = in.readString();
        this.gender = (Integer) in.readValue(Integer.class.getClassLoader());
        this.height = (Double) in.readValue(Double.class.getClassLoader());
        this.weight = (Double) in.readValue(Double.class.getClassLoader());
        this.hairColor = (Integer) in.readValue(Integer.class.getClassLoader());
        this.dateOfBirth = (Long) in.readValue(Long.class.getClassLoader());
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.city = in.readString();
        this.aboutMe = in.readString();
        this.photosId = new ArrayList<Integer>();
        in.readList(this.photosId, Integer.class.getClassLoader());
    }

    public static final Creator<HumanAnswer> CREATOR = new Creator<HumanAnswer>() {
        @Override
        public HumanAnswer createFromParcel(Parcel source) {
            return new HumanAnswer(source);
        }

        @Override
        public HumanAnswer[] newArray(int size) {
            return new HumanAnswer[size];
        }
    };
}
