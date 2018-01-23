package com.streitz_blog.opentodo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
        final EditText editProject = findViewById(R.id.editProject);
        final EditText editContext = findViewById(R.id.editContext);

        Button save = findViewById(R.id.buttonSave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                creation = new SimpleDateFormat("yyyy-mm-dd", Locale.getDefault()).format(new Date());
                description = editDescription.getText().toString();
                StringBuilder newTodo = new StringBuilder(creation + " " + description + " ");

                for (String x: editProject.toString().split(",")
                     ) {
                    newTodo.append(" +" + x);
                }
                for (String c: editContext.toString().split(",")
                        ) {
                    newTodo.append(" @" + c);
                }


                try{
                    FileOutputStream fileOutputStream = new FileOutputStream(MainActivity.location, true);
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                    objectOutputStream.writeBytes(newTodo.toString());
                    objectOutputStream.close();
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
