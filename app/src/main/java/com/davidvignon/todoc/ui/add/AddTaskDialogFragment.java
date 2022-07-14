package com.davidvignon.todoc.ui.add;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.davidvignon.todoc.R;
import com.davidvignon.todoc.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class AddTaskDialogFragment extends DialogFragment {

    public static DialogFragment newInstance() {
        return new AddTaskDialogFragment();
    }

    @SuppressLint("InflateParams")
    @Override
    @NonNull
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.dialog_add_task, null));
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AddTaskViewModel viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(AddTaskViewModel.class);

        final View view = inflater.inflate(R.layout.dialog_add_task, container, false);

        EditText dialogEditText = view.findViewById(R.id.dialog_et_task_name);
        Spinner dialogSpinner = view.findViewById(R.id.dialog_project_spinner);
        Button addTaskButton = view.findViewById(R.id.dialog_button);


        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.onAddButtonClicked(
                    dialogSpinner.getSelectedItemId() + 1,
                    dialogEditText.getText().toString()
                );
                viewModel.isCancelled.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        dismiss();
                    }
                });
            }
        });

        viewModel.getAddTaskViewStateLiveData().observe(this, new Observer<AddTaskViewState>() {
            @Override
            public void onChanged(AddTaskViewState addTaskViewState) {
                int[] projectListSize = new int[addTaskViewState.getProjects().size()];
                List<String> projectNameList = new ArrayList<>();

                for (int i = 0; i < projectListSize.length; i++) {
                    projectNameList.add(addTaskViewState.getProjects().get(i).getName());
                }

                ArrayAdapter adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, projectNameList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dialogSpinner.setAdapter(adapter);

                dialogEditText.setError(addTaskViewState.getNameError());
            }
        });

        return view;
    }
}
