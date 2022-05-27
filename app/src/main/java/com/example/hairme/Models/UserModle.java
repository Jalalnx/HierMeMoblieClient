package com.example.hairme.Models;

import com.google.gson.annotations.SerializedName;

public class UserModle {

    @SerializedName("id")
    private String id;
    @SerializedName("f_name")
private String f_name;
    @SerializedName("l_name")
    private String l_name;
    @SerializedName("phone")
    private String Phone_Number;
    @SerializedName("Email")
    private String Email;
    @SerializedName("adress")
    private String adress;
    @SerializedName("gender")
    private String gender;
    @SerializedName("photo")
    private String photo;
    @SerializedName("profession")
    private String profession;
    @SerializedName("education_level")
    private String education_level;


    public String getId() {
        return id;
    }

    public void setId(int String) {
        this.id = id;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getL_name() {
        return l_name;
    }

    public void setL_name(String l_name) {
        this.l_name = l_name;
    }

    public String getPhone_Number() {
        return Phone_Number;
    }

    public void setPhone_Number(String phone_Number) {
        Phone_Number = phone_Number;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getEducation_level() {
        return education_level;
    }

    public void setEducation_level(String education_level) {
        this.education_level = education_level;
    }

    public UserModle(String id, String f_name, String l_name, String phone_number, String email, String adress, String gender, String photo, String profession, String education_level) {
        this.id = id;
        this.f_name = f_name;
        this.l_name = l_name;
        Phone_Number = phone_number;
        Email = email;
        this.adress = adress;
        this.gender = gender;
        this.photo = photo;
        this.profession = profession;
        this.education_level = education_level;
    }
}
