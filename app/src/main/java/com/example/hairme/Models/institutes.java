package com.example.hairme.Models;

import com.google.gson.annotations.SerializedName;

public class institutes {
    @SerializedName("id")
    private int id;
    @SerializedName("CompanyName")
    private String CompanyName;
    @SerializedName("workFiled")
    private String workFiled;
    @SerializedName("location")
    private String location;
    @SerializedName("adress")
    private String adress;
    @SerializedName("Email")
    private String _Email;
    @SerializedName("fax")
    private  String fax;
    @SerializedName("logo")
    private  String logo;
    @SerializedName("phone")
    private String phone;
    @SerializedName("photo")
    private String photo;
    public institutes(int id, String companyName, String workFiled, String location, String adress, String email, String fax, String logo, String phone, String photo) {
        this.id = id;
        CompanyName = companyName;
        this.workFiled = workFiled;
        this.location = location;
        this.adress = adress;
        _Email = email;
        this.fax = fax;
        this.logo = logo;
        this.phone = phone;
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getWorkFiled() {
        return workFiled;
    }

    public void setWorkFiled(String workFiled) {
        this.workFiled = workFiled;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String get_Email() {
        return _Email;
    }

    public void set_Email(String _Email) {
        this._Email = _Email;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }


}
