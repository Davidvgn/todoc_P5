package com.davidvignon.todoc.data.dao;

import androidx.room.Insert;

public interface TaskDao {

    @Insert
    long insert(TaskDao taskDao);

}
