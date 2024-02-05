package com.example.collegeschedulerapp.scheduling;

public class TodoTask {

    private String description;

    public TodoTask(String description) {
        setDescription(description);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}