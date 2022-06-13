package com.davidvignon.todoc.ui.add;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.davidvignon.todoc.data.SortingType;
import com.davidvignon.todoc.data.project.Project;
import com.davidvignon.todoc.data.task.TaskRepository;
import com.davidvignon.todoc.utils.SingleLiveEvent;

import java.time.LocalDateTime;
import java.util.List;

public class AddTaskViewModel extends ViewModel {

    private final TaskRepository taskRepository;

    private final MediatorLiveData<AddTaskViewState> addTaskViewStateMediatorLiveData = new MediatorLiveData<>();
    private final MutableLiveData<SortingType> sortingTypeMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<Long> projectMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> taskNameMutableLiveData = new MutableLiveData<>();

    private final SingleLiveEvent<String> showToastSingleLiveEvent = new SingleLiveEvent<>();


    public AddTaskViewModel(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;

        addTaskViewStateMediatorLiveData.addSource(taskNameMutableLiveData, new Observer<String>() {
            @Override
            public void onChanged(String name) {
                combine(name, projectMutableLiveData.getValue());
            }
        });

        addTaskViewStateMediatorLiveData.addSource(projectMutableLiveData, new Observer<Long>() {
            @Override
            public void onChanged(Long project) {
                combine(taskNameMutableLiveData.getValue(), project);
            }
        });

    }

    private void combine(String name, Long project) {
        addTaskViewStateMediatorLiveData.setValue(
            new AddTaskViewState(
                name,
                project
            )
        );
    }


    public LiveData<AddTaskViewState> getAddTaskViewStateLiveData() {
        return addTaskViewStateMediatorLiveData;
    }

    public void onAddButtonClicked(
        long projectId,
        @NonNull String name) {

        LocalDateTime creationTimestamp = LocalDateTime.now();

        taskRepository.addTask(
            projectId,
            name,
            creationTimestamp
        );
    }

//    public void onProjectSelected(Project project) {
//        projectMutableLiveData.setValue(project.getId());
//    }
}
