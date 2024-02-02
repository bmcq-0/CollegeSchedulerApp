package com.example.collegeschedulerapp.scheduling;

import java.time.LocalDate;

public class Assignment {

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

    public void setCourse(String course) {
        this.course = course;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

}