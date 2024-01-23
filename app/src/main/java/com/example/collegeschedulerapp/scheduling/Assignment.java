package com.example.collegeschedulerapp.scheduling;

public class Assignment {

    private Course course;
    private String name;

    public Assignment(Course course, String name) {
        setCourse(course);
        setName(name);
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

}