package com.streitz_blog.opentodo;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
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

    public static String getData(File todoTxtLocation) {
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
                createFile(todoTxtLocation);
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
            Pattern todoPattern = Pattern.compile("(x)?\\s?(\\([A-Z]\\))?\\s?(\\d{4}-\\d{2}-\\d{2}\\s?)?\\s?(\\d{4}-\\d{2}-\\d{2}\\s?)?(.*)");
            Pattern tagPattern = Pattern.compile("\\s\\+([\\S]+)");                      // Finds any tags within the description
            Pattern contextPattern = Pattern.compile("\\s@([\\S]+)");                    // Finds any context tags within the description

            String[] lines = text.split(System.getProperty("line.separator"));
            for (int i = 0; i < lines.length; i++) {
                String Completed = null;
                String Priority = null;
                String Completion = null;
                String Creation = null;
                String Description = null;
                ArrayList<String> tags = new ArrayList<>();
                ArrayList<String> context = new ArrayList<>();

                Matcher match = todoPattern.matcher(lines[i]);

                if (match.find()) {
                    Completed = match.group(1);
                    Log.d(TAG, "completedMatcher = " + Completed);

                    Priority = match.group(2);
                    Log.d(TAG, "PriorityMatcher = " + Priority);

                    //Find the dates and assign them to the right place.
                    if (match.group(4) == null && match.group(3) != null)
                        Creation = match.group(3);
                    else {
                        Completion = match.group(3);
                        Creation = match.group(4);
                    }
                    Log.d(TAG, "Dates = " + Completion + " and " + Creation);

                    Description = match.group(5);
                    Log.d(TAG, "Description = " + Description);
                } else {
                    Log.d(TAG, "--- Invalid todo.txt format ---");
                }

                // Finds tags and adds them to the List 'tags'
                Matcher tagMatch = tagPattern.matcher(lines[i]);
                while (tagMatch.find()) {
                    tags.add(tagMatch.group(1));
                }
                Log.d(TAG, "tags = " + tags.toString());

                // Finds context tag in description
                Matcher contextMatch = contextPattern.matcher(lines[i]);
                while (contextMatch.find())
                    context.add(contextMatch.group(1));
                Log.d(TAG, "contexts = " + context.toString());

                todos.add(new ToDoItem(Completed, Priority, Completion, Creation, Description, tags, context));
            }
        }
        return todos;
    }

    private static void createFile(File location) {
        try {
            FileWriter fileWriter = new FileWriter(location);
            fileWriter.write("Add some todos!!!");
            fileWriter.close();
        } catch (FileNotFoundException e) {
            Log.d(TAG, "Didn't write file");
        } catch (IOException err) {
            Log.d(TAG, "getData: didn't write file");
        }
    }
}
