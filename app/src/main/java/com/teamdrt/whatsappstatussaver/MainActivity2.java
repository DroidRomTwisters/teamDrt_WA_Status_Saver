package com.teamdrt.whatsappstatussaver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;


import android.content.SharedPreferences;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.preference.PreferenceManager;
import android.widget.Toast;

import java.util.concurrent.Executor;

import static java.sql.DriverManager.println;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String ui=sharedPref.getString ( "ui_mode","auto" );
        if (ui.equals ( "dark" )){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else if (ui.equals ( "light" )){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main2 );


        boolean applock=sharedPref.getBoolean ( "security",false );

        if (applock) {
            KeyguardManager km = (KeyguardManager) getSystemService ( Context.KEYGUARD_SERVICE );
            assert km != null;
            if (km.isKeyguardSecure ()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    BiometricPrompt biometricPrompt = new BiometricPrompt.Builder ( this )
                            .setTitle ( "Authentication required" )
                            .setDescription ( "password" )
                            .setDeviceCredentialAllowed ( true )
                            .build ();

                    CancellationSignal cancellationSignal = new CancellationSignal ();
                    cancellationSignal.setOnCancelListener ( this::finish );
                    Executor executor = ContextCompat.getMainExecutor ( MainActivity2.this );

                    BiometricPrompt.AuthenticationCallback authenticationCallback = new BiometricPrompt.AuthenticationCallback () {
                        @Override
                        public void onAuthenticationError(int errorCode, CharSequence errString) {
                            finish ();
                            super.onAuthenticationError ( errorCode, errString );
                        }

                        @Override
                        public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                            super.onAuthenticationHelp ( helpCode, helpString );
                        }

                        @Override
                        public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                            super.onAuthenticationSucceeded ( result );
                            startMainActivity ();
                        }

                        @Override
                        public void onAuthenticationFailed() {
                            super.onAuthenticationFailed ();
                        }
                    };
                    biometricPrompt.authenticate ( cancellationSignal, executor, authenticationCallback );
                } else {
                    Intent intent = km.createConfirmDeviceCredentialIntent ( "Authentication required", "password" );
                    startActivityForResult ( intent, 241 );
                }
            } else {
                startMainActivity ();
            }
        }else {
            startMainActivity ();
        }

    }

    @Override
    protected void onResume() {
        super.onResume ();

    }

    public void startMainActivity(){
        startActivity ( new Intent (MainActivity2.this,MainActivity.class) .addFlags ( Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }


}