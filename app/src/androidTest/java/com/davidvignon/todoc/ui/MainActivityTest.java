package com.davidvignon.todoc.ui;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.davidvignon.todoc.R;
import com.davidvignon.todoc.utils.DeleteViewAction;
import com.davidvignon.todoc.utils.RecyclerViewMatcher;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.davidvignon.todoc.utils.RecyclerViewItemCountAssertion.withItemCount;
import static com.davidvignon.todoc.utils.Waiter.waitFor;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    public MainActivity mainActivity;

    @Before
    public void setUp() {
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
        activityScenario.onActivity(activity -> mainActivity = activity);
    }

    @Test
    public void open_dialog_no_description_added_show_error() {

        onView(ViewMatchers.withId(R.id.main_fab_add)).perform(click());

        onView(allOf(withId(R.id.task_Rv),
            isDisplayed()));

        onView(withHint("Ajouter"))
            .perform(click());

        pressBack();

        onView(isRoot())
            .perform(waitFor(1000));
        onView(allOf(withId(R.id.task_Rv),
            isDisplayed()))
            .check(withItemCount(1));
        //TODO verify EmptyState
    }

    @Test
    public void addTasks() {
        createTask("Something to do", "Projet Tartampion");

        onView(isRoot())
            .perform(waitFor(1000));
        onView(allOf(withId(R.id.task_Rv),
            isDisplayed()))
            .check(withItemCount(1));
    }

    @Test
    public void add2Tasks() {
        createTask("Something to do", "Projet Tartampion");
        createTask("Something to do again", "Projet Lucidia");

        onView(isRoot())
            .perform(waitFor(1000));
        onView(allOf(withId(R.id.task_Rv),
            isDisplayed()))
            .check(withItemCount(2));
    }

    @Test
    public void deleteTask() {
        createTask("Something to do", "Projet Tartampion");
        createTask("Something to do again", "Projet Lucidia");


        onView(isRoot())
            .perform(waitFor(1000));
        onView(allOf(withId(R.id.task_Rv),
            isDisplayed()))
            .check(withItemCount(2));

        onView(allOf(withId(R.id.task_Rv),
            isDisplayed()))
            .perform(RecyclerViewActions
                .actionOnItemAtPosition(0, new DeleteViewAction()));
        onView(isRoot())
            .perform(waitFor(1000));

        onView(allOf(withId(R.id.task_Rv),
            isDisplayed()))
            .check(withItemCount(1));// 1 because of the emptyState
    }

    @Test
    public void delete1TaskOn2() {
        createTask("Something to do", "Projet Tartampion");

        onView(isRoot())
            .perform(waitFor(1000));
        onView(allOf(withId(R.id.task_Rv),
            isDisplayed()))
            .check(withItemCount(1));

        onView(allOf(withId(R.id.task_Rv),
            isDisplayed()))
            .perform(RecyclerViewActions
                .actionOnItemAtPosition(0, new DeleteViewAction()));
        onView(isRoot())
            .perform(waitFor(1000));

        onView(allOf(withId(R.id.task_Rv),
            isDisplayed()))
            .check(withItemCount(1));// 1 because of the emptyState
    }

    @Test
    public void sortAlphabetically() {
        createTask("Something to do", "Projet Tartampion");
        createTask("Something to do", "Projet Circus");
        createTask("Something to do", "Projet Lucidia");

        onView(withId(R.id.action_filter)).perform(click());
        onView(isRoot())
            .perform(waitFor(1000));

        onView(withText(R.string.sort_alphabetical))
            .perform(click());
        checkSorting(0, "Projet Circus");
        checkSorting(1, "Projet Lucidia");
        checkSorting(2, "Projet Tartampion");

    }

    @Test
    public void sortAlphabeticallyInverted() {
        createTask("Something to do", "Projet Tartampion");
        createTask("Something to do", "Projet Circus");
        createTask("Something to do", "Projet Lucidia");

        onView(withId(R.id.action_filter)).perform(click());
        onView(isRoot())
            .perform(waitFor(1000));

        onView(withText(R.string.sort_alphabetical_invert))
            .perform(click());
        checkSorting(0, "Projet Tartampion");
        checkSorting(1, "Projet Lucidia");
        checkSorting(2, "Projet Circus");
    }

    @Test
    public void sortRecentFirst() {
        createTask("Something to do", "Projet Tartampion");
        createTask("Something to do", "Projet Circus");
        createTask("Something to do", "Projet Lucidia");

        onView(withId(R.id.action_filter)).perform(click());
        onView(isRoot())
            .perform(waitFor(1000));

        onView(withText(R.string.sort_recent_first))
            .perform(click());
        checkSorting(0, "Projet Lucidia");
        checkSorting(1, "Projet Circus");
        checkSorting(2, "Projet Tartampion");
    }

    @Test
    public void sortOlderFirst() {
        createTask("Something to do", "Projet Tartampion");
        createTask("Something to do", "Projet Circus");
        createTask("Something to do", "Projet Lucidia");

        onView(withId(R.id.action_filter)).perform(click());
        onView(isRoot())
            .perform(waitFor(1000));

        onView(withText(R.string.sort_oldest_first))
            .perform(click());
        checkSorting(0, "Projet Tartampion");
        checkSorting(1, "Projet Circus");
        checkSorting(2, "Projet Lucidia");
    }

    private void checkSorting(int position, String projectName) {
        onView(new RecyclerViewMatcher(R.id.task_Rv)
            .atPositionOnView(position, R.id.task_item_tv_project_name))
            .check(matches(withText(projectName)));
    }

    public void createTask(String taskDescription, String projectNameToClickOn) {
        onView(withId(R.id.main_fab_add)).perform(click());
        onView(withId(R.id.dialog_et_task_name))
            .perform(click(),
                replaceText(taskDescription));
        onView(withId(R.id.dialog_project_spinner))
            .perform(click());
        onView(withText(projectNameToClickOn)).inRoot(isPlatformPopup()).perform(click());
        onView(withHint("Ajouter"))
            .perform(click());
    }
}

