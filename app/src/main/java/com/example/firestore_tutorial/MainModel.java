package com.example.firestore_tutorial;

public class MainModel {

    String name,email,course,year,image;

    MainModel()
    {

    }

    public MainModel(String name, String email, String course, String year, String image) {
        this.name = name;
        this.email = email;
        this.course = course;
        this.year = year;
        this.image = image;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
