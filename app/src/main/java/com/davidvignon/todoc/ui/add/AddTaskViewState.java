package com.davidvignon.todoc.ui.add;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

public class AddTaskViewState {

    @Nullable
    private final String name;

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
