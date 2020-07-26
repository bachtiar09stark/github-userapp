package com.ngoding.githubuserapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import com.ngoding.githubuserapp.R;
import com.ngoding.githubuserapp.notification.AlarmReceiver;
import com.ngoding.githubuserapp.notification.Preferences;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    AlarmReceiver alarmReceiver;
    Preferences notificationPreference;
    SharedPreferences spAlarmReminder;
    SharedPreferences.Editor edtAlarmReminder;

    String TYPE_DAILY = "reminderDaily";
    String DAILY_REMINDER = "dailyReminder";
    String KEY_DAILY_REMINDER = "Daily";

    String timeDaily = "09:00";
    private SwitchCompat swDaily;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        alarmReceiver = new AlarmReceiver();
        notificationPreference = new Preferences(this);
        LinearLayout selectLanguage = findViewById(R.id.select_language);
        selectLanguage.setOnClickListener(this);
        swDaily = findViewById(R.id.sw_daily);
        setPreference();
        setDailyAlarm();
    }

    private void setPreference() {
        spAlarmReminder = getSharedPreferences(DAILY_REMINDER, MODE_PRIVATE);
        boolean checkAlarmReminder = spAlarmReminder.getBoolean(KEY_DAILY_REMINDER, false);
        swDaily.setChecked(checkAlarmReminder);
    }

    private void setDailyAlarm() {
        edtAlarmReminder = spAlarmReminder.edit();
        swDaily.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                edtAlarmReminder.putBoolean(KEY_DAILY_REMINDER, true);
                edtAlarmReminder.apply();
                dailyAlarmOn();
            } else {
                edtAlarmReminder.putBoolean(KEY_DAILY_REMINDER, false);
                edtAlarmReminder.commit();
                dailyAlarmOff();
            }
        });
    }

    private void dailyAlarmOff() {
        alarmReceiver.cancelNotification(SettingsActivity.this);
        Toast.makeText(SettingsActivity.this, R.string.reminder_off, Toast.LENGTH_SHORT).show();
    }

    private void dailyAlarmOn() {
        String message = getResources().getString(R.string.daily_reminder_message);
        notificationPreference.setTimeDaily(timeDaily);
        notificationPreference.setDailyMessage(message);
        alarmReceiver.setAlarm(SettingsActivity.this, TYPE_DAILY, timeDaily, message);
        Toast.makeText(SettingsActivity.this, R.string.reminder_on, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.select_language) {
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
        }
    }
}