package com.teamdrt.whatsappstatussaver.ui.main.Databases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database ( entities = Download.class,version = 1)
public abstract class AppDatabse extends RoomDatabase {

    private static AppDatabse instance;

    public abstract DownloadsDao downloadsDao();


    public static synchronized AppDatabse getInstance(Context context){
        if (instance==null){
            instance= Room.databaseBuilder (
                    context, AppDatabse.class,"WhatsApp_status_saver" )
                    .fallbackToDestructiveMigration ()
                    .build ();
        }
        return instance;

    }
}
