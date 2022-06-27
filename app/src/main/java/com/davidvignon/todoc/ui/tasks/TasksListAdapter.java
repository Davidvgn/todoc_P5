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
import com.davidvignon.todoc.data.project.Project;
import com.davidvignon.todoc.data.project.ProjectRepository;
import com.davidvignon.todoc.databinding.TaskEmptyStateItemBinding;
import com.davidvignon.todoc.ui.OnTaskClickedListener;

import org.jetbrains.annotations.NotNull;

public class TasksListAdapter extends ListAdapter<TasksViewStateItem, RecyclerView.ViewHolder> {
    @NonNull
    private final OnTaskClickedListener listener;


    public TasksListAdapter(OnTaskClickedListener listener) {
        super(new ListTaskItemCallBack());
        this.listener = listener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (TasksViewStateItem.Type.values()[viewType]) {
            case TASK:
                return new TaskViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task,
                    parent, false)
                );
            case EMPTY_STATE:
                return new RecyclerView.ViewHolder(
                    TaskEmptyStateItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false).getRoot()) {};
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

        private ProjectRepository projectRepository;

        private final AppCompatImageView projectColor;
        private final ImageView deleteImage;
        private final TextView taskDescription;
        private final TextView projectName;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            projectColor = itemView.findViewById(R.id.task_item_iv_color);
            deleteImage = itemView.findViewById(R.id.task_item_iv_delete);
            taskDescription = itemView.findViewById(R.id.task_item_tv_description);
            projectName = itemView.findViewById(R.id.task_item_tv_project_name);
        }

        @SuppressLint("RestrictedApi")
        public void bind(TasksViewStateItem.Task item, OnTaskClickedListener listener) {
            projectColor.setSupportImageTintList(ColorStateList.valueOf(item.getProjectColor()));
            projectName.setText(item.getProjectName());
            taskDescription.setText(item.getTaskDescription());
            deleteImage.setOnClickListener(new View.OnClickListener() {
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