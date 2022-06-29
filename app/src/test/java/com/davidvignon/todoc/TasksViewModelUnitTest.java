package com.davidvignon.todoc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.davidvignon.todoc.data.ProjectWithTask;
import com.davidvignon.todoc.data.project.Project;
import com.davidvignon.todoc.data.task.Task;
import com.davidvignon.todoc.data.task.TaskRepository;
import com.davidvignon.todoc.ui.tasks.TasksViewStateItem;
import com.davidvignon.todoc.utils.LiveDataTestUtils;
import com.davidvignon.todoc.utils.TestExecutor;
import com.davidvignon.todoc.ui.tasks.TasksListViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class TasksViewModelUnitTest {

    private static final int DEFAULT_PROJECT_COUNT = 3;
    private static final String DEFAULT_PROJECT_NAME = "projectName";

    private static final String DEFAULT_TASK_DESCRIPTION = "taskDescription";
    private static final int DEFAULT_TASK_COUNT = 3;


    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    TaskRepository taskRepository;

    private MutableLiveData<List<ProjectWithTask>> projectWithTaskMutableLiveData = new MutableLiveData<>();
    private TasksListViewModel viewModel;
    private final Executor ioExecutor = Mockito.spy(new TestExecutor());


    @Before
    public void setUp(){
        projectWithTaskMutableLiveData.setValue(getDefaultProjectWithTasks());
        given(taskRepository.getAllProjectsWithTasks()).willReturn(projectWithTaskMutableLiveData);
        viewModel = new TasksListViewModel(taskRepository, ioExecutor);
        Mockito.verify(taskRepository).getAllProjectsWithTasks();
    }

    @Test
    public void initialCase(){
        // Given
        projectWithTaskMutableLiveData.setValue(new ArrayList<>());

        // When
        List<TasksViewStateItem> tasksViewStateItems = LiveDataTestUtils.getValueForTesting(viewModel.getTasksViewStateItemsLiveData());

        // Then
        assertEquals(Collections.singletonList(new TasksViewStateItem.EmptyState()), tasksViewStateItems);

    }
    @Test
    public void nominalCase(){

    }

    @Test
    public void sort_project_alphabetical(){

    }

    @Test
    public void sort_project_alphabetical_reverse(){

    }

    @Test
    public void sort_project_by_date_recent_first(){

    }

    @Test
    public void sort_project_by_date_older_first(){

    }

    @Test
    public void delete_task(){

    }

    public List<ProjectWithTask> getDefaultProjectWithTasks(){
        List<ProjectWithTask> projectWithTask = new ArrayList<>();
        int taskId = 0;

        for (long i = 0; i < DEFAULT_PROJECT_COUNT; i++) {
            Project project = new Project(i, DEFAULT_PROJECT_NAME+ i, (int) i);
            List<Task> tasks = new ArrayList<>();

            for (int j = 0; j < DEFAULT_TASK_COUNT; j++) {
                taskId++;

                tasks.add(
                    new Task(
                        taskId,
                        i,
                        DEFAULT_TASK_DESCRIPTION + taskId,
                        LocalDateTime.now().toString()
                    )
                );
            }

            projectWithTask.add(
                new ProjectWithTask(
                    project,
                    tasks
                )
            );
        }

        return projectWithTask;
    }
}

