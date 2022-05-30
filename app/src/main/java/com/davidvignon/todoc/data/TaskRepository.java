package com.davidvignon.todoc.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.davidvignon.todoc.BuildConfig;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TaskRepository {

    private final MutableLiveData<List<Task>> tasksLiveData = new MutableLiveData<>(new ArrayList<>());

    private long maxId = 0;

    public TaskRepository() {
        if (BuildConfig.DEBUG) {
            generateRandomTasks();
        }
    }

    public void addTask(long projectId, String name, LocalDateTime creationTimestamp) {

        List<Task> currentList = tasksLiveData.getValue();

        if (currentList == null) {
            currentList = new ArrayList<>();
        }

        currentList.add(
            new Task(
                maxId,
                projectId,
                name,
                creationTimestamp
            )
        );
        maxId++;

        tasksLiveData.setValue(currentList);
    }

    public void deleteTask(long taskId) {
        List<Task> tasks = tasksLiveData.getValue();

        if (tasks == null) return;

        for (Iterator<Task> iterator = tasks.iterator(); iterator.hasNext(); ) {
            Task task = iterator.next();

            if (task.getId() == taskId) {
                iterator.remove();
                break;
            }

            tasksLiveData.setValue(tasks);
        }
    }

    @NonNull
    public LiveData<List<Task>> getTasksLiveData() {
        return tasksLiveData;
    }

    private void generateRandomTasks() {
        addTask(1, "Test task 1", LocalDateTime.now());

    }
}
