package com.teamdrt.whatsappstatussaver.ui.main.Databases;


import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class DownloadsRepository {
    private DownloadsDao downloadsDao;
    private LiveData <List<Download>> allDownloads;

    public DownloadsRepository(DownloadsDao downloadsDao){
        this.downloadsDao=downloadsDao;
        allDownloads=downloadsDao.getAllDownloads ();
    }

    public void update(Download download){

        new UpdateDownloadAsyncTask ( downloadsDao ).execute ( download );
    }

    public void delete(Download download){
        new DeleteDownloadAsyncTask ( downloadsDao ).execute ( download );
    }

    public void insert(Download download){

        new InsertDownloadAsyncTask ( downloadsDao ).execute ( download );
    }


    public LiveData <List<Download>> getAllDownloads(){
        return allDownloads;
    }

    private static class InsertDownloadAsyncTask extends AsyncTask<Download,Void,Void>{

        private DownloadsDao downloadsDao;

        private InsertDownloadAsyncTask(DownloadsDao downloadsDao){
            this.downloadsDao=downloadsDao;
        }
        @Override
        protected Void doInBackground(Download... downloads) {
            downloadsDao.insert ( downloads[0] );
            return null;
        }
    }

    private static class UpdateDownloadAsyncTask extends AsyncTask<Download,Void,Void>{

        private DownloadsDao downloadsDao;

        private UpdateDownloadAsyncTask(DownloadsDao downloadsDao){
            this.downloadsDao=downloadsDao;
        }
        @Override
        protected Void doInBackground(Download... downloads) {
            downloadsDao.update (downloads[0]);
            return null;
        }
    }

    private static class DeleteDownloadAsyncTask extends AsyncTask<Download,Void,Void>{

        private DownloadsDao downloadsDao;

        private DeleteDownloadAsyncTask(DownloadsDao downloadsDao){
            this.downloadsDao=downloadsDao;
        }
        @Override
        protected Void doInBackground(Download... downloads) {
            downloadsDao.delete ( downloads[0] );
            return null;
        }
    }
}

