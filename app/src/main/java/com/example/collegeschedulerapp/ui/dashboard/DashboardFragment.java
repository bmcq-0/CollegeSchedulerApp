package com.example.collegeschedulerapp.ui.dashboard;

import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegeschedulerapp.R;
import com.example.collegeschedulerapp.databinding.FragmentDashboardBinding;
import com.example.collegeschedulerapp.scheduling.Course;
import com.example.collegeschedulerapp.ui.home.HomeViewModel;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        homeViewModel =
                new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.addAssignmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<ArrayList<Course>>() {
                    @Override
                    public void onChanged(ArrayList<Course> courses) {
                        for (int i = 0; i < courses.size(); i++) {
                            Button button = new Button(getContext());
                            button.setText(courses.get(i).getName());
                            button.setTextSize(20);
                            button.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String text = "Selected Course:  " + button.getText();
                                    binding.selectedCourse.setText(text);
                                }
                            });
                            binding.assignmentCourses.addView(button);
                        }
                    }

                });
                binding.assignmentForm.setVisibility(View.VISIBLE);
                binding.addAssignmentButton.setVisibility(View.INVISIBLE);
            }
        });

        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                binding.assignmentForm.setVisibility(View.INVISIBLE);
                binding.addAssignmentButton.setVisibility(View.VISIBLE);
            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}