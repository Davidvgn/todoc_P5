package com.davidvignon.todoc.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.davidvignon.todoc.data.ProjectWithTask;
import com.davidvignon.todoc.data.task.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    long insert(Task task);

    @Transaction
    @Query("SELECT * FROM project")
    LiveData<List<ProjectWithTask>> getAllProjectsWithTasks();

    @Query("DELETE FROM task WHERE id=:id")
    int deleteTask(long id);
}
