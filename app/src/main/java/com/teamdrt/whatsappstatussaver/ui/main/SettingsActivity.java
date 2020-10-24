package com.teamdrt.whatsappstatussaver.ui.main;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Paint;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.teamdrt.whatsappstatussaver.R;

public class SettingsActivity extends AppCompatActivity {
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.settings_activity );
        if (savedInstanceState == null) {
            getSupportFragmentManager ()
                    .beginTransaction ()
                    .replace ( R.id.settings, new SettingsFragment () )
                    .commit ();
        }

        toolbar = findViewById(R.id.toolbar5);
        setSupportActionBar ( toolbar );
        ActionBar actionBar = getSupportActionBar ();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator ( R.drawable.ic_baseline_arrow_back_24 );
            actionBar.setDisplayHomeAsUpEnabled ( true );
            actionBar.setHomeButtonEnabled ( true );
        }


    }


    public static class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource ( R.xml.root_preferences, rootKey );
        }

        @Override
        public void onConfigurationChanged(@NonNull Configuration newConfig) {
            triggerRebirth ( getContext () );
            super.onConfigurationChanged ( newConfig );
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            PreferenceManager.getDefaultSharedPreferences ( getContext () ).registerOnSharedPreferenceChangeListener ( this );
            super.onCreate ( savedInstanceState );
        }

        @Override
        public void onPause() {
            PreferenceManager.getDefaultSharedPreferences ( getContext () ).unregisterOnSharedPreferenceChangeListener ( this );
            super.onPause ();
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            new AlertDialog.Builder(getContext ())
                    .setTitle("Restart")
                    .setMessage("Application Needs to restart to apply these changes")
                    .setPositiveButton("Ok!! Fine", (dialog, which) -> triggerRebirth ( getContext () ) )
                    .show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed ();
        return super.onSupportNavigateUp ();
    }

    public static void triggerRebirth(Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(context.getPackageName());
        ComponentName componentName = intent.getComponent();
        Intent mainIntent = Intent.makeRestartActivityTask(componentName);
        context.startActivity(mainIntent);
        Runtime.getRuntime().exit(0);
    }


}