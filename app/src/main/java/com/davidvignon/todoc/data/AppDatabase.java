package com.davidvignon.todoc.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.davidvignon.todoc.BuildConfig;
import com.davidvignon.todoc.R;
import com.davidvignon.todoc.data.converters.LocalDateConverter;
import com.davidvignon.todoc.data.dao.ProjectDao;
import com.davidvignon.todoc.data.dao.TaskDao;
import com.davidvignon.todoc.data.project.Project;
import com.davidvignon.todoc.data.task.Task;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.concurrent.Executor;

@Database(
        entities = {
                Task.class,
                Project.class
        },
        version = 1,
        exportSchema = false
)
@TypeConverters({LocalDateConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "app_database";

    private static AppDatabase sInstance;

    public static AppDatabase getInstance(
            @NonNull Application application,
            @NonNull Executor ioExecutor
    ) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = create(application, ioExecutor);
                }
            }
        }
        return sInstance;
    }

    private static AppDatabase create(
            @NonNull Application application,
            @NonNull Executor ioExecutor
    ) {
        Builder<AppDatabase> builder = Room.databaseBuilder(
                application,
                AppDatabase.class,
                DATABASE_NAME
        );

        builder.addCallback(new Callback() {
            @Override
            public void onCreate(@NonNull @NotNull SupportSQLiteDatabase db) {
                ioExecutor.execute(() -> {
                    ProjectDao projectDao = AppDatabase.getInstance(application, ioExecutor).getProjectDao();


                    projectDao.insert(new Project(1L, application.getString(R.string.tartampion_project), 0xFFEADAD1));
                    projectDao.insert(new Project(2L, application.getString(R.string.lucidia_project), 0xFFB4CDBA));
                    projectDao.insert(new Project(3L, application.getString(R.string.circus_project), 0xFFA3CED2));
                });
                if (BuildConfig.DEBUG) {
                    ioExecutor.execute(() -> {
                        TaskDao taskDao = AppDatabase.getInstance(application, ioExecutor).getTaskDao();

                        taskDao.insert(new Task(1, 1L, application.getString(R.string.dishes)));
                        taskDao.insert(new Task(2, 2L, application.getString(R.string.mop)));
                        taskDao.insert(new Task(3, 3L, application.getString(R.string.iron)));
                        taskDao.insert(new Task(4, 1L, "Coucou"));
                    });
                }
            }
        });
        return builder.build();
    }

    public abstract TaskDao getTaskDao();

    public abstract ProjectDao getProjectDao();
}
