package com.davidvignon.todoc.ui;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.davidvignon.todoc.data.ProjectWithTask;
import com.davidvignon.todoc.data.SortingType;
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

    TaskRepository taskRepository = Mockito.mock(TaskRepository.class);
    private final Executor ioExecutor = Mockito.spy(new TestExecutor());

    private MutableLiveData<List<ProjectWithTask>> projectWithTaskMutableLiveData = new MutableLiveData<>();
    private TasksListViewModel viewModel;


    @Before
    public void setUp() {
        projectWithTaskMutableLiveData.setValue(getDefaultProjectWithTasks());
        given(taskRepository.getAllProjectsWithTasks()).willReturn(projectWithTaskMutableLiveData);
        viewModel = new TasksListViewModel(taskRepository, ioExecutor);
        Mockito.verify(taskRepository).getAllProjectsWithTasks();
    }

    @Test
    public void initialCase() {
        // Given
        projectWithTaskMutableLiveData.setValue(new ArrayList<>());

        // When
        List<TasksViewStateItem> tasksViewStateItems = LiveDataTestUtils.getValueForTesting(viewModel.getTasksViewStateItemsLiveData());

        // Then
        assertEquals(Collections.singletonList(new TasksViewStateItem.EmptyState()), tasksViewStateItems);

    }

    @Test
    public void nominalCase() {
        // Given
        List<TasksViewStateItem> tasksViewStateItems = LiveDataTestUtils.getValueForTesting(viewModel.getTasksViewStateItemsLiveData());
        // Then
        assertEquals(getDefaultTaskViewStates(), tasksViewStateItems);

        Mockito.verify(ioExecutor, Mockito.never()).execute(any());
        Mockito.verifyNoMoreInteractions(taskRepository, ioExecutor);

    }

    @Test
    public void test_deleteTask() {
        // Given
        long taskId = 42;

        // When
        viewModel.onDeleteViewModelClicked(42);

        // Then
        Mockito.verify(taskRepository).deleteTask(42);
        Mockito.verifyNoMoreInteractions(taskRepository);
    }

    @Test
    public void noProject() {
        // Given
        projectWithTaskMutableLiveData.setValue(new ArrayList<>());

        // When
        List<TasksViewStateItem> tasksViewStateItems = LiveDataTestUtils.getValueForTesting(viewModel.getTasksViewStateItemsLiveData());

        // Then
        assertEquals(Collections.singletonList(new TasksViewStateItem.EmptyState()), tasksViewStateItems);

    }

    @Test
    public void noSorting() {
        // When
        List<TasksViewStateItem> tasksViewStateItems = LiveDataTestUtils.getValueForTesting(viewModel.getTasksViewStateItemsLiveData());

        // Then
        assertEquals(getDefaultTaskViewStates(), tasksViewStateItems);
    }

    @Test
    public void sort_project_alphabetical() {
        viewModel.sortList(SortingType.ALPHABETICAL);

        // When
        List<TasksViewStateItem> tasksViewStateItems = LiveDataTestUtils.getValueForTesting(viewModel.getTasksViewStateItemsLiveData());

        // Then
        assertEquals(getDefaultTaskViewStates(), tasksViewStateItems);

    }

    @Test
    public void sort_project_alphabetical_reverse() {
        // Given
        viewModel.sortList(SortingType.ALPHABETICAL_INVERTED);

        // When
        List<TasksViewStateItem> tasksViewStateItems = LiveDataTestUtils.getValueForTesting(viewModel.getTasksViewStateItemsLiveData());

        // Then
        assertEquals(getTasksForAlphabeticalInvertedSorting(), tasksViewStateItems);
    }

    @Test
    public void sort_project_recent_first() {
        // Given
        viewModel.sortList(SortingType.RECENT_FIRST);

        // When
        List<TasksViewStateItem> tasksViewStateItems = LiveDataTestUtils.getValueForTesting(viewModel.getTasksViewStateItemsLiveData());

        // Then
        assertEquals(getTasksForAlphabeticalInvertedSorting(), tasksViewStateItems);
    }

    @Test
    public void sort_project_older_first() {
        // Given
        viewModel.sortList(SortingType.OLD_FIRST);

        // When
        List<TasksViewStateItem> tasksViewStateItems = LiveDataTestUtils.getValueForTesting(viewModel.getTasksViewStateItemsLiveData());

        // Then
        assertEquals(getDefaultTaskViewStates(), tasksViewStateItems);
    }

    // region IN
    @NonNull
    public List<ProjectWithTask> getDefaultProjectWithTasks() {
        List<ProjectWithTask> projectWithTask = new ArrayList<>();
        int taskId = 0;

        for (long i = 0; i <= DEFAULT_PROJECT_COUNT; i++) {
            Project project = new Project(i, DEFAULT_PROJECT_NAME + i, (int) i);
            List<Task> tasks = new ArrayList<>();
                taskId++;

                tasks.add(
                    new Task(
                        taskId,
                        i,
                        DEFAULT_TASK_DESCRIPTION + taskId
                    )
                );

            projectWithTask.add(
                new ProjectWithTask(
                    project,
                    tasks
                )
            );
        }

        return projectWithTask;
    }

    // endregion IN

    // region OUT
    @NonNull
    private List<TasksViewStateItem.Task> getDefaultTaskViewStates() {
        List<TasksViewStateItem.Task> tasksViewStateItems = new ArrayList<>();

        int taskId = 0;

        for (int i = 0; i <= DEFAULT_TASK_COUNT; i++) {
                taskId++;

                tasksViewStateItems.add(
                    new TasksViewStateItem.Task(
                        taskId,
                        DEFAULT_PROJECT_NAME + i,
                        i,
                        DEFAULT_TASK_DESCRIPTION + taskId
                    )
                );
        }
        return tasksViewStateItems;
    }

    @NonNull
    private List<TasksViewStateItem> getTasksForAlphabeticalInvertedSorting(){
        List<TasksViewStateItem> tasksViewStateItems = new ArrayList<>();

        int taskId = 4;

        for (int i = 0; i <= DEFAULT_TASK_COUNT ; i++) {
            int taskIdMinusOne = taskId - 1;

            tasksViewStateItems.add(
                    new TasksViewStateItem.Task(
                        taskId,
                        DEFAULT_PROJECT_NAME + taskIdMinusOne,
                        taskIdMinusOne,
                        DEFAULT_TASK_DESCRIPTION + taskId
                    )
                );
            taskId--;


        }
        return tasksViewStateItems;
    }
    // endregion OUT
}

