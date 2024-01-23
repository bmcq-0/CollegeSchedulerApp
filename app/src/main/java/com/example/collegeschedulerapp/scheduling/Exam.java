package com.example.collegeschedulerapp.scheduling;

import java.time.LocalDateTime;

public class Exam extends Assignment {

    private String location;

    public Exam(Course course, String name, LocalDateTime date, String location) {
        super(course, name, date);
        setLocation(location);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}