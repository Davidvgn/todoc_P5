package com.davidvignon.todoc.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.davidvignon.todoc.R;
import com.davidvignon.todoc.databinding.ActivityMainBinding;
import com.davidvignon.todoc.ui.add.AddTaskDialogFragment;
import com.davidvignon.todoc.ui.tasks.TasksListFragment;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        setSupportActionBar(binding.mainToolbar);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, TasksListFragment.newInstance())
                .commitNow();
        }

        binding.mainFabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = AddTaskDialogFragment.newInstance();
                dialogFragment.show(MainActivity.this.getSupportFragmentManager(), "Add new Task");
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actions, menu);
        return true;
    }

}