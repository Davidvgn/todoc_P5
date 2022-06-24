package com.davidvignon.todoc.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.davidvignon.todoc.data.task.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    long insert(Task task);

    @Query("SELECT * FROM task")
    LiveData<List<Task>> getTasksLiveData();

    @Query("DELETE FROM task WHERE id=:id")
    int deleteTask(long id);
}
