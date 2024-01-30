package com.example.collegeschedulerapp.ui.home;

import android.widget.LinearLayout;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.collegeschedulerapp.scheduling.Course;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {
    private final MutableLiveData<ArrayList<Course>> courses
            = new MutableLiveData<>();

    public HomeViewModel () {
        courses.setValue(new ArrayList<Course>());
    }
    public LiveData<ArrayList<Course>> getText() {
        return courses;
    }

    public void setText(Course course) {
        ArrayList<Course> newArr = courses.getValue();
        if (course != null) {
            assert newArr != null;
            newArr.add(course);
        }
        courses.setValue(newArr);
    }

}