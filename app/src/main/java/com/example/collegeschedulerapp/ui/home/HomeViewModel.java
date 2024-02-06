package com.example.collegeschedulerapp.ui.home;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.collegeschedulerapp.scheduling.Assignment;
import com.example.collegeschedulerapp.scheduling.Course;
import com.example.collegeschedulerapp.storage.Database;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {

    private Database<Course> database;

    private final MutableLiveData<ArrayList<Course>> courses = new MutableLiveData<>();

    public void initialize(Context context) {
        database = new Database<>(context, "courses");
        courses.setValue(database.getData());
    }

    public LiveData<ArrayList<Course>> getCourses() {
        return courses;
    }

    public void addCourse(Course course) {
        ArrayList<Course> courses = this.courses.getValue();

        assert courses != null;
        assert course != null;

        courses.add(course);
        this.courses.setValue(courses);

        database.serialize();
    }

}