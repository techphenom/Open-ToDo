package com.streitz_blog.opentodo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AddEditActivity extends AppCompatActivity {
    private static final String TAG = "AddEditActivity";

    private String creation;
    private String description;
    private ArrayList<String> projects = new ArrayList<>();
    private ArrayList<String> contexts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        final EditText editDescription = findViewById(R.id.editDescription);
        final EditText editProject = (EditText) findViewById(R.id.editProject);
        final EditText editContext = findViewById(R.id.editContext);

        Button save = findViewById(R.id.buttonSave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                creation = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                description = editDescription.getText().toString();
                StringBuilder newTodo = new StringBuilder("\n"+ creation + " " + description);

                for (String x: editProject.getText().toString().split(",")
                     ) {
                    if (x.length() > 0)newTodo.append(" +" + x.trim());
                }
                for (String c: editContext.getText().toString().split(",")
                        ) {
                    if (c.length() > 0) newTodo.append(" @" + c.trim());
                }


                try{
                    FileWriter fileWriter = new FileWriter(MainActivity.location, true);
                    Log.d(TAG, "stringbuilder is " + newTodo);
                    fileWriter.write(newTodo.toString());
                    fileWriter.close();
                } catch (FileNotFoundException e) {
                    Log.d(TAG, "Didn't write file");
                } catch (IOException err) {
                    Log.d(TAG, "getData: didn't write file");
                }
                finish();
            }
        });

    }
}
