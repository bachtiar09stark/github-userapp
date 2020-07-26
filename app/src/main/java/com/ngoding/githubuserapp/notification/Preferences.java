package com.ngoding.githubuserapp.notification;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
    private final static String PREFERENCE = "reminderPreferences";
    private final static String KEY_DAILY = "DailyReminder";
    private final static String KEY_DAILY_MESSAGE = "messageDaily";
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public Preferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setTimeDaily(String time) {
        editor.putString(KEY_DAILY, time);
        editor.commit();
    }

    public void setDailyMessage(String message) {
        editor.putString(KEY_DAILY_MESSAGE, message);
    }
}
