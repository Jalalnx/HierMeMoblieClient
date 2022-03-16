package com.example.hairme.Models;

import com.google.gson.annotations.SerializedName;

public class employmentapplications {
    @SerializedName("id")
        private int id;
    @SerializedName("status")
        private String status;
    @SerializedName("createdAt")
        private String createdAt;
    @SerializedName("user")
        private  UserModle user;
    @SerializedName("job")
        private Job Job;

    public employmentapplications(int id, String status, String createdAt, UserModle user, com.example.hairme.Models.Job job) {
        this.id = id;
        this.status = status;
        this.createdAt = createdAt;
        this.user = user;
        Job = job;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public UserModle getUser() {
        return user;
    }

    public void setUser(UserModle user) {
        this.user = user;
    }

    public com.example.hairme.Models.Job getJob() {
        return Job;
    }

    public void setJob(com.example.hairme.Models.Job job) {
        Job = job;
    }
}
