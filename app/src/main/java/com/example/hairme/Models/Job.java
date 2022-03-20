package com.example.hairme.Models;

import com.google.gson.annotations.SerializedName;

public class Job {

    @SerializedName("id")
    private int id;
    @SerializedName("job_role")
    private String  job_role;
    @SerializedName("salary_range")
    private String salary_range;
    @SerializedName("years_of_experience")
    private int years_of_experience;
    @SerializedName("vacancies")
    private String vacancies;
    @SerializedName("Employment_status")
    private String Employment_status;
    @SerializedName("employment_type")
    private String  employment_type;
    @SerializedName("education_level")
    private  String education_level;
    @SerializedName("career_level")
    private String career_level;
    @SerializedName("Gender")
    private String  Gender;
    @SerializedName("industry")
    private String   industry;
    @SerializedName("contry")
    private String contry;
    @SerializedName("city")
    private String   city;
    @SerializedName("joo_description")
    private String    joo_description;
    @SerializedName("max_years_of_experience")
    private String max_years_of_experience;
    @SerializedName("dead_line")
    private String dead_line;
    @SerializedName("requirements")
    private String requirements;
    @SerializedName("status")
    private String status;
    @SerializedName("createdAt")
    private String createdAt ;
    @SerializedName("institute")
    private institutes institutes;
    @SerializedName("views")
    private String views;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJob_role() {
        return job_role;
    }

    public void setJob_role(String job_role) {
        this.job_role = job_role;
    }

    public String getSalary_range() {
        return salary_range;
    }

    public void setSalary_range(String salary_range) {
        this.salary_range = salary_range;
    }

    public int getYears_of_experience() {
        return years_of_experience;
    }

    public void setYears_of_experience(int years_of_experience) {
        this.years_of_experience = years_of_experience;
    }

    public String getVacancies() {
        return vacancies;
    }

    public void setVacancies(String vacancies) {
        this.vacancies = vacancies;
    }

    public String getEmployment_status() {
        return Employment_status;
    }

    public void setEmployment_status(String employment_status) {
        Employment_status = employment_status;
    }

    public String getEmployment_type() {
        return employment_type;
    }

    public void setEmployment_type(String employment_type) {
        this.employment_type = employment_type;
    }

    public String getEducation_level() {
        return education_level;
    }

    public void setEducation_level(String education_level) {
        this.education_level = education_level;
    }

    public String getCareer_level() {
        return career_level;
    }

    public void setCareer_level(String career_level) {
        this.career_level = career_level;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getContry() {
        return contry;
    }

    public void setContry(String contry) {
        this.contry = contry;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getJoo_description() {
        return joo_description;
    }

    public void setJoo_description(String joo_description) {
        this.joo_description = joo_description;
    }

    public String getMax_years_of_experience() {
        return max_years_of_experience;
    }

    public void setMax_years_of_experience(String max_years_of_experience) {
        this.max_years_of_experience = max_years_of_experience;
    }

    public String getDead_line() {
        return dead_line;
    }

    public void setDead_line(String dead_line) {
        this.dead_line = dead_line;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
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

    public com.example.hairme.Models.institutes getInstitutes() {
        return institutes;
    }

    public void setInstitutes(com.example.hairme.Models.institutes institutes) {
        this.institutes = institutes;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public Job(int id, String job_role, String salary_range, int years_of_experience, String vacancies, String employment_status, String employment_type, String education_level, String career_level, String gender, String industry, String contry, String city, String joo_description, String max_years_of_experience, String dead_line, String requirements, String status, String createdAt, com.example.hairme.Models.institutes institutes, String views) {
        this.id = id;
        this.job_role = job_role;
        this.salary_range = salary_range;
        this.years_of_experience = years_of_experience;
        this.vacancies = vacancies;
        Employment_status = employment_status;
        this.employment_type = employment_type;
        this.education_level = education_level;
        this.career_level = career_level;
        Gender = gender;
        this.industry = industry;
        this.contry = contry;
        this.city = city;
        this.joo_description = joo_description;
        this.max_years_of_experience = max_years_of_experience;
        this.dead_line = dead_line;
        this.requirements = requirements;
        this.status = status;
        this.createdAt = createdAt;
        this.institutes = institutes;
        this.views = views;
    }
}
