package com.davidvignon.todoc.ui;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.davidvignon.todoc.R;
import com.davidvignon.todoc.utils.DeleteViewAction;
import com.davidvignon.todoc.utils.RecyclerViewMatcher;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
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

    //The 3 tasks create in AppDatabase must be present.

    @Before
    public void setUp(){
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
        activityScenario.onActivity(activity -> mainActivity = activity);
    }

    @Test
    public void open_dialog_no_description_added_show_error() {

        onView(withId(R.id.main_fab_add)).perform(click());

        onView(allOf(withId(R.id.task_Rv), isDisplayed()));

        onView(withHint("Ajouter"))
            .perform(click());

        pressBack();

        onView(isRoot()).perform(waitFor(1000));
        onView(allOf(withId(R.id.task_Rv), isDisplayed())).check(withItemCount(1));
        // TODO DAVID VERIFY EMPTY STATE
    }

    @Test
    public void addTasks() {
        onView(withId(R.id.main_fab_add)).perform(click());
        onView(withId(R.id.dialog_et_task_name))
            .perform(click(),
                replaceText("Something to do"));
        onView(withId(R.id.dialog_project_spinner))
            .perform(click());
        onView(withText("Projet Tartampion")).inRoot(isPlatformPopup()).perform(click());
        onView(withHint("Ajouter"))
            .perform(click());

        onView(allOf(withId(R.id.task_Rv),
            isDisplayed()))
            .check(withItemCount(1));
    }

    @Test
    public void add2Tasks() {
        onView(withId(R.id.main_fab_add)).perform(click());
        onView(withId(R.id.dialog_et_task_name))
            .perform(click(),
                replaceText("Something to do"));
        onView(withId(R.id.dialog_project_spinner))
            .perform(click());
        onView(withText("Projet Tartampion")).inRoot(isPlatformPopup()).perform(click());
        onView(withHint("Ajouter"))
            .perform(click());

        onView(isRoot()).perform(waitFor(1000));

        onView(withId(R.id.main_fab_add)).perform(click());
        onView(withId(R.id.dialog_et_task_name))
            .perform(click(),
                replaceText("Something else to do"));
        onView(withId(R.id.dialog_project_spinner))
            .perform(click());
        onView(withText("Projet Tartampion")).inRoot(isPlatformPopup()).perform(click());
        onView(withHint("Ajouter"))
            .perform(click());

        onView(isRoot()).perform(waitFor(1000));

        onView(allOf(withId(R.id.task_Rv),
            isDisplayed()))
            .check(withItemCount(2));
    }

    @Test
    public void deleteTask() {

        // TODO DAVID ADD TASK BEFORE

        onView(allOf(withId(R.id.task_Rv),
            isDisplayed()))
            .perform(RecyclerViewActions.actionOnItemAtPosition(0, new DeleteViewAction()));
        onView(isRoot())
            .perform(waitFor(1000));

        onView(allOf(withId(R.id.task_Rv),
            isDisplayed()))
            .check(withItemCount(0));
    }

    // TODO DAVID ADD NEW TEST : ADD 2 TASKS THEN DELETE ONE AND CHECK ONE IS STILL HERE

    @Test
    public void sortAlphabetically() {

        // TODO DAVID ADD TASK BEFORE

        // TODO DAVID CLICK ON MENU
        onView(withText(R.string.sort_alphabetical))
            .inRoot(withDecorView(not(is(mainActivity.getWindow().getDecorView()))))
            .check(matches(isDisplayed()));
        checkSortAlphabetically(0,"Projet Circus");
        checkSortAlphabetically(0,"Projet Lucida");
        checkSortAlphabetically(0,"Projet Tartampion");

    }

    @Ignore
    @Test
    public void sortAlphabeticallyInverted() {
    }

    @Ignore
    @Test
    public void sortRecentFirst() {
    }

    @Ignore
    @Test
    public void sortOlderFirst() {
    }

    private void checkSortAlphabetically(int position, String projectName) {
        onView(new RecyclerViewMatcher(R.id.task_Rv)
            .atPositionOnView(position, R.id.task_item_tv_project_name))
            .check(matches(withText(projectName)));
    }
}

