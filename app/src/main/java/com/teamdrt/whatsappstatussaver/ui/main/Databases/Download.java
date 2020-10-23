package com.teamdrt.whatsappstatussaver.ui.main.Databases;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "download_table")
public class Download {

    @ColumnInfo(name = "timestamp")
    private  Long timestamp;

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "downloaded_path")
    private String downoadedPath;

    @ColumnInfo(name = "media_type")
    private String mediaType;

    public Download(Long timestamp) {
        this.timestamp = timestamp;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public void setDownoadedPath(String downoadedPath) {
        this.downoadedPath = downoadedPath;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public String getDownoadedPath() {
        return downoadedPath;
    }

    public String getMediaType() {
        return mediaType;
    }
}
