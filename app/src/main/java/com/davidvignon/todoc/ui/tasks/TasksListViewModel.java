package com.davidvignon.todoc.ui.tasks;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.davidvignon.todoc.data.Task;
import com.davidvignon.todoc.data.TaskRepository;

import java.util.ArrayList;
import java.util.List;

public class TasksListViewModel extends ViewModel {

    private final TaskRepository taskRepository;

    private final MediatorLiveData<List<TasksViewStateItem>> mediatorLiveData = new MediatorLiveData<>();

    public TasksListViewModel(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;

        LiveData<List<Task>> tasksLiveData = taskRepository.getTasksLiveData();

        mediatorLiveData.addSource(tasksLiveData, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                combine(tasks);
            }
        });
    }

    public LiveData<List<TasksViewStateItem>> getTasksViewStateItemsLiveData(){
        return mediatorLiveData;
    }

    private void combine(@Nullable List<Task> tasks){
        if (tasks == null) {
            return;
        }

        List<TasksViewStateItem> tasksViewStateItems = new ArrayList<>();

        for (Task task : tasks) {
            tasksViewStateItems.add(
                new TasksViewStateItem(
                    task.getId(),
                    task.getProjectId(),
                    task.getName(),
                    task.getCreationTimestamp()
                )
            );
        }
    }


    public void onDeleteViewModelClicked(long taskId) {
        taskRepository.deleteTask(taskId);
    }
}
