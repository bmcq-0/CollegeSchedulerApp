package com.example.collegeschedulerapp.ui.exams;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.collegeschedulerapp.scheduling.Assignment;
import com.example.collegeschedulerapp.scheduling.Exam;
import com.example.collegeschedulerapp.storage.Database;

import java.util.ArrayList;

public class ExamViewModel extends ViewModel {

    private Database<Assignment> database;

    private final MutableLiveData<ArrayList<Exam>> exams = new MutableLiveData<>();

    private ArrayList<Exam> filterExams(ArrayList<Assignment> assignments) {
        ArrayList<Exam> exams = new ArrayList<>();

        for (Assignment assignment : assignments)
            if (assignment instanceof Exam)
                exams.add((Exam) assignment);

        return exams;
    }

    public void initialize(Context context) {
        database = new Database<>(context, "assignments");
        exams.setValue(filterExams(database.getData()));
    }

    public LiveData<ArrayList<Exam>> getExams() {
        return exams;
    }

    public void removeExam(Exam exam) {
        database.removeData(exam);
    }

    public void addExam(Exam exam) {
        ArrayList<Exam> exams = this.exams.getValue();

        assert exams != null;
        assert exam != null;

        exams.add(exam);
        this.exams.setValue(exams);

        database.getData().add(exam);
        database.serialize();
    }

}