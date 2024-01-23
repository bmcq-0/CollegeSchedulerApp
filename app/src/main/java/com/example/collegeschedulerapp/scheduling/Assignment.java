package com.example.collegeschedulerapp.scheduling;

import java.time.LocalDateTime;

public class Assignment {

    private Course course;
    private String name;
    private LocalDateTime date;

    public Assignment(Course course, String name, LocalDateTime date) {
        setCourse(course);
        setName(name);
        setDate(date);
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        if (this.course != null) {
            this.course.removeAssignment(this);
        }
        this.course = course;
        course.addAssignment(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

}