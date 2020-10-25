package com.teamdrt.whatsappstatussaver.ui.main;

import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;
import androidx.work.impl.model.Preference;

import com.teamdrt.whatsappstatussaver.R;

import java.util.concurrent.Executor;

import static java.sql.DriverManager.getConnection;
import static java.sql.DriverManager.println;

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
        SwitchPreferenceCompat applock;
        ListPreference uimode;
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource ( R.xml.root_preferences, rootKey );
            applock= findPreference ( "security" );
            uimode=findPreference ( "ui_mode" );
            String uimode1= androidx.preference.PreferenceManager.getDefaultSharedPreferences ( getContext () ).getString ( "ui_mode","auto" );
            if (uimode1.equals ( "auto" )){
                uimode.setIcon ( R.drawable.ic_brightness );
            }else if (uimode1.equals ( "light" )){
                uimode.setIcon ( R.drawable.ic_icons8_sun );
            }else {
                uimode.setIcon ( R.drawable.ic_iconfinder_moon_dark_mode_night_5402400 );
            }

        }

        @Override
        public void onConfigurationChanged(@NonNull Configuration newConfig) {
            super.onConfigurationChanged ( newConfig );
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate ( savedInstanceState );
        }

        @Override
        public void onResume() {
            super.onResume ();
            PreferenceManager.getDefaultSharedPreferences ( getContext () ).registerOnSharedPreferenceChangeListener ( this );
        }

        @Override
        public void onPause() {
            PreferenceManager.getDefaultSharedPreferences ( getContext () ).unregisterOnSharedPreferenceChangeListener ( this );
            super.onPause ();
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            switch (key) {
                case "wa_version":
                case "ui_mode":
                    new AlertDialog.Builder ( getContext () )
                        .setTitle ( "Restart" )
                        .setMessage ( "Application Needs to restart to apply these changes" )
                        .setPositiveButton ( "Ok!! Fine", (dialog, which) -> triggerRebirth ( getContext () ) )
                        .show ();
                break;

                case "security":
                    boolean security=sharedPreferences.getBoolean ( key,false );
                    if (security) {
                        KeyguardManager km = (KeyguardManager) getContext ().getSystemService ( Context.KEYGUARD_SERVICE );
                        assert km != null;
                        if (km.isKeyguardSecure ()) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                BiometricPrompt biometricPrompt = new BiometricPrompt.Builder ( getContext () )
                                        .setTitle ( "Authentication required" )
                                        .setDescription ( "password" )
                                        .setDeviceCredentialAllowed ( true )
                                        .build ();


                                CancellationSignal cancellationSignal = new CancellationSignal ();
                                cancellationSignal.setOnCancelListener ( () -> {
                                            SharedPreferences.Editor sharedPref = sharedPreferences.edit ();
                                            sharedPref.putBoolean ( key, false );
                                            sharedPref.apply ();
                                            applock.setChecked ( false );
                                        }
                                );
                                Executor executor = ContextCompat.getMainExecutor ( getContext () );

                                BiometricPrompt.AuthenticationCallback authenticationCallback = new BiometricPrompt.AuthenticationCallback () {
                                    @Override
                                    public void onAuthenticationError(int errorCode, CharSequence errString) {
                                        Toast.makeText ( getContext (), "Unable to enable App Lock", Toast.LENGTH_SHORT ).show ();
                                        SharedPreferences.Editor sharedPref = sharedPreferences.edit ();
                                        sharedPref.putBoolean ( key, false );
                                        sharedPref.apply ();
                                        applock.setChecked ( false );
                                        super.onAuthenticationError ( errorCode, errString );
                                    }

                                    @Override
                                    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                                        super.onAuthenticationHelp ( helpCode, helpString );
                                    }

                                    @Override
                                    public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                                        Toast.makeText ( getContext (), "Enabled App Lock", Toast.LENGTH_SHORT ).show ();
                                        super.onAuthenticationSucceeded ( result );
                                    }

                                    @Override
                                    public void onAuthenticationFailed() {
                                        Toast.makeText ( getContext (), "Authentication Failed", Toast.LENGTH_SHORT ).show ();
                                        SharedPreferences.Editor sharedPref = sharedPreferences.edit ();
                                        sharedPref.putBoolean ( key, false );
                                        sharedPref.apply ();
                                        applock.setChecked ( false );
                                        super.onAuthenticationFailed ();
                                    }
                                };

                                biometricPrompt.authenticate ( cancellationSignal, executor, authenticationCallback );
                            } else {
                                Intent intent = km.createConfirmDeviceCredentialIntent ( "Authentication required", "password" );
                                startActivityForResult ( intent, 241 );
                            }
                        } else {
                            Toast.makeText ( getContext (), "Set a screen lock first", Toast.LENGTH_LONG ).show ();
                            SharedPreferences.Editor sharedPref = sharedPreferences.edit ();
                            sharedPref.putBoolean ( key, false );
                            sharedPref.apply ();
                            applock.setChecked ( false );
                            Intent intent=new Intent( DevicePolicyManager.ACTION_SET_NEW_PASSWORD );
                            startActivityForResult ( intent,250 );

                        }
                    }
            }
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult ( requestCode, resultCode, data );

            switch (requestCode){
                case 241:
                    if (resultCode==RESULT_OK){

                        Toast.makeText ( getContext (), "Enabled App Lock", Toast.LENGTH_SHORT ).show ();
                    }else {
                        SharedPreferences.Editor sharedPref = androidx.preference.PreferenceManager.getDefaultSharedPreferences ( getContext () ).edit ();
                        sharedPref.putBoolean ( "security", false );
                        sharedPref.apply ();
                        applock.setChecked ( false );
                    }

                case 250:
                    if (resultCode==RESULT_OK){
                        SharedPreferences.Editor sharedPref = androidx.preference.PreferenceManager.getDefaultSharedPreferences ( getContext () ).edit ();
                        sharedPref.putBoolean ( "security", true);
                        sharedPref.apply ();
                        applock.setChecked ( true );
                    }
            }
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