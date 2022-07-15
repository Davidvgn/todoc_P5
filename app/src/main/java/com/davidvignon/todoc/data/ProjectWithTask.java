package com.davidvignon.todoc.data;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Relation;

import com.davidvignon.todoc.data.project.Project;
import com.davidvignon.todoc.data.task.Task;

import java.util.List;
import java.util.Objects;

public class ProjectWithTask {

    @NonNull
    @Embedded
    private final Project project;

    @NonNull
    @Relation(
        parentColumn = "projectId",
        entityColumn = "projectId"
    )
    private final List<Task> task;

    public ProjectWithTask(@NonNull Project project, @NonNull List<Task> task) {
        this.project = project;
        this.task = task;
    }

    @NonNull
    public Project getProject() {
        return project;
    }

    @NonNull
    public List<Task> getTask() {
        return task;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectWithTask that = (ProjectWithTask) o;
        return Objects.equals(project, that.project) &&
            Objects.equals(task, that.task);
    }

    @Override
    public int hashCode() {
        return Objects.hash(project, task);
    }

    @NonNull
    @Override
    public String toString() {
        return "ProjectWithTask{" +
            "project=" + project +
            ", task=" + task +
            '}';
    }
}
