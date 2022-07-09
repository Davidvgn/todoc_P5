package com.davidvignon.todoc.ui.add;

import android.app.Application;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.davidvignon.todoc.data.project.Project;
import com.davidvignon.todoc.data.project.ProjectRepository;
import com.davidvignon.todoc.data.task.TaskRepository;

import com.davidvignon.todoc.ui.utils.MainThreadExecutor;
import com.davidvignon.todoc.utils.LiveDataTestUtils;
import com.davidvignon.todoc.utils.TestExecutor;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import static org.junit.Assert.assertEquals;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class AddTaskViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final TaskRepository taskRepository = Mockito.mock(TaskRepository.class);
    private final ProjectRepository projectRepository = Mockito.mock(ProjectRepository.class);
    private final Application application = Mockito.mock(Application.class);

    private final MainThreadExecutor mainThreadExecutor = Mockito.mock(MainThreadExecutor.class);
    private final Executor ioExecutor = Mockito.spy(new TestExecutor());

    private final MutableLiveData<List<Project>> projectMutableLiveData = new MutableLiveData<>();

    private AddTaskViewModel viewModel;

    @Before
    public void setUp() {
        Mockito.doReturn(projectMutableLiveData).when(projectRepository).getAllProjects();

        projectMutableLiveData.setValue(getDefaultProjects());
        viewModel = new AddTaskViewModel(application, taskRepository, projectRepository, mainThreadExecutor, ioExecutor);
        verify(projectRepository).getAllProjects();
    }

    @Test
    public void nominalCase(){
        // Given
        String name = null;

        // When
        AddTaskViewState addTaskViewState = LiveDataTestUtils.getValueForTesting(viewModel.getAddTaskViewStateLiveData());

        // Then
        assertEquals(new AddTaskViewState(getDefaultProjects(),name), addTaskViewState);
        Mockito.verify(ioExecutor, Mockito.never()).execute(any());
    }


    @Test
    public void addTask() {
        // Given
        long projectId = 1L;
        String taskDescription = "Learn";

        // When
        viewModel.onAddButtonClicked(projectId, taskDescription);

        // Then
        verify(taskRepository).addTask(projectId, taskDescription);
        Mockito.verify(ioExecutor).execute(any());

    }

    @Test
    public void task_not_added_if_taskDescription_is_empty() {
        // Given
        long projectId = 1L;
        String taskDescription = "";

        // When
        viewModel.onAddButtonClicked(projectId, taskDescription);

        // Then
        verify(taskRepository, never()).addTask(projectId, taskDescription);
        Mockito.verify(ioExecutor).execute(any());

    }

    // region IN
    private List<Project> getDefaultProjects() {
        List<Project> projects = new ArrayList<>();

        projects.add(new Project(1L, "Tartampion", 0xFFEADAD1));
        projects.add(new Project(2L, "Lucida", 0xFFB4CDBA));
        projects.add(new Project(3L, "Circus", 0xFFA3CED2));

        return projects;
    }
    // regionend IN
}
