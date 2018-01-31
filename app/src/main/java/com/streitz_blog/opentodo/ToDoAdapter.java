package com.streitz_blog.opentodo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Russell Streitz on 1/9/18.
 * Adapter for the RecyclerView
 */

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder> {
    private static final String TAG = "ToDoAdapter";

    private ArrayList<ToDoItem> mTodos;
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

    public ToDoAdapter(Context context, ArrayList<ToDoItem> todos) {
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
        View toDoView = inflater.inflate(R.layout.to_do_item, parent, false);
        return new ToDoViewHolder(toDoView);
    }

    @Override
    public void onBindViewHolder(ToDoViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: starts");
        final int pos = position;

        ToDoItem todo = mTodos.get(pos);
        TextView textView = holder.toDoDescription;
        textView.setText(todo.getmDescription());
        CheckBox checkBox = holder.checkBox;
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "You clicked checkbox at " + pos, Toast.LENGTH_SHORT).show();
                mTodos.get(pos).completeTodo();
                DataHandling.updateFile(MainActivity.location, mTodos);
                ((MainActivity) view.getContext()).onResume();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTodos.size();
    }
}