package com.teamdrt.whatsappstatussaver.ui.main.Downloads;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.teamdrt.whatsappstatussaver.ui.main.Databases.AppDatabse;
import com.teamdrt.whatsappstatussaver.ui.main.Databases.Download;
import com.teamdrt.whatsappstatussaver.ui.main.Databases.DownloadsDao;
import com.teamdrt.whatsappstatussaver.ui.main.Databases.DownloadsRepository;

import java.util.List;

public class DownloadsViewModel extends ViewModel {
    private DownloadsRepository repository;
    public LiveData <List <Download>> allDownloads;

    private void updateDownloads(LiveData<List<Download>> allDownloads) {
        this.allDownloads = allDownloads;
    }

    public void getAllDownloads(Context ctx){
        DownloadsDao downloadsDao= AppDatabse.getInstance ( ctx ).downloadsDao ();
        repository= new DownloadsRepository(downloadsDao);
        updateDownloads ( repository.getAllDownloads () );
    }
}