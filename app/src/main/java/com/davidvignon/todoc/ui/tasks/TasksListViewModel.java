package com.davidvignon.todoc.ui.tasks;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.davidvignon.todoc.data.ProjectWithTask;
import com.davidvignon.todoc.data.SortingType;
import com.davidvignon.todoc.data.task.Task;
import com.davidvignon.todoc.data.task.TaskRepository;
import com.davidvignon.todoc.utils.SingleLiveEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executor;

@SuppressWarnings("ALL")
public class TasksListViewModel extends ViewModel {

    @NonNull
    private final TaskRepository taskRepository;
    @NonNull
    private final Executor ioExecutor;

    private final MediatorLiveData<List<TasksViewStateItem>> mediatorLiveData = new MediatorLiveData<>();
    private final SingleLiveEvent<SortingType> sortingListMediatorLiveData = new SingleLiveEvent<>();

    public TasksListViewModel(@NonNull TaskRepository taskRepository, @NonNull Executor ioExecutor) {
        this.taskRepository = taskRepository;
        this.ioExecutor = ioExecutor;

        LiveData<List<ProjectWithTask>> projectWithTask = taskRepository.getAllProjectsWithTasks();

        mediatorLiveData.addSource(projectWithTask, new Observer<List<ProjectWithTask>>() {
            @Override
            public void onChanged(List<ProjectWithTask> projectWithTask) {
                combine(projectWithTask, sortingListMediatorLiveData.getValue());
            }
        });
        mediatorLiveData.addSource(sortingListMediatorLiveData, new Observer<SortingType>() {
            @Override
            public void onChanged(SortingType sortingType) {
                combine(projectWithTask.getValue(), sortingType);
            }
        });
    }

    public LiveData<List<TasksViewStateItem>> getTasksViewStateItemsLiveData() {
        return mediatorLiveData;
    }

    private void combine(@Nullable List<ProjectWithTask> tasks,
        SortingType sortingType) {
        if (tasks == null) {
            return;
        }


        List<TasksViewStateItem> taskViewStates = new ArrayList<>();

        if (sortingType == SortingType.ALPHABETICAL) {
            Collections.sort(tasks, new Comparator<ProjectWithTask>() {
                @Override
                public int compare(ProjectWithTask o1, ProjectWithTask o2) {
                    return o1.getProject().getName().compareToIgnoreCase(o2.getProject().getName());
                }
            });
        } else if (sortingType == SortingType.ALPHABETICAL_INVERTED) {
            Collections.sort(tasks, new Comparator<ProjectWithTask>() {
                @Override
                public int compare(ProjectWithTask o1, ProjectWithTask o2) {
                    return o2.getProject().getName().compareToIgnoreCase(o1.getProject().getName());
                }
            });
        }

        for (ProjectWithTask projectWithTask : tasks) {
            for (Task task : projectWithTask.getTask()) {
                taskViewStates.add(mapItem(projectWithTask, task));
            }
        }

        if ((sortingType == null) ||
            (sortingType == SortingType.OLD_FIRST)) {
            Collections.sort(taskViewStates, new Comparator<TasksViewStateItem>() {
                @Override
                public int compare(TasksViewStateItem o1, TasksViewStateItem o2) {
                    return Long.compare(((TasksViewStateItem.Task) o1).getTaskId(), ((TasksViewStateItem.Task) o2).getTaskId());
                }
            });
        } else if (sortingType == SortingType.RECENT_FIRST) {
            Collections.sort(taskViewStates, new Comparator<TasksViewStateItem>() {
                @Override
                public int compare(TasksViewStateItem o1, TasksViewStateItem o2) {
                    return Long.compare(((TasksViewStateItem.Task) o2).getTaskId(), ((TasksViewStateItem.Task) o1).getTaskId());
                }
            });
        }

        if (taskViewStates.isEmpty()) {
            taskViewStates.add(new TasksViewStateItem.EmptyState());
        }

        mediatorLiveData.setValue(taskViewStates);
    }

    public void onDeleteViewModelClicked(long taskId) {
        ioExecutor.execute(new Runnable() {
            @Override
            public void run() {
                taskRepository.deleteTask(taskId);
            }
        });
    }

    private TasksViewStateItem.Task mapItem(ProjectWithTask projectWithTask, Task task) {
        return new TasksViewStateItem.Task(
            task.getId(),
            projectWithTask.getProject().getName(),
            projectWithTask.getProject().getColor(),
            task.getTaskDescription()
        );
    }

    public void sortList(SortingType sortingType) {
        sortingListMediatorLiveData.setValue(sortingType);
    }
}

