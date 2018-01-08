package com.streitz_blog.opentodo;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Russell Streitz on 1/4/18.
 * Pull data from t0do.txt file.
 */

public class DataHandling {
    private static final String TAG = "DataHandling";

    //File todoTxtLocation = Environment.getExternalStorageDirectory();

    public String getData(File todoTxtLocation) {
        Log.d(TAG, "DataHandling.getData: starts. todo.txt location = '" + todoTxtLocation + "'");
        StringBuilder text = new StringBuilder();

        if (todoTxtLocation != null) {
            File todoFile = new File(todoTxtLocation, "todo.txt");

            try {
                BufferedReader br = new BufferedReader(new FileReader(todoFile));
                String line;

                while ((line = br.readLine()) != null) {
                    text.append(line);
                    text.append('\n');
                }
                br.close();
            } catch (FileNotFoundException er) {
                Log.d(TAG, "getData: error file not found");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return text.toString();
    }

    public List<ToDoItem> parseData(String text) {
        Log.d(TAG, "parseData: starts");
        List<ToDoItem> todos = new ArrayList<ToDoItem>();

        Pattern completedPattern = Pattern.compile("^x");                       // Finds if x starts the line
        Pattern priorityPattern1 = Pattern.compile("^x\\s\\(([A-Z])\\)\\s");    // Finds if there is priority after x
        Pattern priorityPattern2 = Pattern.compile("^\\(([A-Z])\\)\\s");        // Finds if there is priority at the start of the line
        Pattern datePattern = Pattern.compile("\\s(\\d{4}-\\d\\d-\\d\\d)");     // Finds all the dates
        Pattern descriptionPattern = Pattern.compile("x*\\s?(\\([A-Z]\\))?\\s?(\\d{4}-\\d{2}-\\d{2}\\s){0,2}(.*)");
        Pattern tagPattern = Pattern.compile("\\+[\\S]+");                      // Finds any tags within the description
        Pattern contextPattern = Pattern.compile("@[\\S]+");                    // Finds any context tags within the description

        String[] lines = text.split(System.getProperty("line.separator"));
        for (int i = 0; i < lines.length; i++) {
            boolean Completed;
            String Priority = null;
            String Completion = null;
            String Creation = null;
            String Description;
            List<String> tags = new ArrayList<String>();
            String context = null;

            if (completedPattern.matcher(lines[i]).find()) Completed = true;
            else Completed = false;

            // Finds if there is Priority in the t0do and then assigns it to Priority variable.
            Matcher priorityMatch1 = priorityPattern1.matcher(lines[i]);
            Matcher priorityMatch2 = priorityPattern2.matcher(lines[i]);

            if (priorityMatch1.find())
                Priority = priorityMatch1.group(1);
            else if (priorityMatch2.find())
                Priority = priorityMatch2.group(1);

            // Finds all the dates then assigns them to either Completion, Creation, or exits loop if there is a date in the description.
            Matcher dateMatch = datePattern.matcher(lines[i]);
            while (dateMatch.find()) {
                if (Completion != null & Creation != null) break;
                if (Completion == null & Creation != null) {
                    Completion = Creation;
                    Creation = dateMatch.group(1);
                }
                if (Completion == null & Creation == null)
                    Creation = dateMatch.group(1);
            }

            // Pull out the description
            Matcher descriptionMatch = descriptionPattern.matcher(lines[i]);
            Description = descriptionMatch.group(3);

            // Finds tags and adds them to the List 'tags'
            Matcher tagMatch = tagPattern.matcher(lines[i]);
            while (tagMatch.find()) {
                tags.add(tagMatch.group(1));
            }

            // Finds context tag in description
            Matcher contextMatch = contextPattern.matcher(lines[i]);
            if (contextMatch.find())
                context = contextMatch.group(1);

            todos.add(new ToDoItem(Completed, Priority, Completion, Creation, Description, tags, context));
        }
        return todos;
    }
}
