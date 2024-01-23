package com.example.collegeschedulerapp.scheduling;

public class Course {

    private String name;
    private String instructor;

    public Course(String name, String instructor) {
        setName(name);
        setInstructor(instructor);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }
}
