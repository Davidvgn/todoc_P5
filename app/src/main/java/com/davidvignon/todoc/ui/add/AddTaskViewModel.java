package com.davidvignon.todoc.ui.add;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.davidvignon.todoc.data.TaskRepository;

public class AddTaskViewModel extends ViewModel {

    private final TaskRepository taskRepository;

    private final LiveData<AddTaskViewState> addTaskViewStateMediatorLiveData = new MediatorLiveData<>();


    public AddTaskViewModel(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }


    public LiveData<AddTaskViewState> getAddTaskViewStateLiveData() {
        return addTaskViewStateMediatorLiveData;
    }
}
