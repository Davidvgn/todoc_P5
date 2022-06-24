package com.davidvignon.todoc.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.davidvignon.todoc.data.project.Project;

import java.util.List;

@Dao
public interface ProjectDao {

    @Insert
    long insert(Project project);

    @Query("SELECT * FROM project")
    LiveData<List<Project>> getAllProjects();

    @Query("SELECT * FROM project WHERE projectId = :id")
    Project getProjectById(long id);
}
