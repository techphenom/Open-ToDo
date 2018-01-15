package com.streitz_blog.opentodo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Russell Streitz on 1/9/18.
 * Adapter for the RecyclerView
 */

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder> {
    private static final String TAG = "ToDoAdapter";

    private List<ToDoItem> mToDos;
    private Context mContext;

    public class ToDoViewHolder extends RecyclerView.ViewHolder {
        public CheckBox checkBox;
        public TextView toDoDescription;

        public ToDoViewHolder(View toDoView) {
            super(toDoView);

            checkBox = itemView.findViewById(R.id.checkBox);
            toDoDescription = itemView.findViewById(R.id.toDoDescription);
        }
    }

    public ToDoAdapter(Context context, List<ToDoItem> todos) {
        mContext = context;
        mToDos = todos;
    }

    private Context getContext() {
        return mContext;
    }

    @Override
    public ToDoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: new view requested");
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View toDoView = inflater.inflate(R.layout.to_do_item, parent, false);
        return new ToDoViewHolder(toDoView);
    }

    @Override
    public void onBindViewHolder(ToDoViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: starts");

        ToDoItem todo = mToDos.get(position);
        TextView textView = holder.toDoDescription;
        textView.setText(todo.getmDescription());
        CheckBox checkBox = holder.checkBox;
    }

    @Override
    public int getItemCount() {
        return mToDos.size();
    }
}
