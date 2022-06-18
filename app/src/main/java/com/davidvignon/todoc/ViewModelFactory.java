package com.davidvignon.todoc;


import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.davidvignon.todoc.data.task.TaskRepository;
import com.davidvignon.todoc.ui.add.AddTaskViewModel;
import com.davidvignon.todoc.ui.tasks.TasksListViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private static ViewModelFactory factory;

    public static ViewModelFactory getInstance() {
        if (factory == null) {
            synchronized (ViewModelFactory.class) {
                if (factory == null) {
                    factory = new ViewModelFactory(
                        new TaskRepository()
                    );
                }
            }
        }

        return factory;
    }

    @NonNull
    private final TaskRepository taskRepository;

    private ViewModelFactory(
        @NonNull TaskRepository taskRepository
    ) {
        this.taskRepository = taskRepository;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TasksListViewModel.class)) {
            return (T) new TasksListViewModel(
                taskRepository
            );
        } else if (modelClass.isAssignableFrom(AddTaskViewModel.class)) {
            return (T) new AddTaskViewModel(
                taskRepository
            );
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}

