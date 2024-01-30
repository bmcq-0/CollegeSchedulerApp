package com.example.collegeschedulerapp.scheduling;

import java.sql.Time;
import java.util.ArrayList;

public class Course {

    private String name;
    private String instructor;

    private String time;

    private int id;

    private ArrayList<Assignment> assignments = new ArrayList<>();

    public Course(String name, String time, String instructor, int id) {
        setName(name);
        setTime(time);
        setInstructor(instructor);
        this.id = id;

    }

    public String getName() {
        return name;
    }
    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setTime(String time) {
        this.time = time;
    }

    public String getTime () {
        return time;
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
