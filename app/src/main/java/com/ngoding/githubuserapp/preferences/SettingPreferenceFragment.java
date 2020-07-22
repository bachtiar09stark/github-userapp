package com.ngoding.githubuserapp.preferences;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.ngoding.githubuserapp.R;
import com.ngoding.githubuserapp.receiver.AlarmReceiver;


public class SettingPreferenceFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    boolean isChecked = false;
    private String REMINDER;
    private SwitchPreference reminderPreference;
    private Preference languagePreference;
    private AlarmReceiver alarmReceiver;

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.preferences);
        init();
        setSummaries();

        languagePreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);
                return true;
            }
        });

        alarmReceiver = new AlarmReceiver();
    }

    private void setSummaries() {
        SharedPreferences sh = getPreferenceManager().getSharedPreferences();
        reminderPreference.setDefaultValue(sh.getBoolean(REMINDER, isChecked));
    }

    private void init() {
        String LANGUAGE = getResources().getString(R.string.key_language);
        REMINDER = getResources().getString(R.string.key_reminder);

        reminderPreference = findPreference(REMINDER);
        languagePreference = findPreference(LANGUAGE);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(REMINDER)) {
            if (reminderPreference.isChecked()) {
                if (getActivity() != null) {
                    alarmReceiver.setRepeatingAlarm(getActivity());
                    isChecked = true;
                }
            } else {
                if (getActivity() != null) {
                    alarmReceiver.cancelRepeatingAlarm(getActivity());
                    isChecked = false;
                }
            }
        }
    }
}
