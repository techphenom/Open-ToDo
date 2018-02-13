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

/**
 * Created by Russell Streitz on 1/9/18.
 * Adapter for the RecyclerView
 */

public class IncompleteAdapter extends RecyclerView.Adapter<IncompleteAdapter.ToDoViewHolder> {
    private static final String TAG = "IncompleteAdapter";

    private DataHandling mTodos;
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

    public IncompleteAdapter(Context context, DataHandling todos) {
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
        View toDoView = inflater.inflate(R.layout.incomplete_to_do, parent, false);
        return new ToDoViewHolder(toDoView);
    }

    @Override
    public void onBindViewHolder(ToDoViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: starts");
        Log.d(TAG, "onBindViewHolder context is " + getContext().toString());
        final int pos = holder.getAdapterPosition();
        final ToDoItem todo;

        todo = mTodos.getIncomplete().get(pos);
        TextView textView = holder.toDoDescription;
        textView.setText(todo.getmDescription());
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "You clicked todo at " + pos, Toast.LENGTH_SHORT).show();
            }
        });
        CheckBox checkBox = holder.checkBox;
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "You clicked checkbox at " + pos, Toast.LENGTH_SHORT).show();
                mTodos.getIncomplete().get(pos).completeTodo();
                mTodos.updateFile(MainActivity.location);
                ((MainActivity) view.getContext()).onResume();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mTodos.getIncomplete().size();
    }
}