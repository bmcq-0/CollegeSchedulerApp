package com.example.collegeschedulerapp.ui.exams;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collegeschedulerapp.databinding.FragmentExamsBinding;
import com.example.collegeschedulerapp.scheduling.Course;
import com.example.collegeschedulerapp.scheduling.Exam;
import com.example.collegeschedulerapp.ui.home.HomeViewModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

public class ExamsFragment extends Fragment {

    FragmentExamsBinding binding;
    ExamViewModel examViewModel;
    HomeViewModel homeViewModel;

    int size;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentExamsBinding.inflate(inflater, container, false);

        examViewModel = new ViewModelProvider(requireActivity()).get(ExamViewModel.class);
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        homeViewModel.initialize(getContext());
        examViewModel.initialize(getContext());

        View root = binding.getRoot();

        return root;


    }

    public void addStoredExams() {
        examViewModel.getExams().observe(getViewLifecycleOwner(), new Observer<ArrayList<Exam>>() {
            @Override
            public void onChanged(ArrayList<Exam> exams) {
                for (int i = 0; i < exams.size(); i++) {
                    addNewExam(exams.get(i));

                }
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addStoredExams();

        homeViewModel.getCourses().observe(getViewLifecycleOwner(), new Observer<ArrayList<Course>>() {
            @Override
            public void onChanged(ArrayList<Course> courses) {
                size = courses.size();
                for (int i = 0; i < courses.size(); i++) {
                    Button button = new Button(getContext());
                    button.setText(courses.get(i).getName());
                    button.setTextSize(20);
                    button.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String text = "Selected Course:  " + button.getText();
                            binding.selected.setText(text);
                        }
                    });

                    Button button2 = new Button(getContext());
                    button2.setText(courses.get(i).getName());
                    button2.setTextSize(20);
                    button2.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);

                    button2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String text = "Selected Course:  " + button.getText();
                            binding.editSelected.setText(text);
                        }
                    });

                    binding.examCourses.addView(button);
                    binding.courses.addView(button2);
                }
            }
        });

        binding.examAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (size != 0) {
                    binding.examAddForm.setVisibility(View.VISIBLE);
                    binding.examAddButton.setVisibility(View.INVISIBLE);

                    binding.examDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                        @Override
                        public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                            Calendar c = Calendar.getInstance();
                            c.set(year, month, dayOfMonth);
                            binding.examDate.setDate(c.getTimeInMillis());
                        }
                    });
                }
            }
        });

        binding.formAddExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (String.valueOf(binding.selected.getText()).length() >= 18) {
                    String examTitle = String.valueOf(binding.examTitle.getText());
                    String examCourse = String.valueOf(binding.selected.getText()).substring(17);

                    LocalDate examDate = LocalDate.ofEpochDay(binding.examDate.getDate() / (1000 * 86400));
                    String examTime = String.valueOf(binding.timeInput.getText());
                    String examLocation = String.valueOf(binding.locationInput.getText());
                    String examRoom = String.valueOf(binding.roomInput.getText());

                    Exam exam = new Exam(examCourse, examTitle, examDate, examTime, examLocation, examRoom);
                    examViewModel.getExams().removeObservers(getViewLifecycleOwner());
                    examViewModel.addExam(exam);
                    addNewExam(exam);

                    binding.examAddForm.setVisibility(View.INVISIBLE);
                    binding.examAddButton.setVisibility(View.VISIBLE);
                } else {
                    Toast toast = Toast.makeText(getContext(), "Select a Couse!", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    private void addNewExam(Exam exam) {
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.HORIZONTAL);

        TextView textView = new TextView(getContext());
        textView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
        textView.setTextSize(20);
        String text = "Title: " + exam.getName() + "\n Course: "
                + exam.getCourse() + "\n Due Date: " + exam.getDate()
                + "\n Time: " + exam.getTime() + "\n Location: "
                + exam.getLocation() + "\n Room: " + exam.getRoom();
        textView.setText(text);

        Button edit = new Button(getContext());
        edit.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        edit.setTextSize(20);
        String editText = "Edit";
        edit.setText(editText);

        Button delete = new Button(getContext());
        delete.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        delete.setTextSize(20);
        String deleteText = "Delete";
        delete.setText(deleteText);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );

        LinearLayout.LayoutParams childParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );

        layout.addView(textView, childParams);
        layout.addView(edit, childParams);
        layout.addView(delete, childParams);
        editButton(edit, exam, textView);
        deleteButton(delete, exam, layout);

        binding.examList.addView(layout, layoutParams);
    }

    private void editButton(Button button, Exam exam, TextView textView) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.editAddForm.setVisibility(View.VISIBLE);
                binding.editDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                        Calendar c = Calendar.getInstance();
                        c.set(year, month, dayOfMonth);
                        binding.editDate.setDate(c.getTimeInMillis());
                    }
                });

                binding.updateExam.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (String.valueOf(binding.editSelected.getText()).length() >= 18) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setMessage("Confirm Edits?").setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                exam.setName(String.valueOf(binding.editExamTitle.getText()));

                                LocalDate date = LocalDate.ofEpochDay(binding.editDate.getDate() / (1000 * 86400));
                                exam.setDate(date);
                                exam.setCourse(String.valueOf(binding.editSelected.getText()).substring(17));
                                String text = "Title: " + exam.getName() + "\n Course: "
                                        + exam.getCourse() + "\n Due Date: " + exam.getDate()
                                        + "\n Time: " + exam.getTime() + "\n Location: "
                                        + exam.getLocation() + "\n Room: " + exam.getRoom();
                                textView.setText(text);
                                binding.editAddForm.setVisibility(View.INVISIBLE);
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        AlertDialog alr = builder.create();
                        alr.show();
                    } else {
                            Toast toast = Toast.makeText(getContext(), "Select a Couse!", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                });
            }
        });

    }

    private void deleteButton(Button button, Exam exam, LinearLayout layout) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Do you want to delete this assignment?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        binding.examList.removeView(layout);

                        examViewModel.getExams().observe(getViewLifecycleOwner(), new Observer<ArrayList<Exam>>() {
                            @Override
                            public void onChanged(ArrayList<Exam> exams) {
                                exams.remove(exam);
                            }
                        });
                        examViewModel.removeExam(exam);

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alr = builder.create();
                alr.show();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}