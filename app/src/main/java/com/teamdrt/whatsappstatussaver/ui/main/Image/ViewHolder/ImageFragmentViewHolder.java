package com.teamdrt.whatsappstatussaver.ui.main.Image.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.SystemClock;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.teamdrt.whatsappstatussaver.BuildConfig;
import com.teamdrt.whatsappstatussaver.R;
import com.teamdrt.whatsappstatussaver.ui.main.Image.Adapter.ImageAdapter;
import com.teamdrt.whatsappstatussaver.ui.main.Video.Adapter.VideoAdapter;

import java.io.File;

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
    public void viewContent(String path, Context ctx){
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.addFlags ( Intent.FLAG_GRANT_READ_URI_PERMISSION );
        File file=new File(path);
        Uri uri = FileProvider.getUriForFile ( ctx, BuildConfig.APPLICATION_ID + ".FileProvider", file );
        String mimetype = ctx.getContentResolver ().getType ( uri );
        if (mimetype == null) {
            mimetype = "*/*";
        }
        intent.setDataAndType ( uri, mimetype );
        if (intent.resolveActivity ( ctx.getPackageManager () ) != null) {
            ctx.startActivity ( intent );
        } else {
            Toast.makeText ( ctx, "No app Found to handle this event", Toast.LENGTH_SHORT ).show ();
        }
    }
}
