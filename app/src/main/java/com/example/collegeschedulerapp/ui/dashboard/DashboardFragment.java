package com.example.collegeschedulerapp.ui.dashboard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.collegeschedulerapp.databinding.FragmentDashboardBinding;
import com.example.collegeschedulerapp.scheduling.Assignment;
import com.example.collegeschedulerapp.scheduling.Course;
import com.example.collegeschedulerapp.ui.home.HomeViewModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    HomeViewModel homeViewModel;
    DashboardViewModel dashboardViewModel;
    int size;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        dashboardViewModel = new ViewModelProvider(requireActivity()).get(DashboardViewModel.class);

        homeViewModel.initialize(getContext());
        dashboardViewModel.initialize(getContext());

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    private void sortAssignments() {

        binding.courseList.removeAllViews();

        dashboardViewModel.getAssignments().observe(getViewLifecycleOwner(), new Observer<ArrayList<Assignment>>() {
            @Override
            public void onChanged(ArrayList<Assignment> assignments) {
                binding.courseList.removeAllViews();
                for (int i = 0; i < assignments.size(); i++) {
                    addAssignment(assignments.get(i));
                }
                   binding.sortButton.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           binding.sortButton.setClickable(false);
                           binding.courseSort.setClickable(true);
                           assignments.sort(new Comparator<Assignment>() {
                               @Override
                               public int compare(Assignment o1, Assignment o2) {
                                   if (o1.getDate().isBefore(o2.getDate())) {
                                       return -1;
                                   } else if (o1.getDate().isAfter(o2.getDate())) {
                                       return 1;
                                   } else {
                                       return 0;
                                   }
                               }
                           });
                           binding.courseList.removeAllViews();
                           for (int i = 0; i < assignments.size(); i++) {
                               addAssignment(assignments.get(i));
                           }
                       }
                   });

                   binding.courseSort.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           binding.sortButton.setClickable(true);
                           binding.courseSort.setClickable(false);
                           ArrayList<Assignment> arr = new ArrayList<>();

                           for (int i = 0; i < assignments.size(); i++) {
                               if (!arr.contains(assignments.get(i))) {
                                   arr.add(assignments.get(i));
                               }
                               for (int j = i + 1; j < assignments.size(); j++) {
                                   if (i != j) {
                                       if (assignments.get(i).getCourse().contentEquals(assignments.get(j).getCourse())) {
                                           if (!arr.contains(assignments.get(j))) {
                                               arr.add(assignments.get(j));
                                           }
                                       }
                                   }
                               }
                           }
                           binding.courseList.removeAllViews();
                           for (int i = 0; i < arr.size(); i++) {
                               addAssignment(arr.get(i));
                           }
                       }
                   });
                }
        });
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sortAssignments();
        homeViewModel.getCourses().observe(getViewLifecycleOwner(), new Observer<ArrayList<Course>>() {
            @Override
            public void onChanged(ArrayList<Course> courses) {
                size = courses.size();
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

                    Button button2 = new Button(getContext());
                    button2.setText(courses.get(i).getName());
                    button2.setTextSize(20);
                    button2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    button2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String text = "Selected Course:  " + button2.getText();
                            binding.editSelectedCourse.setText(text);
                        }
                    });

                    binding.assignmentCourses.addView(button);
                    binding.editCoursesList.addView(button2);
                }
            }

        });

        binding.sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.courseList.removeAllViews();
                sortAssignments();
            }
        });

            binding.addAssignmentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (size != 0) {
                        binding.assignmentForm.setVisibility(View.VISIBLE);
                        binding.addAssignmentButton.setVisibility(View.INVISIBLE);

                        binding.dueDateChoice.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                            @Override
                            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                                Calendar c = Calendar.getInstance();
                                c.set(year, month, dayOfMonth);
                                binding.dueDateChoice.setDate(c.getTimeInMillis());
                            }
                        });
                    }
                }
            });


        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (String.valueOf(binding.selectedCourse.getText()).length() >= 18) {
                    String courseText = String.valueOf(binding.selectedCourse.getText()).substring(17);
                    String titleText = String.valueOf(binding.formTitle.getText());

                    LocalDate date = LocalDate.ofEpochDay(binding.dueDateChoice.getDate() / (1000 * 86400));
                    Assignment assignment = new Assignment(courseText, titleText, date);
                    dashboardViewModel.getAssignments().removeObservers(getViewLifecycleOwner());
                    dashboardViewModel.addAssignment(assignment);
                    addAssignment(assignment);

                    binding.assignmentForm.setVisibility(View.INVISIBLE);
                    binding.addAssignmentButton.setVisibility(View.VISIBLE);
                } else {
                    Toast toast = Toast.makeText(getContext(), "Select a Couse!", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });


    }

    private void addAssignment (Assignment assignment) {
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.HORIZONTAL);

        TextView textView = new TextView(getContext());
        textView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
        textView.setTextSize(20);
        String text = "Title: " + assignment.getName() + "\n Course: "
                + assignment.getCourse() + "\n Due Date: " + assignment.getDate();
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
        editButton(edit, assignment, textView);
        deleteButton(delete, assignment, layout);

        binding.courseList.addView(layout, layoutParams);

    }

    private void deleteButton(Button button, Assignment assignment, LinearLayout layout) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Do you want to delete this assignment?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        binding.courseList.removeView(layout);

                        dashboardViewModel.getAssignments().observe(getViewLifecycleOwner(), new Observer<ArrayList<Assignment>>() {
                            @Override
                            public void onChanged(ArrayList<Assignment> assignments) {
                                assignments.remove(assignment);
                            }
                        });
                        dashboardViewModel.removeAssignment(assignment);
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

    private void editButton(Button button, Assignment assignment, TextView textView) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.editAssignment.setVisibility(View.VISIBLE);
                binding.editDueDate.setDate(binding.dueDateChoice.getDate());
                binding.editTitle.setText(binding.formTitle.getText());
                binding.editSelectedCourse.setText(binding.selectedCourse.getText());

                binding.editDueDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                        Calendar c = Calendar.getInstance();
                        c.set(year, month, dayOfMonth);
                        binding.editDueDate.setDate(c.getTimeInMillis());
                    }
                });

                binding.editAddAssignment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("Confirm Edits?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                assignment.setName(String.valueOf(binding.editTitle.getText()));

                                LocalDate date = LocalDate.ofEpochDay(binding.editDueDate.getDate() / (1000 * 86400));
                                assignment.setDate(date);
                                assignment.setCourse(String.valueOf(binding.editSelectedCourse.getText()).substring(17));
                                String text = "Title: " + assignment.getName() + "\n Course: "
                                        + assignment.getCourse() + "\n Due Date: " + assignment.getDate();
                                textView.setText(text);
                                binding.editAssignment.setVisibility(View.INVISIBLE);
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
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}