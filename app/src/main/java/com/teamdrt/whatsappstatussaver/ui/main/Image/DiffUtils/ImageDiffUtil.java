package com.teamdrt.whatsappstatussaver.ui.main.Image.DiffUtils;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import java.util.ArrayList;

public class ImageDiffUtil extends DiffUtil.Callback {

    ArrayList<String> oldpath;
    ArrayList<String> newpath;

    public ImageDiffUtil(ArrayList <String> oldpath, ArrayList <String> newpath) {
        this.oldpath = oldpath;
        this.newpath = newpath;
    }

    @Override
    public int getOldListSize() {
        return oldpath.size ();
    }

    @Override
    public int getNewListSize() {
        return newpath.size ();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldpath.get ( oldItemPosition ).equals ( newpath.get ( newItemPosition ) );
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldpath.get ( oldItemPosition ).equals ( newpath.get ( newItemPosition ) );
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        Bundle diff=new Bundle (  );

        if (oldpath.get ( oldItemPosition ).equals ( newpath.get ( newItemPosition ) )){
            return null;
        }
        diff.putString ( "path",newpath.get ( newItemPosition ) );
        return diff;
    }
}

