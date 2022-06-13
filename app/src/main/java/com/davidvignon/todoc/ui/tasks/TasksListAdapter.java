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
import com.davidvignon.todoc.data.task.TaskRepository;
import com.davidvignon.todoc.ui.OnTaskClickedListener;

public class TasksListAdapter extends ListAdapter<TasksViewStateItem, TasksListAdapter.ViewHolder> {

    private final OnTaskClickedListener listener;


    public TasksListAdapter(OnTaskClickedListener listener) {
        super(new ViewHolder.ListTaskItemCallBack());
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task,
            parent,
            false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position), listener);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ProjectRepository projectRepository;

        private final AppCompatImageView projectColor;
        private final ImageView deleteImage;
        private final TextView taskDescription;
        private final TextView projectName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            projectColor = itemView.findViewById(R.id.task_item_iv_color);
            deleteImage = itemView.findViewById(R.id.task_item_iv_delete);
            taskDescription = itemView.findViewById(R.id.task_item_tv_description);
            projectName = itemView.findViewById(R.id.task_item_tv_project_name);
        }

        @SuppressLint("RestrictedApi")
        public void bind(TasksViewStateItem item, OnTaskClickedListener listener) {

            final Project taskProject = item.getProject();

            projectColor.setSupportImageTintList(ColorStateList.valueOf(taskProject.getColor()));
            taskDescription.setText(item.getName());
            projectName.setText(taskProject.getName());
            deleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDeleteTaskClicked(item.getId());
                }
            });


        }

        private static class ListTaskItemCallBack extends DiffUtil.ItemCallback<TasksViewStateItem>{
            @Override
            public boolean areItemsTheSame(@NonNull TasksViewStateItem oldItem, @NonNull TasksViewStateItem newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull TasksViewStateItem oldItem, @NonNull TasksViewStateItem newItem) {
                return oldItem.equals(newItem);
            }
        }
    }
}
