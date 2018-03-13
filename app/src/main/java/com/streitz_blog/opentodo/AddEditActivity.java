package com.streitz_blog.opentodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AddEditActivity extends AppCompatActivity {
    private static final String TAG = "AddEditActivity";

    private String creation;
    private String description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        Intent intent = getIntent();

        final EditText editDescription = findViewById(R.id.editDescription);
        final EditText editProject = findViewById(R.id.editProject);
        final EditText editContext = findViewById(R.id.editContext);

        Button save = findViewById(R.id.buttonSave);

        if (intent.getIntExtra("position", -1) >= 0) {
            final int position = intent.getIntExtra("position", 0);
            editDescription.setText(MainActivity.toDoData.getIncomplete().get(position).getmDescription());

            if (!MainActivity.toDoData.getAllTodos().get(position).getmTags().isEmpty()) {
                StringBuilder projectSb = new StringBuilder();
                for (String x : MainActivity.toDoData.getAllTodos().get(position).getmTags()
                        ) {
                    projectSb.append(x + ",");
                }
                projectSb.deleteCharAt(projectSb.length()-1);
                editProject.setText(projectSb);
            }
            if (!MainActivity.toDoData.getAllTodos().get(position).getmContext().isEmpty()) {
                StringBuilder contextSb = new StringBuilder();
                for (String c : MainActivity.toDoData.getAllTodos().get(position).getmContext()
                        ) {
                    contextSb.append(c + ",");
                }
                contextSb.deleteCharAt(contextSb.length() - 1);
                editContext.setText(contextSb);
            }
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    creation = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                    description = editDescription.getText().toString();
                    ArrayList<String> projectTags = new ArrayList<>();
                    ArrayList<String> contextTags = new ArrayList<>();

                    for (String x : editProject.getText().toString().split(",")
                            ) {
                        if (!x.isEmpty())
                            projectTags.add(x.trim());
                    }
                    for (String c : editContext.getText().toString().split(",")
                            ) {
                        if (!c.isEmpty())
                            contextTags.add(c.trim());
                    }

                    ToDoItem newTodo = new ToDoItem(null, null, null, creation, description, projectTags, contextTags);
                    Log.d(TAG, "position equals =" + position);
                    MainActivity.toDoData.editOldToDo(newTodo, position);
                    MainActivity.toDoData.updateFile(MainActivity.location);

                    finish();
                }
            });
        } else {
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    creation = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                    description = editDescription.getText().toString();
                    ArrayList<String> projectTags = new ArrayList<>();
                    ArrayList<String> contextTags = new ArrayList<>();

                    for (String x : editProject.getText().toString().split(",")
                            ) {
                        if (!x.isEmpty())
                            projectTags.add(x.trim());
                    }
                    for (String c : editContext.getText().toString().split(",")
                            ) {
                        if (!c.isEmpty())
                        contextTags.add(c.trim());
                    }

                    ToDoItem newTodo = new ToDoItem(null, null, null, creation, description, projectTags, contextTags);
                    MainActivity.toDoData.addNewToDo(newTodo);
                    MainActivity.toDoData.updateFile(MainActivity.location);

                    finish();
                }
            });
        }

    }
}
