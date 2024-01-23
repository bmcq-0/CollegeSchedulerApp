package com.example.collegeschedulerapp.scheduling;

public class Exam extends Assignment {

    private String location;

    public Exam(Course course, String name, String location) {
        super(course, name);
        setLocation(location);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}