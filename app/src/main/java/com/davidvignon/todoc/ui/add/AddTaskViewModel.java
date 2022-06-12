package com.davidvignon.todoc.ui.add;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.davidvignon.todoc.data.SortingType;
import com.davidvignon.todoc.data.task.TaskRepository;
import com.davidvignon.todoc.utils.SingleLiveEvent;

import java.time.LocalDateTime;

public class AddTaskViewModel extends ViewModel {

    private final TaskRepository taskRepository;

    private final MediatorLiveData<AddTaskViewState> addTaskViewStateMediatorLiveData = new MediatorLiveData<>();
    private final MutableLiveData<SortingType> sortingTypeMutableLiveData = new MutableLiveData<>();

    private final SingleLiveEvent<String> showToastSingleLiveEvent = new SingleLiveEvent<>();


    public AddTaskViewModel(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
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
}
