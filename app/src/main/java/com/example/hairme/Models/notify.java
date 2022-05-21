package com.example.hairme.Models;

import com.google.gson.annotations.SerializedName;

public class notify {
    @SerializedName("id")
    private int id;
    @SerializedName("message")
    private String notify;
    @SerializedName("viewed")
    private String viwed;
    @SerializedName("job")
    private Job job;
    @SerializedName("createdAt")
    private String createdAt;
    @SerializedName("updatedAt")
    private String updatedAt;

    public notify(int id, String notify, String viwed, Job job, String createdAt, String updatedAt) {
        this.id = id;
        this.notify = notify;
        this.viwed = viwed;
        this.job = job;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNotify() {
        return notify;
    }

    public void setNotify(String notify) {
        this.notify = notify;
    }

    public String getViwed() {
        return viwed;
    }

    public void setViwed(String viwed) {
        this.viwed = viwed;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
