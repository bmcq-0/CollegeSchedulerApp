package com.example.collegeschedulerapp.ui.todo;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collegeschedulerapp.databinding.FragmentTodoBinding;
import com.example.collegeschedulerapp.scheduling.Task;

import java.util.ArrayList;

public class TodoFragment extends Fragment {

    FragmentTodoBinding binding;
    TodoViewModel todoViewModel;

    int size;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTodoBinding.inflate(inflater, container, false);
        todoViewModel = new ViewModelProvider(requireActivity()).get(TodoViewModel.class);

        todoViewModel.initialize(getContext());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addStoredTasks();

        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.addTodoForm.setVisibility(View.VISIBLE);
                binding.addButton.setVisibility(View.INVISIBLE);
            }
        });

        binding.todoAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (String.valueOf(binding.formTodo.getText()).length() > 0) {
                    Task task = new Task(String.valueOf(binding.formTodo.getText()));
                    todoViewModel.getTasks().removeObservers(getViewLifecycleOwner());
                    todoViewModel.addTask(task);
                    addNewTask(task);

                    binding.addTodoForm.setVisibility(View.INVISIBLE);
                    binding.addButton.setVisibility(View.VISIBLE);
                } else {
                    Toast toast = Toast.makeText(getContext(), "Enter a task!", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    public void addStoredTasks() {
        todoViewModel.getTasks().observe(getViewLifecycleOwner(), new Observer<ArrayList<Task>>() {
            @Override
            public void onChanged(ArrayList<Task> todo) {
                for (int i = 0; i < todo.size(); i++) {
                    addNewTask(todo.get(i));
                }
            }
        });
    }

    private void addNewTask(Task task) {
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);

        TextView textView = new TextView(getContext());
        textView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
        textView.setTextSize(20);
        textView.setText(task.getDescription());

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
        editButton(edit, task, textView);
        deleteButton(delete, task, layout);

        binding.todoList.addView(layout, layoutParams);
    }

    private void editButton(Button button, Task task, TextView textView) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.editForm.setVisibility(View.VISIBLE);

                binding.updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (String.valueOf(binding.editTodoDesc.getText()).length() > 0) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setMessage("Confirm Edits?").setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            task.setDescription(String.valueOf(binding.editTodoDesc.getText()));
                                            textView.setText(task.getDescription());
                                            binding.editForm.setVisibility(View.INVISIBLE);
                                        }
                                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            AlertDialog alr = builder.create();
                            alr.show();
                        } else {
                            Toast toast = Toast.makeText(getContext(), "Enter a description!", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                });
            }
        });

    }

    private void deleteButton(Button button, Task task, LinearLayout layout) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Do you want to delete this task?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        binding.todoList.removeView(layout);

                        todoViewModel.getTasks().observe(getViewLifecycleOwner(), new Observer<ArrayList<Task>>() {
                            @Override
                            public void onChanged(ArrayList<Task> exams) {
                                exams.remove(task);
                            }
                        });

                        todoViewModel.removeTask(task);
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
}