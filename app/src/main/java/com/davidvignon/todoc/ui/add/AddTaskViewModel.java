package com.davidvignon.todoc.ui.add;

import android.media.browse.MediaBrowser;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.davidvignon.todoc.data.task.TaskRepository;
import com.davidvignon.todoc.utils.SingleLiveEvent;

import java.time.LocalDateTime;

public class AddTaskViewModel extends ViewModel {

    private final TaskRepository taskRepository;

    private final MediatorLiveData<AddTaskViewState> addTaskViewStateMediatorLiveData = new MediatorLiveData<>();
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

    private void combine(String name, long project) {
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

    public SingleLiveEvent<String> getShowToastSingleLiveEvent(){
        return showToastSingleLiveEvent;
    }

    public void onAddButtonClicked(
        long projectId,
        @NonNull String name) {

        LocalDateTime creationTimestamp = LocalDateTime.now();

        String trimed = name.trim();

        if (!trimed.isEmpty()) {
            taskRepository.addTask(
                projectId,
                name,
                creationTimestamp
            );
        }

    }
}
