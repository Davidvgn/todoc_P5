package com.davidvignon.todoc.data.project;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;


@Entity(tableName = "project")
public class Project {

    @PrimaryKey
    private final Long projectId;

    @NonNull
    private final String name;

    @ColorInt
    private final int color;

    public Project(Long projectId, @NonNull String name, int color) {
        this.projectId = projectId;
        this.name = name;
        this.color = color;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public Long getProjectId() {
        return projectId;
    }

    @ColorInt
    public int getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project that = (Project) o;
        return projectId == that.projectId &&
            color == that.color &&
            name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, name, color);
    }

    @NonNull
    @NotNull
    @Override
    public String toString() {
        return "Project{" +
            "projectId=" + projectId +
            ", name='" + name + '\'' +
            ", color=" + color +
            '}';    }
}

