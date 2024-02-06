package com.example.collegeschedulerapp.scheduling;

import java.io.Serializable;

public class Task implements Serializable {

    private String description;

    public Task(String description) {
        setDescription(description);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}