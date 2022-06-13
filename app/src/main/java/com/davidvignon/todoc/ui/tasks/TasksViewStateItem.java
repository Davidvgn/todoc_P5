package com.davidvignon.todoc.ui.tasks;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.davidvignon.todoc.data.project.Project;
import com.davidvignon.todoc.data.project.ProjectRepository;

import java.time.LocalDateTime;
import java.util.Objects;

public class TasksViewStateItem {

    private final long id;
    private  final long projectId;

    @NonNull
    private String name;
    private LocalDateTime creationTimestamp;

    public TasksViewStateItem(long id, long projectId, @NonNull String name, LocalDateTime creationTimestamp) {
        this.id = id;
        this.projectId = projectId;
        this.name = name;
        this.creationTimestamp = creationTimestamp;

    }

    public long getId() {
        return id;
    }

//    public long getProjectId() {
//        return projectId;
//    }

    public Project getProject(){
        return ProjectRepository.getProjectById(projectId);
    }

    @NonNull
    public String getName() {
        return name;
    }

    public LocalDateTime getCreationTimestamp() {
        return creationTimestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TasksViewStateItem task = (TasksViewStateItem) o;
        return id == task.id &&
            projectId == task.projectId &&
            name.equals(task.name) &&
            creationTimestamp == task.creationTimestamp;
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, projectId, name, creationTimestamp);
    }

    @Override
    public String toString() {
        return "Task{" +
            "id=" + id +
            ", projectId=" + projectId +
            ", name='" + name + '\'' +
            ", creationTimeStamp ='" + creationTimestamp + '\'' +
            '}';
    }
}
