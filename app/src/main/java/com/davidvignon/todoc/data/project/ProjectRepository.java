package com.davidvignon.todoc.data.project;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.davidvignon.todoc.data.dao.ProjectDao;

import java.util.List;

public class ProjectRepository {

    private final ProjectDao projectDao;

    public ProjectRepository(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    @MainThread
    @NonNull
    public LiveData<List<Project>> getAllProjects() {
        return projectDao.getAllProjects();
    }
}
