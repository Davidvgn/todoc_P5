package com.davidvignon.todoc.ui.add;

import androidx.annotation.Nullable;

import com.davidvignon.todoc.data.project.Project;
import com.davidvignon.todoc.data.project.ProjectRepository;

public class AddTaskViewState {

    @Nullable
    private final String name;
    @Nullable
    private final long project;

    public AddTaskViewState(@Nullable String name, long project) {
        this.name = name;
        this.project = project;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public long getProject() {
        return project;
    }

}
