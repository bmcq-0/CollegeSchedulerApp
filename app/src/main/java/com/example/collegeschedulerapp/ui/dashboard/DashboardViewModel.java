package com.example.collegeschedulerapp.ui.dashboard;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.collegeschedulerapp.scheduling.Assignment;
import com.example.collegeschedulerapp.scheduling.Exam;
import com.example.collegeschedulerapp.storage.Database;

import java.util.ArrayList;

public class DashboardViewModel extends ViewModel {

    private Database<Assignment> database;

    private final MutableLiveData<ArrayList<Assignment>> assignments = new MutableLiveData<>();

    public void initialize(Context context) {
        database = new Database<>(context, "assignments");
        assignments.setValue(database.getData());
    }

    public LiveData<ArrayList<Assignment>> getAssignments() {
        return assignments;
    }

    public void removeAssignment(Assignment assignment) {
        database.removeData(assignment);
    }

    public void addAssignment(Assignment assignment) {
        ArrayList<Assignment> assignments = this.assignments.getValue();

        assert assignments != null;
        assert assignment != null;

        assignments.add(assignment);
        this.assignments.setValue(assignments);

        database.serialize();
    }

}