package com.davidvignon.todoc.data.dao;

import android.content.Context;
import android.database.sqlite.SQLiteException;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.davidvignon.todoc.data.AppDatabase;
import com.davidvignon.todoc.data.ProjectWithTask;
import com.davidvignon.todoc.data.project.Project;
import com.davidvignon.todoc.data.task.Task;
import com.davidvignon.todoc.utils.LiveDataTestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TaskDaoTest {

    private static final long TASK_ID = 1;
    private static final long TASK_ID_2 = 2;
    private static final long TASK_ID_3 = 3;

    private static final long EXPECTED_PROJECT_ID_1 = 1;
    private static final String EXPECTED_PROJECT_NAME_1 = "EXPECTED_PROJECT_NAME_1";
    private static final int EXPECTED_PROJECT_COLOR_1 = 1;

    private static final long EXPECTED_PROJECT_ID_2 = 2;
    private static final String EXPECTED_PROJECT_NAME_2 = "EXPECTED_PROJECT_NAME_2";
    private static final int EXPECTED_PROJECT_COLOR_2 = 2;

    private static final long EXPECTED_TASK_PROJECT_ID_1 = EXPECTED_PROJECT_ID_1;
    private static final String EXPECTED_TASK_DESCRIPTION_1 = "EXPECTED_TASK_DESCRIPTION_1";

    private static final long EXPECTED_TASK_PROJECT_ID_2 = EXPECTED_PROJECT_ID_1;
    private static final String EXPECTED_TASK_DESCRIPTION_2 = "EXPECTED_TASK_DESCRIPTION_2";

    private static final long EXPECTED_TASK_PROJECT_ID_3 = EXPECTED_PROJECT_ID_2;
    private static final String EXPECTED_TASK_DESCRIPTION_3 = "EXPECTED_TASK_DESCRIPTION_3";


    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private AppDatabase appDatabase;
    private TaskDao taskDao;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        appDatabase = Room
            .inMemoryDatabaseBuilder(context, AppDatabase.class)
            .build();
        taskDao = appDatabase.getTaskDao();

        appDatabase.getProjectDao().insert(getFirstProject());
    }
    @After
    public void closeDb() {
        appDatabase.close();
    }

    @Test
    public void insert_one() {
        // Given
        Task task = new Task(TASK_ID, EXPECTED_TASK_PROJECT_ID_1, EXPECTED_TASK_DESCRIPTION_1);

        // When
        long taskId = taskDao.insert(task);
        List<ProjectWithTask> results = LiveDataTestUtils.getValueForTesting(taskDao.getAllProjectsWithTasks());

        // Then
        assertEquals(1, taskId);
        assertEquals(
            Collections.singletonList(
                new ProjectWithTask(
                    getFirstProject(),
                    Collections.singletonList(
                        new Task(
                            TASK_ID,
                            EXPECTED_PROJECT_ID_1,
                            EXPECTED_TASK_DESCRIPTION_1
                        )
                    )
                )
            ),
            results
        );
    }

    @Test
    public void insert_two() {
        // Given
        Task task = new Task(TASK_ID, EXPECTED_TASK_PROJECT_ID_1, EXPECTED_TASK_DESCRIPTION_1);
        Task task2 = new Task(TASK_ID_2, EXPECTED_TASK_PROJECT_ID_2, EXPECTED_TASK_DESCRIPTION_2);

        // When
        long taskId = taskDao.insert(task);
        long taskId2 = taskDao.insert(task2);
        List<ProjectWithTask> results = LiveDataTestUtils.getValueForTesting(taskDao.getAllProjectsWithTasks());

        // Then
        assertEquals(1, taskId);
        assertEquals(2, taskId2);
        assertEquals(Collections.singletonList(
                new ProjectWithTask(
                    getFirstProject(),
                    Arrays.asList(
                        new Task(
                            TASK_ID,
                            EXPECTED_TASK_PROJECT_ID_1,
                            EXPECTED_TASK_DESCRIPTION_1
                        ),
                        new Task(
                            TASK_ID_2,
                            EXPECTED_TASK_PROJECT_ID_2,
                            EXPECTED_TASK_DESCRIPTION_2
                        )
                    )
                )
            ),
            results
        );
    }

    @Test
    public void insert_three_with_2_projects() {
        // Given
        Task task = new Task(TASK_ID, EXPECTED_TASK_PROJECT_ID_1, EXPECTED_TASK_DESCRIPTION_1);
        Task task2 = new Task(TASK_ID_2, EXPECTED_TASK_PROJECT_ID_2, EXPECTED_TASK_DESCRIPTION_2);
        Task task3 = new Task(TASK_ID_3, EXPECTED_TASK_PROJECT_ID_3, EXPECTED_TASK_DESCRIPTION_3);

        // When
        appDatabase.getProjectDao().insert(getSecondProject());
        long taskId = taskDao.insert(task);
        long taskId2 = taskDao.insert(task2);
        long taskId3 = taskDao.insert(task3);
        List<ProjectWithTask> results = LiveDataTestUtils.getValueForTesting(taskDao.getAllProjectsWithTasks());

        // Then
        assertEquals(1, taskId);
        assertEquals(2, taskId2);
        assertEquals(3, taskId3);
        assertEquals(
            Arrays.asList(
                new ProjectWithTask(
                    getFirstProject(),
                    Arrays.asList(
                        new Task(
                            TASK_ID,
                            EXPECTED_TASK_PROJECT_ID_1,
                            EXPECTED_TASK_DESCRIPTION_1
                        ),
                        new Task(
                            TASK_ID_2,
                            EXPECTED_TASK_PROJECT_ID_2,
                            EXPECTED_TASK_DESCRIPTION_2
                        )
                    )
                ),
                new ProjectWithTask(
                    getSecondProject(),
                    Collections.singletonList(
                        new Task(
                            TASK_ID_3,
                            EXPECTED_TASK_PROJECT_ID_3,
                            EXPECTED_TASK_DESCRIPTION_3
                        )
                    )
                )
            ),
            results
        );
    }

    @Test(expected = SQLiteException.class)
    public void should_throw_with_incorrect_project_foreign_key() {
        Task task = new Task(1,4, EXPECTED_TASK_DESCRIPTION_1);
        taskDao.insert(task);
    }

    @Test
    public void insert_and_delete() {
        // Given
        Task task = new Task(TASK_ID, EXPECTED_TASK_PROJECT_ID_1, EXPECTED_TASK_DESCRIPTION_1);

        // When
        taskDao.insert(task);
        taskDao.deleteTask(1);
        List<ProjectWithTask> results = LiveDataTestUtils.getValueForTesting(taskDao.getAllProjectsWithTasks());

        // Then
        assertEquals(
            Collections.singletonList(
                new ProjectWithTask(
                    getFirstProject(),
                    new ArrayList<>()
                )
            ),
            results
        );
    }

    @Test
    public void insert_two_and_delete_one() {
        // Given
        Task task = new Task(TASK_ID, EXPECTED_TASK_PROJECT_ID_1, EXPECTED_TASK_DESCRIPTION_1);
        Task task2 = new Task(TASK_ID_2, EXPECTED_TASK_PROJECT_ID_2, EXPECTED_TASK_DESCRIPTION_2);

        // When
        taskDao.insert(task);
        taskDao.insert(task2);
        taskDao.deleteTask(1);
        List<ProjectWithTask> results = LiveDataTestUtils.getValueForTesting(taskDao.getAllProjectsWithTasks());

        // Then
        assertEquals(
            Collections.singletonList(
                new ProjectWithTask(
                    getFirstProject(),
                    Collections.singletonList(
                        new Task(
                            2,
                            EXPECTED_TASK_PROJECT_ID_2,
                            EXPECTED_TASK_DESCRIPTION_2
                        )
                    )
                )
            ),
            results
        );
    }

    @Test
    public void should_return_0_when_nothing_is_deleted() {
        // When
        int deletedCount = taskDao.deleteTask(1);

        // Then
        assertEquals(0, deletedCount);
    }

    // region IN
    private Project getFirstProject() {
        return new Project(EXPECTED_PROJECT_ID_1, EXPECTED_PROJECT_NAME_1, EXPECTED_PROJECT_COLOR_1);
    }

    private Project getSecondProject() {
        return new Project(EXPECTED_PROJECT_ID_2, EXPECTED_PROJECT_NAME_2, EXPECTED_PROJECT_COLOR_2);
    }
    // endregion IN
}
