package com.davidvignon.todoc.ui.tasks;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.davidvignon.todoc.data.project.Project;
import com.davidvignon.todoc.data.project.ProjectRepository;
import com.davidvignon.todoc.data.task.Task;
import com.davidvignon.todoc.data.task.TaskRepository;

import java.time.LocalDateTime;
import java.util.Objects;

public abstract class TasksViewStateItem {

    public enum Type {
        TASK,
        EMPTY_STATE
    }

    @NonNull
    protected final Type type;
    private String nameTask;



    public TasksViewStateItem(@NonNull Type type) {
        this.type = type;
    }

    @NonNull
    public Type getType() {
        return type;
    }

    @Override
    public abstract boolean equals(@Nullable Object obj);

    public static class Task extends TasksViewStateItem {

        private final long id;
        private final long projectId;

        @NonNull
        private String name;
        private LocalDateTime creationTimestamp;

        public Task(long id, long projectId, @NonNull String name, LocalDateTime creationTimestamp) {
            super(Type.TASK);

            this.id = id;
            this.projectId = projectId;
            this.name = name;
            this.creationTimestamp = creationTimestamp;

        }

        public long getTaskId() {
            return id;
        }

        public Project getProject() {
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

    public static class EmptyState extends TasksViewStateItem {

        public EmptyState() {
            super(Type.EMPTY_STATE);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            return o != null && getClass() == o.getClass();
        }

        @NonNull
        @Override
        public String toString() {
            return "EmptyState{" +
                "type=" + type +
                '}';
        }
    }
}
