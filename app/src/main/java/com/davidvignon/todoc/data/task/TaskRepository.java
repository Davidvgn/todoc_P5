package com.davidvignon.todoc.data.task;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;

import com.davidvignon.todoc.BuildConfig;
import com.davidvignon.todoc.data.AppDatabase;
import com.davidvignon.todoc.data.dao.ProjectDao;
import com.davidvignon.todoc.data.dao.TaskDao;
import com.davidvignon.todoc.data.project.Project;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;

public class TaskRepository {

    @NonNull
    private final TaskDao taskDao;

    public TaskRepository(@NonNull TaskDao taskDao) {

        this.taskDao = taskDao;
//
//        if (BuildConfig.DEBUG) {
//            generateRandomTasks();
//        }
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


    @NonNull
    public LiveData<List<Task>> getTasksLiveData() {
        return taskDao.getTasksLiveData();
    }

//    @WorkerThread
//    private void generateRandomTasks() {
//        addTask(1, "Faire", LocalDateTime.now().toString());
//        addTask(2, "Acheter", LocalDateTime.now().toString());
//        addTask(3, "Build", LocalDateTime.now().toString());
//
//    }
}
