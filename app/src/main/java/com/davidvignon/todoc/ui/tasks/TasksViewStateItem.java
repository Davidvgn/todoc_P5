package com.davidvignon.todoc.ui.tasks;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.time.LocalDateTime;
import java.util.Objects;

public abstract class TasksViewStateItem {

    public enum Type {
        TASK,
        EMPTY_STATE
    }

    @NonNull
    protected final Type type;


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
        private final String projectName;
        @ColorInt
        private final int projectColor;
        @NonNull
        private final String taskDescription;

        private String creationTimestamp = LocalDateTime.now().toString();

        public Task(long id, long projectId, String projectName,@ColorInt int projectColor, @NonNull String taskDescription, String creationTimestamp) {
            super(Type.TASK);

            this.id = id;
            this.projectId = projectId;
            this.projectName = projectName;
            this.projectColor = projectColor;
            this.taskDescription = taskDescription;
            this.creationTimestamp = creationTimestamp;
        }

        public long getTaskId() {
            return id;
        }

        public String getProjectName() {
            return projectName;
        }

        public int getProjectColor() {
            return projectColor;
        }

        @NonNull
        public String getTaskDescription() {
            return taskDescription;
        }

//        public String getCreationTimestamp() {
//            return creationTimestamp;
//        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Task task = (Task) o;
            return id == task.id &&
                projectId == task.projectId &&
                taskDescription.equals(task.taskDescription) &&
                creationTimestamp.equals(task.creationTimestamp);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, projectId, taskDescription, creationTimestamp);
        }

        @Override
        public String toString() {
            return "Task{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", name='" + taskDescription + '\'' +
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
