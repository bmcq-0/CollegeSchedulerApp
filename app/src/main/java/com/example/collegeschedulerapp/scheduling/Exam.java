package com.example.collegeschedulerapp.scheduling;

import java.io.Serializable;
import java.time.LocalDate;

public class Exam extends Assignment implements Serializable {

    private String location;
    private String room;
    private String time;

    public Exam(String course, String name, LocalDate date, String time, String location, String room) {
        super(course, name, date);
        setLocation(location);
        setRoom(room);
        setTime(time);
    }

    public String getLocation() {
        return location;
    }

    public String getRoom() {
        return room;
    }

    public String getTime() {
        return time;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setTime(String time) {
        this.time = time;
    }

}