package com.example.collegeschedulerapp.scheduling;

import java.io.Serializable;
import java.util.ArrayList;

public class Course implements Serializable {

    private String name;
    private String instructor;
    private String time;

    private int id;

    private final ArrayList<Assignment> assignments = new ArrayList<>();

    public Course(String name, String time, String instructor, int id) {
        setName(name);
        setTime(time);
        setInstructor(instructor);
        setId(id);
    }

    public String getName() {
        return name;
    }

    public String getInstructor() {
        return instructor;
    }

    public String getTime() {
        return time;
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

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public void setId(int id) {
        this.id = id;
    }

}
