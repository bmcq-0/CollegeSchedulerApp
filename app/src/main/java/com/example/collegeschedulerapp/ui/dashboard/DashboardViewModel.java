package com.example.collegeschedulerapp.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.collegeschedulerapp.scheduling.Assignment;
import com.example.collegeschedulerapp.scheduling.Course;

import java.util.ArrayList;

public class DashboardViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<Assignment>> assignments
            = new MutableLiveData<>();

    public DashboardViewModel () {
        assignments.setValue(new ArrayList<Assignment>());
    }
    public LiveData<ArrayList<Assignment>> getText() {
        return assignments;
    }

    public void setText(Assignment assignment) {
        ArrayList<Assignment> newArr = assignments.getValue();
        if (assignment != null) {
            assert newArr != null;
            newArr.add(assignment);
        }
        assignments.setValue(newArr);
    }
}