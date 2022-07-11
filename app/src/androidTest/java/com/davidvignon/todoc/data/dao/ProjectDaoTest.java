package com.davidvignon.todoc.data.dao;

import android.content.Context;
import android.database.sqlite.SQLiteException;

import androidx.room.Room;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.davidvignon.todoc.data.AppDatabase;
import com.davidvignon.todoc.data.project.Project;
import com.davidvignon.todoc.utils.LiveDataTestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class ProjectDaoTest {

    private static final long EXPECTED_PROJECT_ID_1 = 1;
    private static final String EXPECTED_PROJECT_NAME_1 = "EXPECTED_PROJECT_NAME_1";
    private static final int EXPECTED_PROJECT_COLOR_1 = 1;
    private static final long EXPECTED_PROJECT_ID_2 = 2;
    private static final String EXPECTED_PROJECT_NAME_2 = "EXPECTED_PROJECT_NAME_2";
    private static final int EXPECTED_PROJECT_COLOR_2 = 2;

    private AppDatabase appDatabase;
    private ProjectDao projectDao;

    @Rule
    public InstantTaskExecutor instantTaskExecutor = new InstantTaskExecutor();

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        appDatabase = Room
            .inMemoryDatabaseBuilder(context, AppDatabase.class)
            .build();
        projectDao = appDatabase.getProjectDao();
    }

    @After
    public void closeDb() {
        appDatabase.close();
    }

    @Test
    public void insert_one() {
        // Given
        Project project = new Project(EXPECTED_PROJECT_ID_1, EXPECTED_PROJECT_NAME_1, EXPECTED_PROJECT_COLOR_1);

        // When
        projectDao.insert(project);
        List<Project> results = LiveDataTestUtils.getValueForTesting(projectDao.getAllProjects());

        // Then
        assertEquals(
            Collections.singletonList(
                new Project(EXPECTED_PROJECT_ID_1, EXPECTED_PROJECT_NAME_1, EXPECTED_PROJECT_COLOR_1)
            ),
            results
        );
    }

    @Test
    public void insert_two() {
        // Given
        Project project = new Project(EXPECTED_PROJECT_ID_1, EXPECTED_PROJECT_NAME_1, EXPECTED_PROJECT_COLOR_1);
        Project project2 = new Project(EXPECTED_PROJECT_ID_2, EXPECTED_PROJECT_NAME_2, EXPECTED_PROJECT_COLOR_2);

        // When
        projectDao.insert(project);
        projectDao.insert(project2);
        List<Project> results = LiveDataTestUtils.getValueForTesting(projectDao.getAllProjects());

        // Then
        assertEquals(
            Arrays.asList(
                new Project(EXPECTED_PROJECT_ID_1, EXPECTED_PROJECT_NAME_1, EXPECTED_PROJECT_COLOR_1),
                new Project(EXPECTED_PROJECT_ID_2, EXPECTED_PROJECT_NAME_2, EXPECTED_PROJECT_COLOR_2)
            ),
            results
        );
    }
}

