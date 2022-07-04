package com.davidvignon.todoc.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.davidvignon.todoc.data.dao.ProjectDao;
import com.davidvignon.todoc.data.project.Project;
import com.davidvignon.todoc.data.project.ProjectRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ProjectRepositoryTest {

    private final ProjectDao projectDao = Mockito.mock(ProjectDao.class);

    ProjectRepository projectRepository;

    @Before
    public void setup() {
        projectRepository = new ProjectRepository(projectDao);
    }

    @Test
    public void test_getAllProjects() {
        // Given
        LiveData<List<Project>> projects = Mockito.spy(new
            MutableLiveData<>());
        Mockito.doReturn(projects).when(projectDao).getAllProjects();

        // When
        LiveData<List<Project>> projectList = projectRepository.getAllProjects();

        // Then
        assertEquals(projects, projectList);
        Mockito.verify(projectDao).getAllProjects();
        Mockito.verifyNoMoreInteractions(projectDao);

    }
}
