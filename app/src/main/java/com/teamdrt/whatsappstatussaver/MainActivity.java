package com.teamdrt.whatsappstatussaver;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.content.pm.PackageManager;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import com.teamdrt.whatsappstatussaver.ui.main.Pager.SectionsPagerAdapter;
import com.teamdrt.whatsappstatussaver.ui.main.SettingsActivity;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView ( R.layout.activity_main );
        super.onCreate ( savedInstanceState );
        toolbar = findViewById(R.id.toolbar3);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter ( this, getSupportFragmentManager () );
        ViewPager viewPager = findViewById ( R.id.view_pager );
        viewPager.setOffscreenPageLimit ( 3 );
        viewPager.setAdapter ( sectionsPagerAdapter );
        TabLayout tabs = findViewById ( R.id.tabs );
        tabs.setupWithViewPager ( viewPager );
        toolbar.inflateMenu ( R.menu.toolbar_menu );
    }

    public void init(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean applock=sharedPref.getBoolean ( "security",false );
        if (applock){
            getWindow ().addFlags ( WindowManager.LayoutParams.FLAG_SECURE );
        }else {
            getWindow ().clearFlags ( WindowManager.LayoutParams.FLAG_SECURE );
        }

        toolbar.setOnMenuItemClickListener ( item -> {
            int itemId = item.getItemId ();
            if (itemId == R.id.setting) {
                Intent intent = new Intent(MainActivity.this,SettingsActivity.class);
                startActivity ( intent );
                return true;
            }
            return true;
        } );
    }
    @Override
    protected void onResume() {
        super.onResume ();
        if (isStoragePermissionGranted ()){
            init ();
        }

    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission( Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case 1:
            if (grantResults.length > 0 && grantResults[ 0 ] == PackageManager.PERMISSION_GRANTED) {
                init ();
            }
            break;
        }
        super.onRequestPermissionsResult ( requestCode, permissions, grantResults );
    }
}