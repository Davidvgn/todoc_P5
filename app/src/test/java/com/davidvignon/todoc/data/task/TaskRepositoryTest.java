package com.davidvignon.todoc.data.task;

import androidx.lifecycle.LiveData;

import com.davidvignon.todoc.data.ProjectWithTask;
import com.davidvignon.todoc.data.dao.TaskDao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class TaskRepositoryTest {

    private final TaskDao taskDao = Mockito.mock(TaskDao.class);

    private TaskRepository taskRepository;

    @Before
    public void setup(){
        taskRepository = new TaskRepository(taskDao);
    }

    @Test
    public void test_getAllProjectsWithTasks(){
        // Given
        @SuppressWarnings("unchecked") // Can not generify mock
        LiveData<List<ProjectWithTask>> projectWithTaskLiveData = Mockito.mock(LiveData.class);
        Mockito.doReturn(projectWithTaskLiveData).when(taskDao).getAllProjectsWithTasks();

        // When
        LiveData<List<ProjectWithTask>> result = taskRepository.getAllProjectsWithTasks();

        // Then
        assertEquals(projectWithTaskLiveData, result);
        Mockito.verify(taskDao).getAllProjectsWithTasks();
        Mockito.verifyNoMoreInteractions(taskDao);
    }

    @Test
    public void test_addTask(){
        // Given
        long projectId = 1L;
        String name = "Faire la vaisselle";

        // When
        taskRepository.addTask(projectId, name);

        // Then
        Mockito.verify(taskDao).insert(new Task(0, projectId, name));
        Mockito.verifyNoMoreInteractions(taskDao);
    }

    @Test
    public void test_deleteTask(){
        // Given
        long taskId = 42;

        // When
        taskRepository.deleteTask(taskId);

        // Then
        Mockito.verify(taskDao).deleteTask(taskId);
        Mockito.verifyNoMoreInteractions(taskDao);
    }
}
