package com.teamdrt.whatsappstatussaver.ui.main.Image.ViewModel;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.preference.PreferenceManager;

import org.apache.commons.io.comparator.LastModifiedFileComparator;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;


public class ImageVM extends ViewModel {
    public MutableLiveData <ArrayList <String>> path=new MutableLiveData <> ();

    Context context;
    File file;
    public void setPath(ArrayList<String> path) {
        this.path.postValue ( path );
    }

    public void getPath(Context context){

        this.context=context;
        SharedPreferences myPreference= PreferenceManager.getDefaultSharedPreferences( context );
        String wa_preference=myPreference.getString ( "wa_version","off" );

        switch (wa_preference){
            case "off":
                file = new File ( Environment.getExternalStorageDirectory () + File.separator + "WhatsApp/Media/.Statuses" );
                break;
            case "wabu":
                file = new File ( Environment.getExternalStorageDirectory () + File.separator + "WhatsApp Business/Media/.Statuses" );
                break;
            case "gbwa":
                file = new File ( Environment.getExternalStorageDirectory () + File.separator + "GBWhatsApp/Media/.Statuses" );
                break;
            case "fmwa":
                file = new File ( Environment.getExternalStorageDirectory () + File.separator + "FMWhatsApp/Media/.Statuses" );
                break;
            case "wago":
                file = new File ( Environment.getExternalStorageDirectory () + File.separator + "WhatsApp Gold/Media/.Statuses" );
                break;

        }

        if (file.exists ()) {
            ArrayList <String> list = getVideoList ( file );
            setPath ( list );
        }
        timer (  );
    }

    public ArrayList<String> getVideoList(File file){
        ArrayList<String> list=new ArrayList<>();
        File[] listFiles;

        listFiles=file.listFiles ();

        if (listFiles != null
        ) {
            Arrays.sort(listFiles, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
        }
        if (listFiles!=null){
            for (int i=0;i<listFiles.length;i++){
                if (listFiles[i].getName ().endsWith ( "jpg" ) || listFiles[i].getName ().endsWith ( "jpeg" ) || listFiles[i].getName ().endsWith ( "png" )){
                    list.add ( listFiles[i].getAbsolutePath ());
                }
            }
        }


        return list;
    }

    public void timer(){
        Timer timer=new Timer();
        timer.schedule ( new TimerTask () {
            @Override
            public void run() {
                getPath (context);
            }
        },2000 );

    }
}