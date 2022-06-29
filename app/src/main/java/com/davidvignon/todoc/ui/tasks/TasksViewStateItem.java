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
        private final String projectName;
        @ColorInt
        private final int projectColor;
        @NonNull
        private final String taskDescription;

        public Task(long id, String projectName,@ColorInt int projectColor, @NonNull String taskDescription) {
            super(Type.TASK);

            this.id = id;
            this.projectName = projectName;
            this.projectColor = projectColor;
            this.taskDescription = taskDescription;
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Task task = (Task) o;
            return id == task.id &&
                taskDescription.equals(task.taskDescription);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, taskDescription);
        }

        @Override
        public String toString() {
            return "Task{" +
                "id=" + id +
                ", name='" + taskDescription + '\'' +
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
