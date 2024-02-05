package com.example.collegeschedulerapp.ui.todo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.collegeschedulerapp.scheduling.Exam;
import com.example.collegeschedulerapp.scheduling.TodoTask;

import java.util.ArrayList;

public class TodoViewModel extends ViewModel {
    private final MutableLiveData<ArrayList<TodoTask>> todo
            = new MutableLiveData<>();

    public TodoViewModel () {
        todo.setValue(new ArrayList<TodoTask>());
    }
    public LiveData<ArrayList<TodoTask>> getText() {
        return todo;
    }

    public void setText(TodoTask task) {
        ArrayList<TodoTask> newArr = todo.getValue();
        if (task != null) {
            assert newArr != null;
            newArr.add(task);
        }
        todo.setValue(newArr);
    }
}
