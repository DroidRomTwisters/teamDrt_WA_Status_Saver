package com.teamdrt.whatsappstatussaver;

import android.content.Context;
import android.content.SharedPreferences;

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import com.teamdrt.whatsappstatussaver.ui.main.Pager.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPref = getPreferences ( Context.MODE_PRIVATE );
        String ui=sharedPref.getString ( "ui","dark" );
        if (ui.equals ( "dark" )){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
        toolbar = findViewById(R.id.toolbar3);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter ( this, getSupportFragmentManager () );
        ViewPager viewPager = findViewById ( R.id.view_pager );
        viewPager.setOffscreenPageLimit ( 3 );
        viewPager.setAdapter ( sectionsPagerAdapter );
        TabLayout tabs = findViewById ( R.id.tabs );
        tabs.setupWithViewPager ( viewPager );
        toolbar.inflateMenu ( R.menu.toolbar_menu );
    }

    @Override
    protected void onResume() {
        super.onResume ();

        toolbar.setOnMenuItemClickListener ( item -> {
            switch (item.getItemId ()){

                case R.id.nm:
                    SharedPreferences sharedPref = getPreferences ( Context.MODE_PRIVATE );
                    String ui=sharedPref.getString ( "ui","dark" );
                    if (ui.equals ( "light" )){
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString ( "ui","dark" );
                        editor.apply ();
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    }else {
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString ( "ui","light" );
                        editor.apply ();
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    }

            }

            return true;
        } );
    }


}