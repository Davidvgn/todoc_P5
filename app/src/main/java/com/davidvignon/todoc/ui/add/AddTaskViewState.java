package com.davidvignon.todoc.ui.add;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.davidvignon.todoc.data.project.Project;

import java.util.List;
import java.util.Objects;

public class AddTaskViewState {

    private final List<Project> projects;

    @Nullable
    private final String nameError;

    public AddTaskViewState(List<Project> projects, @Nullable String nameError) {
        this.projects = projects;
        this.nameError = nameError;
    }

    public List<Project> getProjects() {
        return projects;
    }

    @Nullable
    public String getNameError() {
        return nameError;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddTaskViewState that = (AddTaskViewState) o;
        return Objects.equals(projects, that.projects) && Objects.equals(nameError, that.nameError);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projects, nameError);
    }

    @NonNull
    @Override
    public String toString() {
        return "AddTaskViewState{" +
                "projects=" + projects +
                ", nameError='" + nameError + '\'' +
                '}';
    }
}
