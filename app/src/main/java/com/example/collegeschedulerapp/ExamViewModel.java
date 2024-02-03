package com.example.collegeschedulerapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.collegeschedulerapp.scheduling.Course;
import com.example.collegeschedulerapp.scheduling.Exam;

import java.util.ArrayList;

public class ExamViewModel extends ViewModel {
    private final MutableLiveData<ArrayList<Exam>> exams
            = new MutableLiveData<>();

    public ExamViewModel () {
        exams.setValue(new ArrayList<Exam>());
    }
    public LiveData<ArrayList<Exam>> getText() {
        return exams;
    }

    public void setText(Exam exam) {
        ArrayList<Exam> newArr = exams.getValue();
        if (exam != null) {
            assert newArr != null;
            newArr.add(exam);
        }
        exams.setValue(newArr);
    }
}
