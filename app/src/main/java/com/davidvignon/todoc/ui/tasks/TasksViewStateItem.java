package com.davidvignon.todoc.ui.tasks;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.davidvignon.todoc.data.dao.ProjectDao;
import com.davidvignon.todoc.data.project.Project;
import com.davidvignon.todoc.data.project.ProjectRepository;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.Executor;

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

        @NonNull
        private String taskDescription;
        private long projectId;
        private ProjectRepository projectRepo;

        Executor ioExecutor;


        //        private LocalDateTime creationTimestamp;
        private String creationTimestamp = LocalDateTime.now().toString();

        public Task(long id, long projectId, @NonNull String taskDescription, String creationTimestamp) {
            super(Type.TASK);

            this.id = id;
            this.projectId = projectId;
            this.taskDescription = taskDescription;
            this.creationTimestamp = creationTimestamp;
        }

        public long getTaskId() {
            return id;
        }

        @NonNull
        public String getTaskDescription() {
            return taskDescription;
        }

        public Project getProject(){
//            return projectRepo.getProjectById(projectId);//todo david return NPE
            return null; //todo david en attendant de trouver la solution
        }

        public String getCreationTimestamp() {
            return creationTimestamp;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Task task = (Task) o;
            return id == task.id &&
                projectId == task.projectId &&
                taskDescription.equals(task.taskDescription) &&
                creationTimestamp == task.creationTimestamp;
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
