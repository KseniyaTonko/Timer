package com.example.tabatatimer;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        SwitchPreferenceCompat spc = findPreference("night");
        assert spc != null;
        spc.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if (!spc.isChecked()) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                return true;
            }
        });

        Preference preference = findPreference("deleteAll");
        assert preference != null;
        preference.setOnPreferenceClickListener(preference1 -> {
            try {
                if (Build.VERSION_CODES.KITKAT <= Build.VERSION.SDK_INT) {
                    ((ActivityManager)requireActivity().getSystemService(Context.ACTIVITY_SERVICE)).clearApplicationUserData();
                } else {
                    String packageName = requireActivity().getApplicationContext().getPackageName();
                    Runtime runtime = Runtime.getRuntime();
                    runtime.exec("pm clear " + packageName);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            } finally {
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
                if (getContext() instanceof Activity) {
                    ((Activity) requireContext()).finish();
                }

                Runtime.getRuntime().exit(0);
            }
            return true;
        });
    }

    public void changeFontSizeInViews(View v, Integer value) {
        if (v instanceof TextView) {
            ((TextView) v).setTextSize(value);
        } else if (v instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) v).getChildCount(); i++)
                changeFontSizeInViews(((ViewGroup) v).getChildAt(i), value);
        }
    }

}