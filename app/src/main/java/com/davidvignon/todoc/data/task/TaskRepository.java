package com.davidvignon.todoc.data.task;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;

import com.davidvignon.todoc.BuildConfig;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

public class TaskRepository {

    public TaskRepository() {
        if (BuildConfig.DEBUG) {
            generateRandomTasks();
        }
    }

    @WorkerThread
    public void addTask(long projectId, String name, LocalDateTime creationTimestamp) {
        // TODO use an asynchronous thread to fill the database
        dao.insert(
                new Task(
                        0, // 0 to tell autoincrement to work
                        projectId,
                        name,
                        creationTimestamp
                )
        );
    }

    public void deleteTask(long taskId) {
        // TODO use an asynchronous thread to remove from the database
        dao.deleteById(taskId);
    }


    @NonNull
    public LiveData<List<Task>> getTasksLiveData() {
        return // TODO ask dao ;
    }

    private void generateRandomTasks() {

        // TODO async!
        addTask(1, "Faire", LocalDateTime.now());
        addTask(2, "Acheter", LocalDateTime.now());
        addTask(3, "Build", LocalDateTime.now());

    }
}
