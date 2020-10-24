package com.teamdrt.whatsappstatussaver.ui.main.Downloads;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.teamdrt.whatsappstatussaver.ui.main.Databases.Download;

import java.util.List;

public class DownloadsDiffUtil extends DiffUtil.Callback {

    private final List <Download> olddownloads;
    private final List <Download> newdownloads;

    public DownloadsDiffUtil(List <Download> olddownloads, List <Download> newdownloads) {
        this.olddownloads = olddownloads;
        this.newdownloads = newdownloads;
    }

    @Override
    public int getOldListSize() {
        return olddownloads.size ();
    }

    @Override
    public int getNewListSize() {
        return newdownloads.size ();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return olddownloads.get ( oldItemPosition ).getId ().equals ( newdownloads.get ( newItemPosition ).getId () );
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        if (olddownloads.get ( oldItemPosition ).getDownoadedPath ().equals ( newdownloads.get ( newItemPosition ).getDownoadedPath ()  )){
            if (olddownloads.get ( oldItemPosition ).getMediaType ().equals ( newdownloads.get ( newItemPosition ).getMediaType ()  )){
                return olddownloads.get ( oldItemPosition ).getTimestamp ().equals ( newdownloads.get ( newItemPosition ).getTimestamp () );
            }
        }
        return false;
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        Bundle diff=new Bundle (  );
        if (!olddownloads.get ( oldItemPosition ).getDownoadedPath ().equals ( newdownloads.get ( newItemPosition ).getDownoadedPath ()  )){
            diff.putString ( "path",newdownloads.get ( newItemPosition ).getDownoadedPath () );
        }

        if (!olddownloads.get ( oldItemPosition ).getMediaType ().equals ( newdownloads.get ( newItemPosition ).getMediaType ()  )){
            diff.putString ( "media",newdownloads.get ( newItemPosition ).getMediaType () );
        }

        if (!olddownloads.get ( oldItemPosition ).getTimestamp ().equals ( newdownloads.get ( newItemPosition ).getTimestamp ()  )){
            diff.putLong ( "time",newdownloads.get ( newItemPosition ).getTimestamp () );
        }

        if (diff.isEmpty ()){
            return null;
        }

        return diff;
    }
}
