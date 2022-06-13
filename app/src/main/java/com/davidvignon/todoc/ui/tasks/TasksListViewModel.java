package com.davidvignon.todoc.ui.tasks;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.davidvignon.todoc.data.SortingType;
import com.davidvignon.todoc.data.task.Task;
import com.davidvignon.todoc.data.task.TaskRepository;
import com.davidvignon.todoc.utils.SingleLiveEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TasksListViewModel extends ViewModel {

    private final TaskRepository taskRepository;

    private final MediatorLiveData<List<TasksViewStateItem>> mediatorLiveData = new MediatorLiveData<>();
    private final SingleLiveEvent<SortingType> sortingListMediatorLiveData = new SingleLiveEvent<>();


    public TasksListViewModel(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;

        LiveData<List<Task>> tasksLiveData = taskRepository.getTasksLiveData();

        mediatorLiveData.addSource(tasksLiveData, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                combine(tasks, sortingListMediatorLiveData.getValue());
            }
        });
        mediatorLiveData.addSource(sortingListMediatorLiveData, new Observer<SortingType>() {
            @Override
            public void onChanged(SortingType sortingType) {
                combine(tasksLiveData.getValue(), sortingType);
            }
        });
    }

    public LiveData<List<TasksViewStateItem>> getTasksViewStateItemsLiveData() {
        return mediatorLiveData;
    }

    private void combine(@Nullable List<Task> tasks,
        SortingType sortingType) {
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

        if (sortingType != SortingType.NONE) {
            if (sortingType == SortingType.ALPHABETICAL) {
                Collections.sort(tasksViewStateItems, new Comparator<TasksViewStateItem>() {
                    @Override
                    public int compare(TasksViewStateItem o1, TasksViewStateItem o2) {
                        return o1.getName().compareToIgnoreCase(o2.getName());
                    }
                });
            } else if (sortingType == SortingType.ALPHABETICAL_INVERTED) {
                Collections.sort(tasksViewStateItems, new Comparator<TasksViewStateItem>() {
                    @Override
                    public int compare(TasksViewStateItem o1, TasksViewStateItem o2) {
                        return o2.getName().compareToIgnoreCase(o1.getName());
                    }
                });
            } else if (sortingType == SortingType.RECENT_FIRST) {
                Collections.sort(tasksViewStateItems, new Comparator<TasksViewStateItem>() {
                    @Override
                    public int compare(TasksViewStateItem o1, TasksViewStateItem o2) {
                        return o1.getCreationTimestamp().compareTo(o2.getCreationTimestamp());
                    }
                });
            } else if (sortingType == SortingType.OLD_FIRST) {
                Collections.sort(tasksViewStateItems, new Comparator<TasksViewStateItem>() {
                    @Override
                    public int compare(TasksViewStateItem o1, TasksViewStateItem o2) {
                        return o2.getCreationTimestamp().compareTo(o1.getCreationTimestamp());
                    }
                });
            }
        }
        mediatorLiveData.setValue(tasksViewStateItems);
    }


    public void onDeleteViewModelClicked(long taskId) {
        taskRepository.deleteTask(taskId);
    }

    public void sortList(SortingType sortingType) {
        sortingListMediatorLiveData.setValue(sortingType);
    }
}
