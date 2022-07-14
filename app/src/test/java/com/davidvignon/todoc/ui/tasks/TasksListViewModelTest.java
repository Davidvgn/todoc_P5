package com.davidvignon.todoc.ui.tasks;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.davidvignon.todoc.data.ProjectWithTask;
import com.davidvignon.todoc.data.SortingType;
import com.davidvignon.todoc.data.project.Project;
import com.davidvignon.todoc.data.task.Task;
import com.davidvignon.todoc.data.task.TaskRepository;
import com.davidvignon.todoc.utils.LiveDataTestUtils;
import com.davidvignon.todoc.utils.TestExecutor;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
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
public class TasksListViewModelTest {

    private static final int DEFAULT_PROJECT_COUNT = 3;
    private static final String DEFAULT_PROJECT_NAME = "projectName";

    private static final String DEFAULT_TASK_DESCRIPTION = "taskDescription";
    private static final int DEFAULT_TASK_COUNT = 3;


    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final TaskRepository taskRepository = Mockito.mock(TaskRepository.class);
    private final Executor ioExecutor = Mockito.spy(new TestExecutor());

    private final MutableLiveData<List<ProjectWithTask>> projectWithTaskMutableLiveData = new MutableLiveData<>();
    private TasksListViewModel viewModel;


    @Before
    public void setUp() {
        projectWithTaskMutableLiveData.setValue(getDefaultProjectWithTasks());
        given(taskRepository.getAllProjectsWithTasks()).willReturn(projectWithTaskMutableLiveData);
        viewModel = new TasksListViewModel(taskRepository, ioExecutor);
        Mockito.verify(taskRepository).getAllProjectsWithTasks();
    }

    @Test
    public void nominalCase() {
        // When
        List<TasksViewStateItem> tasksViewStateItems = LiveDataTestUtils.getValueForTesting(viewModel.getTasksViewStateItemsLiveData());

        // Then
        assertEquals(getDefaultTaskViewStates(), tasksViewStateItems);

        Mockito.verify(ioExecutor, Mockito.never()).execute(any());
        Mockito.verifyNoMoreInteractions(taskRepository, ioExecutor);

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
    public void test_deleteTask() {
        // Given
        long taskId = 42;

        // When
        viewModel.onDeleteViewModelClicked(taskId);

        // Then
        Mockito.verify(taskRepository).deleteTask(taskId);
        Mockito.verifyNoMoreInteractions(taskRepository);
    }

    @Test
    public void sort_project_alphabetical() {
        // Given
        viewModel.sortList(SortingType.ALPHABETICAL);

        // When
        List<TasksViewStateItem> tasksViewStateItems = LiveDataTestUtils.getValueForTesting(viewModel.getTasksViewStateItemsLiveData());

        // Then
        assertEquals(getTasksInAlphabeticalSorting(), tasksViewStateItems);
    }

    @Test
    public void sort_project_alphabetical_reverse() {
        // Given
        viewModel.sortList(SortingType.ALPHABETICAL_INVERTED);

        // When
        List<TasksViewStateItem> tasksViewStateItems = LiveDataTestUtils.getValueForTesting(viewModel.getTasksViewStateItemsLiveData());

        // Then
        assertEquals(getTasksInAlphabeticalInvertedSorting(), tasksViewStateItems);
    }

    @Test
    public void sort_project_recent_first() {
        // Given
        viewModel.sortList(SortingType.RECENT_FIRST);

        // When
        List<TasksViewStateItem> tasksViewStateItems = LiveDataTestUtils.getValueForTesting(viewModel.getTasksViewStateItemsLiveData());

        // Then
        assertEquals(getTasksRecentFirst(), tasksViewStateItems);
    }

    @Test
    public void sort_project_older_first() {
        // Given
        viewModel.sortList(SortingType.OLD_FIRST);

        // When
        List<TasksViewStateItem> tasksViewStateItems = LiveDataTestUtils.getValueForTesting(viewModel.getTasksViewStateItemsLiveData());

        // Then
        assertEquals(getTasksOlderFirst(), tasksViewStateItems);
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
    private List<TasksViewStateItem.Task> getTasksInAlphabeticalSorting() {
        List<TasksViewStateItem.Task> tasksViewStateItems = new ArrayList<>();
        tasksViewStateItems.add(0,new TasksViewStateItem.Task(1,"projectName0",0,"taskDescription1"));
        tasksViewStateItems.add(1,new TasksViewStateItem.Task(2,"projectName1",1,"taskDescription2"));
        tasksViewStateItems.add(2,new TasksViewStateItem.Task(3,"projectName2",2,"taskDescription3"));
        tasksViewStateItems.add(3,new TasksViewStateItem.Task(4,"projectName3",3,"taskDescription4"));

        return tasksViewStateItems;
    }

    @NonNull
    private List<TasksViewStateItem.Task> getTasksInAlphabeticalInvertedSorting(){
        List<TasksViewStateItem.Task> tasksViewStateItems = new ArrayList<>();
        tasksViewStateItems.add(0,new TasksViewStateItem.Task(4,"projectName3",3,"taskDescription4"));
        tasksViewStateItems.add(1,new TasksViewStateItem.Task(3,"projectName2",2,"taskDescription3"));
        tasksViewStateItems.add(2,new TasksViewStateItem.Task(2,"projectName1",1,"taskDescription2"));
        tasksViewStateItems.add(3,new TasksViewStateItem.Task(1,"projectName0",0,"taskDescription1"));

        return tasksViewStateItems;
    }

    @NonNull
    private List<TasksViewStateItem.Task> getTasksRecentFirst(){
        List<TasksViewStateItem.Task> tasksViewStateItems = new ArrayList<>();
        tasksViewStateItems.add(0,new TasksViewStateItem.Task(4,"projectName3",3,"taskDescription4"));
        tasksViewStateItems.add(1,new TasksViewStateItem.Task(3,"projectName2",2,"taskDescription3"));
        tasksViewStateItems.add(2,new TasksViewStateItem.Task(2,"projectName1",1,"taskDescription2"));
        tasksViewStateItems.add(3,new TasksViewStateItem.Task(1,"projectName0",0,"taskDescription1"));

        return tasksViewStateItems;
    }

    @NonNull
    private List<TasksViewStateItem.Task> getTasksOlderFirst(){
        List<TasksViewStateItem.Task> tasksViewStateItems = new ArrayList<>();
        tasksViewStateItems.add(0,new TasksViewStateItem.Task(1,"projectName0",0,"taskDescription1"));
        tasksViewStateItems.add(1,new TasksViewStateItem.Task(2,"projectName1",1,"taskDescription2"));
        tasksViewStateItems.add(2,new TasksViewStateItem.Task(3,"projectName2",2,"taskDescription3"));
        tasksViewStateItems.add(3,new TasksViewStateItem.Task(4,"projectName3",3,"taskDescription4"));

        return tasksViewStateItems;
    }
    // endregion OUT
}

