package com.streitz_blog.opentodo;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by Russell Streitz on 2/8/18.
 * Adapter for Completed To Do RecyclerView.
 */

public class CompletedAdapter extends RecyclerView.Adapter<CompletedAdapter.ToDoViewHolder> {
    private static final String TAG = "CompletedAdapter";

    private DataHandling mTodos;
    private Context mContext;

    public class ToDoViewHolder extends RecyclerView.ViewHolder {
        public CheckBox checkBox;
        public TextView toDoDescription;

        public ToDoViewHolder(View toDoView) {
            super(toDoView);

            checkBox = itemView.findViewById(R.id.completedCheckbox);
            toDoDescription = itemView.findViewById(R.id.completedToDoText);
        }
    }

    public CompletedAdapter(Context context, DataHandling todos) {
        mContext = context;
        mTodos = todos;
    }

    private Context getContext() {
        return mContext;
    }

    @Override
    public ToDoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: new view requested");
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View toDoView = inflater.inflate(R.layout.completed_to_do, parent, false);
        return new ToDoViewHolder(toDoView);
    }

    @Override
    public void onBindViewHolder(ToDoViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: starts");
        Log.d(TAG, "onBindViewHolder: getItemCount" + getItemCount());
        Log.d(TAG, "onBindViewHolder position is " + position);
        ToDoItem todo = mTodos.getCompleted().get(position);
        TextView textView = holder.toDoDescription;
        textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        textView.setText(todo.getmDescription());
        CheckBox checkBox = holder.checkBox;
    }

    @Override
    public int getItemCount() { return mTodos.getCompleted().size(); }
}
