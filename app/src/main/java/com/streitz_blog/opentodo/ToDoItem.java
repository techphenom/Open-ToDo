package com.streitz_blog.opentodo;

import java.util.List;

/**
 * Created by Russell Streitz on 12/30/17.
 * A To Do item class which represents the data model to be
 * displayed by the RecyclerView.
 */

class ToDoItem {

    private String mCompleted;
    private String mPriority;
    private String mCompletion;
    private String mCreation;
    private String mDescription;
    private List<String> mTags;
    private List<String> mContext;

    public ToDoItem(String mCompleted, String mPriority, String mCompletion, String mCreation, String mDescription, List<String> mTags, List<String> mContext) {
        this.mCompleted = mCompleted;
        this.mPriority = mPriority;
        this.mCompletion = mCompletion;
        this.mCreation = mCreation;
        this.mDescription = mDescription;
        this.mTags = mTags;
        this.mContext = mContext;
    }

    public String getmCompleted() {
        return mCompleted;
    }

    public String getmPriority() {
        return mPriority;
    }

    public String getmCompletion() {
        return mCompletion;
    }

    public String getmCreation() {
        return mCreation;
    }

    public String getmDescription() {
        return mDescription;
    }

    public List<String> getmTags() {
        return mTags;
    }

    public List<String> getmContext() {
        return mContext;
    }

    public void completeTodo() {
        mCompleted = "x";
    }


    @Override
    public String toString() {
        StringBuilder todo = new StringBuilder();
        if (mCompleted != null) {
            todo.append(mCompleted);
            todo.append(" ");
        }
        if (mPriority != null) {
            todo.append(mPriority);
            todo.append(" ");
        }
        if (mCompletion != null) {
            todo.append(mCompletion);
            todo.append(" ");
        }
        if (mCreation != null) {
            todo.append(mCreation);
            todo.append(" ");
        }
        if (mDescription != null) {
            todo.append(mDescription);
            todo.append(" ");
        };

        return todo.toString().trim();
    }
}
