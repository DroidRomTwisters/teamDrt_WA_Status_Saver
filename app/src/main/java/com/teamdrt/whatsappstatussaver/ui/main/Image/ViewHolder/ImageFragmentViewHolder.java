package com.teamdrt.whatsappstatussaver.ui.main.Image.ViewHolder;

import android.os.SystemClock;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.teamdrt.whatsappstatussaver.R;
import com.teamdrt.whatsappstatussaver.ui.main.Image.Adapter.ImageAdapter;
import com.teamdrt.whatsappstatussaver.ui.main.Video.Adapter.VideoAdapter;

public class ImageFragmentViewHolder extends RecyclerView.ViewHolder{
    public ImageView imageView;
    ImageAdapter.clicklistener clicklistener;

    CardView cv;

    public ImageFragmentViewHolder(@NonNull View itemView,ImageAdapter.clicklistener clicklistener) {
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
        if (SystemClock.elapsedRealtime() - lastClickTime < 1000){
            return;
        }else {
            clicklistener.OnDownloadClick ( getAdapterPosition () );
        }
        lastClickTime = SystemClock.elapsedRealtime();
    };

}
