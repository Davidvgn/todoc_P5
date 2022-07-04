package com.davidvignon.todoc.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.davidvignon.todoc.data.dao.TaskDao;
import com.davidvignon.todoc.data.task.Task;
import com.davidvignon.todoc.data.task.TaskRepository;

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

    TaskRepository taskRepository;

    @Before
    public void setup(){
        taskRepository = new TaskRepository(taskDao);
    }

    @Test
    public void test_getAllProjectsWithTasks(){
        // Given
        LiveData<List<ProjectWithTask>> projectWithTaskLiveData = Mockito.spy(new MutableLiveData<>());
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
        Task task = new Task(0, projectId, name);

        // When
        taskRepository.addTask(projectId, name);

        // Then
        Mockito.verify(taskDao).insert(task);
        Mockito.verifyNoMoreInteractions(taskDao);
    }

    @Test
    public void test_deleteTask(){
        // Given
        long taskId = 42;

        // When
        taskRepository.deleteTask(taskId);

        // Then
        Mockito.verify(taskDao).deleteTask(42);
        Mockito.verifyNoMoreInteractions(taskDao);
    }
}
