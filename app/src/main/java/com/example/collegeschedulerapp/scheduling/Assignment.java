package com.example.collegeschedulerapp.scheduling;

import java.io.Serializable;
import java.time.LocalDate;

public class Assignment implements Serializable {

    private String course;
    private String name;
    private LocalDate date;

    public Assignment(String course, String name, LocalDate date) {
        setCourse(course);
        setName(name);
        setDate(date);
    }

    public String getCourse() {
        return course;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

}