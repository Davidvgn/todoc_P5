package com.davidvignon.todoc.data.project;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class Project {

    @PrimaryKey
    private final long id;

    @NonNull
    @ColumnInfo
    private final String name;

    @ColorInt
    @ColumnInfo
    private final int color;

    private Project(long id, @NonNull String name, @ColorInt int color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    @NonNull
    public static Project[] getAllProjects() {
        return new Project[]{
            new Project(1L, "Projet Tartampion", 0xFFEADAD1),
            new Project(2L, "Projet Lucidia", 0xFFB4CDBA),
            new Project(3L, "Projet Circus", 0xFFA3CED2),
        };
    }

//    @Nullable
//    public static Project getProjectById(long id) {
//        for (Project project : getAllProjects()) {
//            if (project.id == id)
//                return project;
//        }
//        return null;
//    }

    public long getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }


    @ColorInt
    public int getColor() {
        return color;
    }


    @Override
    @NonNull
    public String toString() {
        return getName();
    }
}
