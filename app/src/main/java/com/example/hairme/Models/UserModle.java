package com.example.hairme.Models;

import com.google.gson.annotations.SerializedName;

public class UserModle {

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String username;
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
    @SerializedName(" password")
    private String password;
    public UserModle(int id, String username, String phone_Number, String email, String adress, String gender, String photo,String profession,String password) {
        this.id = id;
        this.username = username;
        this.Phone_Number = phone_Number;
      this. Email = email;
        this.adress = adress;
        this.gender = gender;
        this.photo = photo;
        this.profession =profession;
        this.password = password;
    }

    public String getProfession() {
        return profession;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public void setprofession(String profession){
        this.profession = profession;
    }

    public String getprofession()
    {
        return this.profession;
    }


}
