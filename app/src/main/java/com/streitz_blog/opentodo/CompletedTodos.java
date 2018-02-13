package com.streitz_blog.opentodo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class CompletedTodos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_todos);

        final RecyclerView rvToDoList = findViewById(R.id.completedList);
        DataHandling toDoData = new DataHandling();

        rvToDoList.setAdapter(new CompletedAdapter(this, toDoData));
        rvToDoList.setLayoutManager(new LinearLayoutManager(this));
    }
}
