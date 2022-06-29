package com.davidvignon.todoc.ui.tasks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.davidvignon.todoc.R;
import com.davidvignon.todoc.ViewModelFactory;
import com.davidvignon.todoc.data.SortingType;
import com.davidvignon.todoc.databinding.TasksFragmentBinding;
import com.davidvignon.todoc.ui.OnTaskClickedListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TasksListFragment extends Fragment {

    public static TasksListFragment newInstance() {
        TasksListFragment fragment = new TasksListFragment();
        fragment.setArguments(new Bundle());
        return fragment;
    }

    private TasksListViewModel viewModel;
    private TasksFragmentBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = TasksFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (getArguments() == null) {
            throw new IllegalStateException("Please use MeetingFragment.newInstance() to build the Fragment");
        }

        viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(TasksListViewModel.class);
        TasksListAdapter adapter = new TasksListAdapter(new OnTaskClickedListener() {
            @Override
            public void onDeleteTaskClicked(long taskId) {
                viewModel.onDeleteViewModelClicked(taskId);
            }
        });
        binding.taskRv.setAdapter(adapter);

        viewModel.getTasksViewStateItemsLiveData().observe(getViewLifecycleOwner(), new Observer<List<TasksViewStateItem>>() {
            @Override
            public void onChanged(List<TasksViewStateItem> tasksViewStateItems) {
                adapter.submitList(tasksViewStateItems);
                SortingType sortingType = SortingType.NONE;

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        binding = null;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter_alphabetical:
                viewModel.sortList(SortingType.ALPHABETICAL);
                return true;
            case R.id.filter_alphabetical_inverted:
                viewModel.sortList(SortingType.ALPHABETICAL_INVERTED);
                return true;
            case R.id.filter_oldest_first:
                viewModel.sortList(SortingType.OLD_FIRST);
                return true;
            case R.id.filter_recent_first:
                viewModel.sortList(SortingType.RECENT_FIRST);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
