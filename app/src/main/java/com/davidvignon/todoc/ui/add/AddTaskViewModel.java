package com.davidvignon.todoc.ui.add;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.davidvignon.todoc.R;
import com.davidvignon.todoc.data.project.Project;
import com.davidvignon.todoc.data.project.ProjectRepository;
import com.davidvignon.todoc.data.task.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Executor;

public class AddTaskViewModel extends ViewModel {

    private final TaskRepository taskRepository;
    public ProjectRepository projectRepository;
    private final Application application;

    private final Executor ioExecutor;


    private final MediatorLiveData<AddTaskViewState> addTaskViewStateMediatorLiveData = new MediatorLiveData<>();
    private final MutableLiveData<String> nameErrorMutableLiveData = new MutableLiveData<>();

    public AddTaskViewModel(Application application, TaskRepository taskRepository, ProjectRepository projectRepository, Executor ioExecutor) {
        this.application = application;
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.ioExecutor = ioExecutor;


        LiveData<List<Project>> allProjectsLivedata = projectRepository.getAllProjects();

        addTaskViewStateMediatorLiveData.addSource(allProjectsLivedata, new Observer<List<Project>>() {
            @Override
            public void onChanged(List<Project> projects) {
                combine(projects, nameErrorMutableLiveData.getValue());
            }
        });
        addTaskViewStateMediatorLiveData.addSource(nameErrorMutableLiveData, new Observer<String>() {
            @Override
            public void onChanged(String nameError) {
                combine(allProjectsLivedata.getValue(), nameError);
            }
        });
    }

    private void combine(List<Project> projects, String nameError) {

        addTaskViewStateMediatorLiveData.setValue(
            new AddTaskViewState(
                projects,
                nameError
            )
        );
    }

    public LiveData<AddTaskViewState> getAddTaskViewStateLiveData() {
        return addTaskViewStateMediatorLiveData;
    }

    public void onAddButtonClicked(
        long projectId,
        @NonNull String name
    ) {

        ioExecutor.execute(new Runnable() {
            @Override
            public void run() {
                LocalDateTime creationTimestamp = LocalDateTime.now();

                String trimmed = name.trim();

                if (!trimmed.isEmpty()) {
                    taskRepository.addTask(
                        projectId,
                        name,
                        creationTimestamp.toString()
                    );
                } else {
                    nameErrorMutableLiveData.setValue(application.getString(R.string.empty_name));//todo Nino "can't invoke setValue on a background Thread"
                }
            }
        });
    }
}
