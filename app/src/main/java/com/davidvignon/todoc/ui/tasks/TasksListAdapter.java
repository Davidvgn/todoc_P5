package com.davidvignon.todoc.ui.tasks;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.davidvignon.todoc.R;
import com.davidvignon.todoc.databinding.ItemTaskBinding;
import com.davidvignon.todoc.databinding.TaskEmptyStateItemBinding;
import com.davidvignon.todoc.ui.OnTaskClickedListener;

import org.jetbrains.annotations.NotNull;

public class TasksListAdapter extends ListAdapter<TasksViewStateItem, RecyclerView.ViewHolder> {
    @NonNull
    private final OnTaskClickedListener listener;

    public TasksListAdapter(@NonNull OnTaskClickedListener listener) {
        super(new ListTaskItemCallBack());
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (TasksViewStateItem.Type.values()[viewType]) {
            case TASK:
                return new TaskViewHolder(
                        ItemTaskBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
                );
            case EMPTY_STATE:
                return new RecyclerView.ViewHolder(
                        TaskEmptyStateItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false).getRoot()
                ) {};
        }
        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TaskViewHolder) {
            ((TaskViewHolder) holder).bind((TasksViewStateItem.Task) getItem(position), listener);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getType().ordinal();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {

        private final ItemTaskBinding binding;

        public TaskViewHolder(@NonNull ItemTaskBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }

        @SuppressLint("RestrictedApi")
        public void bind(TasksViewStateItem.Task item, OnTaskClickedListener listener) {
            binding.taskItemIvColor.setSupportImageTintList(ColorStateList.valueOf(item.getProjectColor()));
            binding.taskItemTvProjectName.setText(item.getProjectName());
            binding.taskItemTvDescription.setText(item.getTaskDescription());
            binding.taskItemIvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDeleteTaskClicked(item.getTaskId());
                }
            });
        }
    }

    public static class ListTaskItemCallBack extends DiffUtil.ItemCallback<TasksViewStateItem> {
        @Override
        public boolean areItemsTheSame(@NonNull TasksViewStateItem oldItem, @NonNull TasksViewStateItem newItem) {
            return (
                oldItem instanceof TasksViewStateItem.Task && newItem instanceof TasksViewStateItem.Task
                    && ((TasksViewStateItem.Task) oldItem).getTaskId() == ((TasksViewStateItem.Task) newItem).getTaskId()
            ) || (
                oldItem instanceof TasksViewStateItem.EmptyState && newItem instanceof TasksViewStateItem.EmptyState
            );

        }

        @Override
        public boolean areContentsTheSame(@NonNull TasksViewStateItem oldItem, @NonNull TasksViewStateItem newItem) {
            return oldItem.equals(newItem);
        }
    }
}