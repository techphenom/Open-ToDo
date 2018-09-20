package com.streitz_blog.opentodo;

import android.content.Context;
import android.content.Intent;
import android.preference.DialogPreference;
import android.preference.Preference;
import android.util.AttributeSet;


public class FilePickerSettingsPreference extends DialogPreference{
    @Override
    protected void onClick() {
        super.onClick();
        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers,
        // it would be "*/*".
        intent.setType("image/*");

        //startActivityForResult(intent, 42);
    }

    public FilePickerSettingsPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
