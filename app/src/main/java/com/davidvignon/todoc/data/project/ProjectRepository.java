package com.davidvignon.todoc.data.project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ProjectRepository {

    @NonNull
    public static Project[] getAllProjects() {
        return new Project[]{
            new Project(1L, "Projet Tartampion", 0xFFEADAD1),
            new Project(2L, "Projet Lucidia", 0xFFB4CDBA),
            new Project(3L, "Projet Circus", 0xFFA3CED2),
        };
    }

    @Nullable
    public static Project getProjectById(long id) {
        for (Project project : getAllProjects()) {
            if (project.id == id)
                return project;
        }
        return null;
    }
}
