package com.teamdrt.whatsappstatussaver.ui.main.Video.ViewModel;

import android.os.Environment;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.File;
import java.util.ArrayList;

public class VideoFragmnetVM extends ViewModel {
    public MutableLiveData<ArrayList<String>> path=new MutableLiveData <> ();

    public void setPath(ArrayList<String> path) {
        this.path.postValue ( path );
    }

    public void getPath(){
        File file = new File ( Environment.getExternalStorageDirectory () + File.separator + "WhatsApp/Media/.Statuses" );
        if (file.exists ()) {
            ArrayList <String> list = getVideoList ( file );
            setPath ( list );
        }

    }

    public ArrayList<String> getVideoList(File file){
        ArrayList<String> list=new ArrayList<>();
        File[] listFiles;

        listFiles=file.listFiles ();

        if (listFiles!=null){
            for (int i=0;i<listFiles.length;i++){
                if (listFiles[i].getName ().endsWith ( "mp4" )){
                    list.add ( listFiles[i].getAbsolutePath ());
                }
            }
        }

        return list;
    }
}