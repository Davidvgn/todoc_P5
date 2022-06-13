package com.davidvignon.todoc.data.project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.davidvignon.todoc.data.dao.ProjectDao;

import java.util.List;

public class ProjectRepository {

    // TODO à injecter dans la base de donnée lors de la création
    //  new Project(1L, "Projet Tartampion", 0xFFEADAD1)
    //  new Project(2L, "Projet Lucidia", 0xFFB4CDBA)
    //  new Project(3L, "Projet Circus", 0xFFA3CED2)

    private final ProjectDao projectDao;

    public ProjectRepository(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    @NonNull
    public LiveData<List<Project>> getAllProjects() {
        // TODO Ask DAO (this time with a LiveData was will be filled later in time)
    }

    @Nullable
    public Project getProjectById(long id) {
        // TODO Ask DAO (which will work asynchronously, thanks to an Executor!)
        return null;
    }
}
