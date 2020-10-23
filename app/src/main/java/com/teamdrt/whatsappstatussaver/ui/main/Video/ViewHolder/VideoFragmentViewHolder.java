package com.teamdrt.whatsappstatussaver.ui.main.Video.ViewHolder;

import android.os.SystemClock;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.teamdrt.whatsappstatussaver.R;
import com.teamdrt.whatsappstatussaver.ui.main.Video.Adapter.VideoAdapter;

public class VideoFragmentViewHolder extends RecyclerView.ViewHolder {


    public ImageView imageView;
    VideoAdapter.clicklistener clicklistener;

    CardView cv;
    public VideoFragmentViewHolder(@NonNull View itemView,VideoAdapter.clicklistener clicklistener) {
        super ( itemView );
        imageView=itemView.findViewById ( R.id.imageView );
        this.clicklistener=clicklistener;
        cv=itemView.findViewById ( R.id.cv );
        imageView.setOnClickListener ( buttonHandler );
        cv.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                clicklistener.ButtonOnClick ( getAdapterPosition () );
            }
        } );

    }

    private long lastClickTime = 0;

    View.OnClickListener buttonHandler = v -> {
        // preventing double, using threshold of 1000 ms
        if (SystemClock.elapsedRealtime() - lastClickTime < 1000){
            return;
        }else {
            clicklistener.OnDownloadClick ( getAdapterPosition () );
        }
        lastClickTime = SystemClock.elapsedRealtime();
    };


}
