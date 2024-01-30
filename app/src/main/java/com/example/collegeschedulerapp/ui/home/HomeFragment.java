package com.example.collegeschedulerapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.collegeschedulerapp.R;
import com.example.collegeschedulerapp.databinding.FragmentHomeBinding;
import com.example.collegeschedulerapp.scheduling.Course;
import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    LinearLayout layout;

    HomeViewModel homeViewModel;
    int size = 0;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        layout = binding.courseList;

        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View addButton = view.findViewById(R.id.class_add_button);
        TextInputEditText formCourse = view.findViewById(R.id.form_course);
        TextInputEditText formTime = view.findViewById(R.id.form_time);
        TextInputEditText formInstructor = view.findViewById(R.id.form_instructor);

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<ArrayList<Course>>() {
            @Override
            public void onChanged(ArrayList<Course> courses) {
                for (int i = 0; i < courses.size(); i++) {
                    addNewButton(courses.get(i));
                }
                size = courses.size();
            }

        });


        view.findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.findViewById(R.id.add_class_form).setVisibility(View.VISIBLE);
                view.findViewById(R.id.add_button).setVisibility(View.INVISIBLE);
            }
        });

       addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Course course = new Course( String.valueOf(formCourse.getText()),
                        String.valueOf(formTime.getText()),
                        String.valueOf(formInstructor.getText()),
                        size);
                homeViewModel.getText().removeObservers(getViewLifecycleOwner());
                homeViewModel.setText(course);
                addNewButton(course);
                view.findViewById(R.id.add_class_form).setVisibility(View.INVISIBLE);
                view.findViewById(R.id.add_button).setVisibility(View.VISIBLE);
            }
        });
    }

    private void addNewButton(Course course) {
        Button textView = new Button(getContext());
        String text = "Course: " + course.getName() + "\n Time: " + course.getTime()
                + "\n Instructor: " + course.getInstructor();
        textView.setText(text);
        textView.setTextSize(20.0f);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        textView.setId(course.getId());

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );

        layout.addView(textView, layoutParams);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

    }
}