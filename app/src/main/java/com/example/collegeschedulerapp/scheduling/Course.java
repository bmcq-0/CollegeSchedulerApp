package com.example.collegeschedulerapp.scheduling;

import java.util.ArrayList;

public class Course {

    private String name;
    private String instructor;

    private ArrayList<Assignment> assignments = new ArrayList<>();

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

    public void addAssignment(Assignment assignment) {
        if (assignment.getCourse() != this) {
            throw new IllegalStateException("Assignment does not belong to this course.");
        }
        assignments.add(assignment);
    }

    public void removeAssignment(Assignment assignment) {
        if (!assignments.remove(assignment)) {
            throw new IllegalStateException("Assignment is not listed in this course.");
        }
    }
}
