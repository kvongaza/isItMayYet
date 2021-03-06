package com.c301t3.c301t3app;
/**
 * Custom adapter for viewing Tasks in the MainMenuActivity to display each Task's name,
 * description, status, and price
 * Based off of
 * https://developer.android.com/samples/RecyclerView/src/com.example.android.recyclerview/
 * CustomAdapter.html
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class TasksRequestedAdapter extends RecyclerView.Adapter<TasksRequestedAdapter.TaskHolder> {
    private ArrayList<Task> tasks;
    private Context context;
    private int position;

    private View.OnLongClickListener longClickListener;
    private OnItemClickListener clickListener;

    /**
     * Adapter constructor
     * @param context Context for activity
     * @param tasks ArrayList of Task objects
     */
    public TasksRequestedAdapter(Context context, ArrayList<Task> tasks) {
        this.tasks = tasks;
        this.context = context;
    }

    @Override
    public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_item,parent,false);
        return new TaskHolder(this.context, view);
    }

    @Override
    public void onBindViewHolder(final TaskHolder holder, final int pos) {

        // get task and place to holder
        final Task task = this.tasks.get(pos);
        holder.bindTask(task);

        // set pos on item click
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
                public boolean onLongClick(View v) {
                position = pos;
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {return this.tasks.size();}

    public Task getItem(int position) {return tasks.get(position);}

    public void removeItem(int position) {
        tasks.remove(position);
        notifyItemRemoved(position);
    }

    public void setPosition(int pos) { this.position = pos; }

    public int getPosition() { return this.position; }

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.clickListener = listener;
    }

    public void setOnLongClickListener(View.OnLongClickListener listener) {
        this.longClickListener = listener;
    }

    /**
     * Class for handling each task to be viewed
     */
    public class TaskHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        public final View taskItemView;
        private final TextView taskNameView;
        private final TextView taskStatusView;
        private final TextView taskDescriptionView;
        private final TextView taskPriceView;
        private final TextView taskOwnerView;

        private Task task;
        private Context context;

        /**
         * Constructor for TaskHolder
         * @param context Context for activity
         * @param itemView View for activity
         */
        public TaskHolder(final Context context, View itemView) {
            super(itemView);

            this.context = context;

            this.taskItemView = itemView;
            this.taskNameView = itemView.findViewById(R.id.task_name);
            this.taskStatusView = itemView.findViewById(R.id.task_status);
            this.taskDescriptionView = itemView.findViewById(R.id.task_desc);
            this.taskPriceView = itemView.findViewById(R.id.task_price);
            this.taskOwnerView = itemView.findViewById(R.id.task_owner);

            itemView.setOnLongClickListener(this); //currently no function

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener!=null) {
                        int position = getAdapterPosition();
                        if (position!=RecyclerView.NO_POSITION) {
                            clickListener.onItemClick(v, position);
                        }
                    }
                }
            });

            taskOwnerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO: bring user to owner profile
                    Intent ownerProfileIntent = new Intent(context,TaskOwnerProfile.class);
                    String owner = taskOwnerView.getText().toString();
                    ownerProfileIntent.putExtra("ownerName",owner);
                    context.startActivity(ownerProfileIntent);

                }
            });
        }

        @Override
        public boolean onLongClick(View v) { //TODO: add function
            longClickListener.onLongClick(v);
            return false;
        }

        /**
         * Binds a task to a single task view, setting each field in the view to contain
         * an aspect of the task
         * @param task Task object to be viewed
         */
        public void bindTask(Task task) {
            String taskName = task.getName();
            String taskStatus = task.getStatus().toString();
            String taskDescription = task.getDescription();
            float taskPrice = task.getPrice();
            String taskPriceFormat = String.format("$%.2f",taskPrice);
            String taskOwner = task.getOwnerName();
            String taskMBid = task.getMinBid();


            this.taskNameView.setText(taskName);
            this.taskStatusView.setText(taskStatus);
            this.taskDescriptionView.setText(taskDescription);
            this.taskPriceView.setText(taskMBid + " / " + taskPriceFormat);
            this.taskOwnerView.setText(taskOwner);
        }

    }

}
