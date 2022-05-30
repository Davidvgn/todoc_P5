package com.davidvignon.todoc.ui.add;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.davidvignon.todoc.R;
import com.davidvignon.todoc.ViewModelFactory;

import org.jetbrains.annotations.NotNull;

public class AddTaskDialogFragment extends DialogFragment {

    public static DialogFragment newInstance() {
        AddTaskDialogFragment addTaskDialogFragment = new AddTaskDialogFragment();
        return addTaskDialogFragment;
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();

        builder.setTitle(R.string.add_task);
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

        viewModel.getAddTaskViewStateLiveData().observe(this, new Observer<AddTaskViewState>() {
            @Override
            public void onChanged(AddTaskViewState addTaskViewState) {
                dialogEditText.setText(addTaskViewState.getName());
//                 dialogSpinner.(addTaskViewState.getProject());
            }
        });
        return view;
    }



    }
