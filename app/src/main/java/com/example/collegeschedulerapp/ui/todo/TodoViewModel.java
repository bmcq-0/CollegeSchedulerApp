package com.example.collegeschedulerapp.ui.todo;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.collegeschedulerapp.scheduling.Task;
import com.example.collegeschedulerapp.storage.Database;

import java.util.ArrayList;

public class TodoViewModel extends ViewModel {

    private Database<Task> database;

    private final MutableLiveData<ArrayList<Task>> tasks = new MutableLiveData<>();

    public void initialize(Context context) {
        database = new Database<>(context, "tasks");
        tasks.setValue(database.getData());
    }

    public LiveData<ArrayList<Task>> getTasks() {
        return tasks;
    }
    public void removeTask(Task task) {
        database.removeData(task);
    }

    public void addTask(Task task) {
        ArrayList<Task> tasks = this.tasks.getValue();

        assert tasks != null;
        assert task != null;

        tasks.add(task);
        this.tasks.setValue(tasks);

        database.serialize();
    }

}