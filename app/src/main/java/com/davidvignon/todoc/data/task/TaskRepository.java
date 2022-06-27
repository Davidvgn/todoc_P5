package com.davidvignon.todoc.data.task;


import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;

import com.davidvignon.todoc.data.ProjectWithTask;
import com.davidvignon.todoc.data.dao.TaskDao;

import java.util.List;

public class TaskRepository {

    @NonNull
    private final TaskDao taskDao;

    public TaskRepository(@NonNull TaskDao taskDao) {

        this.taskDao = taskDao;
    }

    @MainThread
    public LiveData<List<ProjectWithTask>> getAllProjectsWithTasks() {
        return taskDao.getAllProjectsWithTasks();
    }

    @WorkerThread
    public void addTask(long projectId, String name, String creationTimestamp) {
        taskDao.insert(
                new Task(
                        0, // 0 to tell autoincrement to work
                        projectId,
                        name,
                        creationTimestamp
                )
        );
    }

    @WorkerThread
    public void deleteTask(long taskId) {
        taskDao.deleteTask(taskId);
    }

}
