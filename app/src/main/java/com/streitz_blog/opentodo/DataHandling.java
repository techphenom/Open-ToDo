package com.streitz_blog.opentodo;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
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

    public static String getData(File todoTxtLocation, Context context) {
        Log.d(TAG, "DataHandling.getData: starts. todo.txt location = '" + todoTxtLocation + "'");
        StringBuilder text = new StringBuilder();

        if (todoTxtLocation != null) {

                try {
                    FileInputStream todoFile = new FileInputStream(todoTxtLocation);
                    BufferedReader br = new BufferedReader(new InputStreamReader(todoFile));
                    String line;

                    while ((line = br.readLine()) != null) {
                        text.append(line);
                        text.append('\n');
                    }
                    br.close();
                } catch (FileNotFoundException er) {
                    Log.d(TAG, er.toString());
                    createFile(todoTxtLocation,context);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        return text.toString();
    }

    public static List<ToDoItem> parseData(String text) {
        Log.d(TAG, "parseData: starts");
        List<ToDoItem> todos = new ArrayList<ToDoItem>();

        if (!text.isEmpty()) {
            Pattern completedPattern = Pattern.compile("^x");                       // Finds if x starts the line
            Pattern priorityPattern1 = Pattern.compile("^x\\s\\(([A-Z])\\)\\s");    // Finds if there is priority after x
            Pattern priorityPattern2 = Pattern.compile("^\\(([A-Z])\\)\\s");        // Finds if there is priority at the start of the line
            Pattern datePattern = Pattern.compile("\\s(\\d{4}-\\d\\d-\\d\\d)");     // Finds all the dates
            Pattern descriptionPattern = Pattern.compile("x?\\s?(\\([A-Z]\\))?\\s?(\\d{4}-\\d{2}-\\d{2}\\s?){0,2}(.*)");
//            Pattern descriptionPattern = Pattern.compile("(.*)");
            Pattern tagPattern = Pattern.compile("(\\+[\\S]+)");                      // Finds any tags within the description
            Pattern contextPattern = Pattern.compile("(@[\\S]+)");                    // Finds any context tags within the description

            String[] lines = text.split(System.getProperty("line.separator"));
            for (int i = 0; i < lines.length; i++) {
                boolean Completed;
                String Priority = null;
                String Completion = null;
                String Creation = null;
                String Description;
                ArrayList<String> tags = new ArrayList<>();
                String context = null;

                if (completedPattern.matcher(lines[i]).find()) Completed = true;
                else Completed = false;
                Log.d(TAG, "completedMatcher = " + Completed);

                // Finds if there is Priority in the t0do and then assigns it to Priority variable.
                Matcher priorityMatch1 = priorityPattern1.matcher(lines[i]);
                Matcher priorityMatch2 = priorityPattern2.matcher(lines[i]);

                if (priorityMatch1.find())
                    Priority = priorityMatch1.group(1);
                else if (priorityMatch2.find())
                    Priority = priorityMatch2.group(1);
                Log.d(TAG, "priority = " + Priority);

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
                Log.d(TAG, "Dates = " + Completion + " and " + Creation);

                // Pull out the description
                Log.d(TAG, "line is " + lines[i].toString());
                Matcher descriptionMatch = descriptionPattern.matcher(lines[i]);
                if (descriptionMatch.find()) Description = descriptionMatch.group(3);
                else Description = "--Invalid todo.txt format--";
                Log.d(TAG, "Description = " + Description);

                // Finds tags and adds them to the List 'tags'
                Matcher tagMatch = tagPattern.matcher(lines[i]);
                while (tagMatch.find()) {
                    tags.add(tagMatch.group(1));
                }
                Log.d(TAG, "tags = " + tags.toString());

                // Finds context tag in description
                Matcher contextMatch = contextPattern.matcher(lines[i]);
                if (contextMatch.find())
                    context = contextMatch.group(1);

                todos.add(new ToDoItem(Completed, Priority, Completion, Creation, Description, tags, context));
            }
        }
        return todos;
    }

    public static void createFile(File location, Context context) {
        try{
            FileOutputStream fileOutputStream = new FileOutputStream(location);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject("Add some todos!");
            objectOutputStream.close();
            Log.d(TAG, "getData: wrote file");
        } catch (FileNotFoundException e) {
            Log.d(TAG, "Didn't write file");
        } catch (IOException err) {
            Log.d(TAG, "getData: didn't write file");
        }
    }
}
