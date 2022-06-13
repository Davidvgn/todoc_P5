package com.davidvignon.todoc.data.task;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Task {

    private final long id;
    private final long projectId;

    @NonNull
    private String name;
    private LocalDateTime creationTimestamp;

    public Task(long id, long projectId, @NonNull String name, LocalDateTime creationTimestamp) {
        this.id = id;
        this.projectId = projectId;
        this.name = name;
        this.creationTimestamp = creationTimestamp;

    }

    public long getId() {
        return id;
    }

    public long getProjectId() {
        return projectId;
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
        Task task = (Task) o;
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