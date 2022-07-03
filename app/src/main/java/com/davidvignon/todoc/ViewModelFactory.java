package com.davidvignon.todoc;


import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.davidvignon.todoc.data.AppDatabase;
import com.davidvignon.todoc.data.project.ProjectRepository;
import com.davidvignon.todoc.data.task.TaskRepository;
import com.davidvignon.todoc.ui.add.AddTaskViewModel;
import com.davidvignon.todoc.ui.tasks.TasksListViewModel;
import com.davidvignon.todoc.ui.utils.MainThreadExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private static ViewModelFactory factory;

    private final MainThreadExecutor mainThreadExecutor = new MainThreadExecutor();
    private final Executor ioExecutor = Executors.newFixedThreadPool(4);

    @NonNull
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    private ViewModelFactory() {
        AppDatabase appDatabase = AppDatabase.getInstance(MainApplication.getInstance(), ioExecutor);
        taskRepository = new TaskRepository(
            appDatabase.getTaskDao());
        projectRepository = new ProjectRepository(appDatabase.getProjectDao());
    }

    public static ViewModelFactory getInstance() {
        if (factory == null) {
            synchronized (ViewModelFactory.class) {
                if (factory == null) {
                    factory = new ViewModelFactory();
                }
            }
        }
        return factory;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TasksListViewModel.class)) {
            return (T) new TasksListViewModel(
                taskRepository,
                ioExecutor
            );
        } else if (modelClass.isAssignableFrom(AddTaskViewModel.class)) {
            return (T) new AddTaskViewModel(
                MainApplication.getInstance(),
                taskRepository,
                projectRepository,
                mainThreadExecutor,
                ioExecutor
            );
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}

