package com.teamdrt.whatsappstatussaver.ui.main.Databases;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DownloadsDao {

    @Insert
    void insert(Download download);

    @Update
    void update(Download download);

    @Delete
    void delete(Download download);


    @Query ( "SELECT * from download_table ORDER BY timestamp DESC" )
    LiveData <List<Download>> getAllDownloads();
}
