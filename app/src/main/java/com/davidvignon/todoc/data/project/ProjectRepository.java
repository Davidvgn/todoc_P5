package com.davidvignon.todoc.data.project;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;

import com.davidvignon.todoc.data.dao.ProjectDao;

import java.util.List;
import java.util.concurrent.Executor;

public class ProjectRepository {

    private final ProjectDao projectDao;

    public ProjectRepository(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    @MainThread
    @NonNull
    public LiveData<List<Project>> getAllProjects() {
        return projectDao.getAllProjects();
        // TODO Ask DAO (this time with a LiveData was will be filled later in time)
    }

    @WorkerThread
    @NonNull
    public Project getProjectById(long id) {
        return projectDao.getProjectById(id);
        // TODO Ask DAO (which will work asynchronously, thanks to an Executor!)
    }
}
