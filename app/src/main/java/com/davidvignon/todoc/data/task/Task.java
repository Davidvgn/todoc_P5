package com.davidvignon.todoc.data.task;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.davidvignon.todoc.data.project.Project;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity(
        tableName = "task",
        foreignKeys = @ForeignKey(
                entity = Project.class,
                parentColumns = "projectId",
                childColumns = "projectId"
        )
)
public class Task {

    @PrimaryKey(autoGenerate = true)
    private final long id;

    private final long projectId;

    @NonNull
    private final String taskDescription;

    public Task(long id, long projectId, @NonNull String taskDescription) {
        this.id = id;
        this.projectId = projectId;
        this.taskDescription = taskDescription;
    }

    public long getId() {
        return id;
    }

    public long getProjectId() {
        return projectId;
    }

    @NonNull
    public String getTaskDescription() {
        return taskDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id &&
                projectId == task.projectId &&
                taskDescription.equals(task.taskDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, projectId, taskDescription);
    }

    @NonNull
    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", taskDescription='" + taskDescription + '\'' +
                '}';
    }
}